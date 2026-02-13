package com.example.bookmap.data.repository

import android.util.Log
import com.example.bookmap.data.dao.UserDao
import com.example.bookmap.data.entity.UserEntity
import com.example.bookmap.data.entity.enum.CountType
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun createUser(user: UserEntity): Boolean {
        return try {
            val emailExists = userDao.emailExists(user.email)

            if (emailExists) {
                return false
            } else {
                user.countType = CountType.USER
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