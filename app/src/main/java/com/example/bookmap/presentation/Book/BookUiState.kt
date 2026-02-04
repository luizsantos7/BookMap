package com.example.bookmap.presentation.Book

import com.example.bookmap.data.entity.BookEntity

open class BookUiState {
    sealed class State() {
        object Initial : BookUiState()
        object Loading : BookUiState()
        data class Success(val bookEntity: BookEntity) : BookUiState()
        data class Error(val message: String) : BookUiState()
    }
}