package com.example.bookmap.presentation.login

import com.example.bookmap.presentation.login.LoginScreenAction.EmailChanged
import com.example.bookmap.presentation.login.LoginScreenAction.PasswordChanged
import com.example.bookmap.presentation.login.LoginScreenAction.SubmitLogin


sealed class LoginScreenAction {
    data class EmailChanged(val newEmail: String) : LoginScreenAction()
    data class PasswordChanged(val newPassword: String) : LoginScreenAction()
    class SubmitLogin(val email: String, val password: String) : LoginScreenAction()
    class newRegister() : LoginScreenAction()

}
