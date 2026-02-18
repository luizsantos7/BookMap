package com.example.bookmap.presentation.favorite

import com.example.bookmap.data.entity.FavoriteBookEntity
import com.example.bookmap.data.entity.UserEntity
import com.example.bookmap.data.entity.enum.CountType
import com.example.bookmap.utils.constants.EMPTY_STRING

data class FavoriteUiState(
    val favoritedList: List<FavoriteBookEntity> = listOf(),
    val isLoading: Boolean = false,
    val showError: Boolean = false,
    val isEmpty : Boolean = false,
    val errorMessage: String = "",
    val isContinue: Boolean = false,
    val user: UserEntity = UserEntity(
        name = EMPTY_STRING,
        email = EMPTY_STRING,
        confirmEmail = EMPTY_STRING,
        password = EMPTY_STRING,
        birthday = EMPTY_STRING,

        gender = EMPTY_STRING,
        countType = CountType.GUEST.name
    )
)