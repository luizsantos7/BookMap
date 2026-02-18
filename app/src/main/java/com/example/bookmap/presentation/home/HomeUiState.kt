package com.example.bookmap.presentation.home

import com.example.bookmap.data.entity.BookEntity
import com.example.bookmap.data.entity.UserEntity
import com.example.bookmap.data.entity.enum.CountType
import com.example.bookmap.utils.constants.EMPTY_STRING


data class HomeUiState(
    val searchBook: Boolean = false,
    val searchBookText: String = "",
    val listBook: List<BookEntity> = emptyList(),
    val filteredBooks: List<BookEntity> = emptyList(),
    val isLoading: Boolean = false,
    val showError: Boolean = false,
    val isContinue: Boolean = false,
    val errorMessage: String = "",
    val user : UserEntity = UserEntity(
        name = EMPTY_STRING,
        email = EMPTY_STRING,
        confirmEmail = EMPTY_STRING,
        password = EMPTY_STRING,
        birthday = EMPTY_STRING,
        gender = EMPTY_STRING,
        countType = CountType.GUEST.name
    )
)
