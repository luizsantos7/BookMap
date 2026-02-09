package com.example.bookmap.data.repository

import android.util.Log
import com.example.bookmap.data.dao.UserDao
import com.example.bookmap.data.entity.UserEntity
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
){
    suspend fun createUser(user: UserEntity): Boolean {
        Log.d("Register", "user.email = '${user.email}'")
        return try {
            if (user.email.isBlank()) {
                return false
            }

            if (userDao.emailExists(user.email)) {
                false
            } else {
                userDao.createuser(user)
                true
            }
        } catch (e: Exception) {
            false
        }
    }

    suspend fun updateUser(userEntity: UserEntity) {
        userDao.updateUser(userEntity)
    }

    suspend fun getUserById(id: Int): UserEntity {
        return userDao.getUserById(id)
    }

    suspend fun loginUser(email: String, password: String): Boolean {
        return userDao.loginUser(email, password)
    }
}