package com.example.bookmap.presentation.login

import com.example.bookmap.data.entity.UserEntity
import com.example.bookmap.utils.constants.EMPTY_STRING

data class LoginUiState(
    val email: String = EMPTY_STRING,
    val password: String = EMPTY_STRING,
    val isLoginButtonEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val isContinue: Boolean = false,
    val showRegisterDialog: Boolean = false,
    val showError: Boolean = false,
    val errorMessage: String = EMPTY_STRING,
    val userRegister : UserEntity = UserEntity(
        name = EMPTY_STRING,
        email = EMPTY_STRING,
        confirmEmail = EMPTY_STRING,
        password = EMPTY_STRING,
        birthday = EMPTY_STRING,
        gender = EMPTY_STRING
    )
)
