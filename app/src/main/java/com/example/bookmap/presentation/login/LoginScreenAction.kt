package com.example.bookmap.presentation.login

import com.example.bookmap.presentation.login.LoginScreenAction.EmailChanged
import com.example.bookmap.presentation.login.LoginScreenAction.PasswordChanged
import com.example.bookmap.presentation.login.LoginScreenAction.SubmitLogin


sealed class LoginScreenAction {
    data class EmailChanged(val newEmail: String) : LoginScreenAction()
    data class PasswordChanged(val newPassword: String) : LoginScreenAction()
    object SubmitLogin : LoginScreenAction()
}

fun LoginScreenAction.fold(
    onEmailChanged: (email: String) -> Unit = {},
    onPasswordChanged: (password: String) -> Unit = {},
    onSubmitLogin: () -> Unit = {},
) {
    when (this) {
        is EmailChanged -> onEmailChanged(newEmail)
        is PasswordChanged -> onPasswordChanged(newPassword)
        SubmitLogin -> onSubmitLogin()
    }
}