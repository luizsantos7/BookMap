package com.example.bookmap.data.models

data class UserRegisterDataModel(
    val email: String = "",
    val confirmEmail: String = "",
    val password: String = "",
    val profile: Profile = Profile()
)

data class Profile(
    val name: String = "",
    val birthday: String = "",
    val gender: String = ""
)
