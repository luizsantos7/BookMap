package com.example.bookmap.presentation.login

import com.example.bookmap.presentation.login.models.UserRegister
import com.example.bookmap.utils.constants.EMPTY_STRING

data class LoginUiState(
    val email: String = EMPTY_STRING,
    val password: String = EMPTY_STRING,
    val showRegisterDialog: Boolean = false,
    val showError: Boolean = false,
    val errorMessage: String = EMPTY_STRING,
    val userRegister: UserRegister = UserRegister()
)
