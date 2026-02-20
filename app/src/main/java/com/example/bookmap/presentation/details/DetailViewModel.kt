package com.example.bookmap.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookmap.data.repository.BookRepository
import com.example.bookmap.presentation.details.DetailScreenAction.LoadBookDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val bookRepository: BookRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState = _uiState

    fun onActionEvent(action: DetailScreenAction) {
        when (action) {
            is LoadBookDetails -> loadBookDetails(action.bookId)
        }
    }

    private fun loadBookDetails(bookId: String?) {
        _uiState.update { it.copy(isLoading = true, isContinue = false, showError = false) }
        if (bookId == null) return

        viewModelScope.launch {
            bookRepository.buscarLivroPorId(bookId)
                .onSuccess { bookDetails ->
                    _uiState.update { it.copy(
                        book = bookDetails,
                        isContinue = true,
                        isLoading = false
                    ) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(
                        errorMessage = error.localizedMessage,
                        showError = true) }
                }
        }
    }

}