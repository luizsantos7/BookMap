package com.example.bookmap.presentation.home

import com.example.bookmap.data.entity.BookEntity
import com.example.bookmap.utils.constants.EMPTY_STRING


data class HomeUiState(
    val searchBook: Boolean = false,
    val searchBookText: String = "",
    val listBook: List<BookEntity> = emptyList(),
    val filteredBooks: List<BookEntity> = emptyList(),
    val isLoading: Boolean = false,
    val showError: Boolean = false,
    val isContinue: Boolean = false,
    val errorMessage: String = ""
)
