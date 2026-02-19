package com.example.bookmap.presentation.home

import com.example.bookmap.data.models.BookDataModel
import com.example.bookmap.presentation.login.models.UserRegister


data class HomeUiState(
    val searchBook: Boolean = false,
    val searchBookText: String = "",
    val listBook: List<BookDataModel> = emptyList(),
    val filteredBooks: List<BookDataModel> = emptyList(),
    val isLoading: Boolean = false,
    val showError: Boolean = false,
    val isContinue: Boolean = false,
    val errorMessage: String = "",
    val user : UserRegister = UserRegister()
)
