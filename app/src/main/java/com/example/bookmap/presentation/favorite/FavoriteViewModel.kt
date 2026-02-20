package com.example.bookmap.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookmap.data.models.BookDataModel
import com.example.bookmap.data.repository.FavoriteRepository
import com.example.bookmap.presentation.favorite.FavoriteScreenAction.OnRemoveFromFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoriteUiState())
    val uiState = _uiState

    init {
        getFavoriteBooks()
    }

    fun onActionEvent(action: FavoriteScreenAction) {
        when (action) {
            is OnRemoveFromFavorite -> unfavoriteBook(action.book)
        }
    }

    private fun getFavoriteBooks() {
        _uiState.update { it.copy(isLoading = true, showError = false) }

        viewModelScope.launch {
            val favorites = try {
                favoriteRepository.getFavoriteBooks()
            } catch (e: Exception) {
                emptyList()
            }

            _uiState.update {
                it.copy(
                    isLoading = false,
                    showError = false,
                    listBook = favorites
                )
            }
        }
    }

    private fun unfavoriteBook(book: BookDataModel) {
        viewModelScope.launch {
            favoriteRepository.removeFavorite(book)
            getFavoriteBooks()
        }
    }
}