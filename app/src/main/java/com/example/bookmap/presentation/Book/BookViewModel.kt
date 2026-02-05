package com.example.bookmap.presentation.Book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookmap.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _bookState = MutableStateFlow<BookUiState>(value = BookUiState.Initial)
    val bookState = _bookState

    fun getBooks(id: Int) {
        _bookState.value = BookUiState.Loading

        viewModelScope.launch {
            val result = bookRepository.buscarLivroPorId(id)

            _bookState.value = result.fold(
                onSuccess = { BookUiState.Success(bookEntity = it) },
                onFailure = {
                    BookUiState.Error(
                        message = it.message ?: "Erro ao buscar os livros"
                    )
                },
            )
        }
    }

}