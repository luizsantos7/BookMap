package com.example.bookmap.presentation.home

import com.example.bookmap.data.entity.BookEntity
import com.example.bookmap.utils.constants.EMPTY_STRING


data class HomeUiState(
    val searchBook: Boolean = false,
    val searchBookText: String = EMPTY_STRING,
    val listBook : List<BookEntity> = listOf(),
    val isLoading: Boolean = false,
    val isContinue: Boolean = false,
    val showError: Boolean = false,
)
