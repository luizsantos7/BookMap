package com.example.bookmap.presentation.login

import com.example.bookmap.utils.constants.EMPTY_STRING

open class LoginUiState {
    object Initial : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()


    data class Presentation(
        val email: String = EMPTY_STRING,
        val password: String = EMPTY_STRING,
    )
}