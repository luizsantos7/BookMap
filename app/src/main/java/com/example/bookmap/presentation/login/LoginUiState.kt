package com.example.bookmap.presentation.login

import com.example.bookmap.utils.constants.EMPTY_STRING

data class LoginUiState(
    val email: String = EMPTY_STRING,
    val password: String = EMPTY_STRING,
    val isLoginButtonEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val showError: Boolean = false
)
