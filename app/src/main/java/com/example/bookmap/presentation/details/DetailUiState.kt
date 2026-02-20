package com.example.bookmap.presentation.details

data class DetailUiState (
    val bookId: String = "",
    val name: String = "",
    val author: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val isFavorited: Boolean = false,
    val isLoading: Boolean = false,
    val showError: Boolean = false,
    val errorMessage: String = "",
)