package com.example.bookmap.data.repository

import com.example.bookmap.data.dao.UserDao
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
){
    suspend fun createUser(userEntity: com.example.bookmap.data.entity.UserEntity) {
        userDao.createuser(userEntity)
    }

    suspend fun updateUser(userEntity: com.example.bookmap.data.entity.UserEntity) {
        userDao.updateUser(userEntity)
    }

    suspend fun getUserById(id: Int): com.example.bookmap.data.entity.UserEntity {
        return userDao.getUserById(id)
    }

    suspend fun loginUser(email: String, password: String): Boolean {
        return userDao.loginUser(email, password)
    }
}