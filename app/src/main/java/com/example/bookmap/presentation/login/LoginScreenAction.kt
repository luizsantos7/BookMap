package com.example.bookmap.presentation.login

import com.example.bookmap.data.entity.UserEntity
import com.example.bookmap.presentation.login.LoginScreenAction.EmailChanged
import com.example.bookmap.presentation.login.LoginScreenAction.PasswordChanged
import com.example.bookmap.presentation.login.LoginScreenAction.SubmitLogin


sealed class LoginScreenAction {
    data class EmailChanged(val newEmail: String) : LoginScreenAction()
    data class PasswordChanged(val newPassword: String) : LoginScreenAction()
    class SubmitLogin(val email: String, val password: String) : LoginScreenAction()
    class newRegister() : LoginScreenAction()

    // Register
    data class RegisterNameChanged(val name: String) : LoginScreenAction()
    data class RegisterEmailChanged(val email: String) : LoginScreenAction()
    data class RegisterConfirmEmailChanged(val confirmEmail: String) : LoginScreenAction()
    data class RegisterPasswordChanged(val password: String) : LoginScreenAction()
    data class RegisterBirthdayChanged(val birthday: String) : LoginScreenAction()
    data class RegisterGenderChanged(val gender: String) : LoginScreenAction()
    data class SubmitRegister(val userEntity : UserEntity) : LoginScreenAction()

}
