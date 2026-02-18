package com.example.bookmap.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookmap.data.entity.UserEntity
import com.example.bookmap.data.repository.BookRepository
import com.example.bookmap.data.repository.UserRepository
import com.example.bookmap.presentation.favorite.FavoriteScreenAction.SetUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavoriteUiState())
    val uiState = _uiState

    init {
        loadFavoriteBooks(_uiState.value.user)
    }

    fun onActionEvent(action: FavoriteScreenAction) {
        when (action) {
            is SetUser -> setNewUser(action.user)
        }
    }

    private fun loadFavoriteBooks(user: UserEntity) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    isContinue = false,
                    showError = false,
                    isEmpty = false
                )
            }
            val favorites = userRepository.getUserFavorites(user.id)
            if (favorites.isEmpty()) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isContinue = false,
                        showError = false,
                        isEmpty = true
                    )
                }
            }
            if (favorites.isNotEmpty()) {
                _uiState.update {
                    it.copy(
                        favoritedList = favorites,
                        isLoading = false,
                        isContinue = true,
                        showError = false,
                        isEmpty = false
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isContinue = false,
                        showError = true,
                        errorMessage = "Erro ao carregar os favoritos, tente novamente!"
                    )
                }
            }

        }
    }

    private fun setNewUser(user: UserEntity) {
        _uiState.update {
            it.copy(
                user = user
            )
        }
    }
}