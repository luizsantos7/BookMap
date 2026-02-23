package com.example.bookmap.presentation.profile

import com.example.bookmap.data.models.BookDataModel
import com.example.bookmap.data.models.UserRegisterDataModel

data class ProfileUiState(
    val isLoading: Boolean = false,
    val showError: Boolean = false,
    val errorMessage: String = "",
    val user : UserRegisterDataModel = UserRegisterDataModel(),
    val readingBooks: List<BookDataModel> = emptyList(),
    val readBooks: List<BookDataModel> = emptyList(),
    val unreadBooks: List<BookDataModel> = emptyList(),
    val pausedBooks: List<BookDataModel> = emptyList(),
    val droppedBooks: List<BookDataModel> = emptyList(),
)