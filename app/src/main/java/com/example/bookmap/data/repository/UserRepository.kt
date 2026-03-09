package com.example.bookmap.data.repository

import com.example.bookmap.data.models.Profile
import com.example.bookmap.data.models.UserRegisterDataModel

interface UserRepository {
    fun createUser(user: UserRegisterDataModel, onSuccess: () -> Unit, onFailure: () -> Unit)
    fun loginUser(email: String, password: String, onSuccess: () -> Unit, onFailure: () -> Unit)
    fun loadUserProfile(
        uid: String,
        onSuccess: (Profile) -> Unit,
        onFailure: (Exception) -> Unit
    )
}