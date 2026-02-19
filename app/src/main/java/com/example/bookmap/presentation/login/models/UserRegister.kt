package com.example.bookmap.presentation.login.models

data class UserRegister(
    val name: String = "",
    val email: String = "",
    val confirmEmail: String = "",
    val password: String = "",
    val birthday: String = "",
    val gender: String = ""
)
