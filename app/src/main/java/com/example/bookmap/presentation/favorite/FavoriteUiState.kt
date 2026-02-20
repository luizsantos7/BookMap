package com.example.bookmap.presentation.favorite

import com.example.bookmap.data.models.BookDataModel

data class FavoriteUiState(
    val listBook: List<BookDataModel> = emptyList(),
    val isLoading: Boolean = false,
    val isContinue: Boolean = false,
    val showError: Boolean = false,
    val errorMessage: String = "",
)