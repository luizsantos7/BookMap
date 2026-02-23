package com.example.bookmap.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookmap.data.models.BookDataModel
import com.example.bookmap.data.models.ReadStatusDataModel
import com.example.bookmap.data.repository.BookRepository
import com.example.bookmap.data.repository.StatusRepository
import com.example.bookmap.presentation.details.DetailScreenAction.LoadBookDetails
import com.example.bookmap.presentation.details.DetailScreenAction.OnStatusChange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val bookRepository: BookRepository,
    val statusRepository: StatusRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState = _uiState

    private val bookId = savedStateHandle.get<String>("bookId").orEmpty()

    init {
        loadBookDetails()
    }

    fun onActionEvent(action: DetailScreenAction) {
        when (action) {
            is LoadBookDetails -> loadBookDetails()
            is OnStatusChange -> onStatusChange(status = action.status)
        }
    }

    private fun loadBookDetails() {
        _uiState.update { it.copy(isLoading = true, isContinue = false, showError = false) }

        viewModelScope.launch {
            bookRepository.buscarLivroPorId(bookId)
                .onSuccess { bookDetails ->
                    _uiState.update {
                        it.copy(
                            book = bookDetails,
                            isContinue = true,
                            isLoading = false
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            errorMessage = error.localizedMessage,
                            showError = true
                        )
                    }
                }
        }
    }

    private fun onStatusChange(status: ReadStatusDataModel) {
        val oldBook = _uiState.value.book
        val oldStatus = oldBook.isRead


        statusRepository.removeStatus(book = oldBook.copy(isRead = oldStatus))

        val updatedBook = oldBook.copy(isRead = status)

        statusRepository.addStatus(status = status, book = updatedBook)

        _uiState.update { currentState ->
            currentState.copy(book = updatedBook)
        }
    }
}