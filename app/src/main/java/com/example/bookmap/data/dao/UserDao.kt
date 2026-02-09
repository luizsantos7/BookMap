package com.example.bookmap.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bookmap.data.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createuser(userEntity: UserEntity)

    suspend fun updateUser(userEntity: UserEntity) {
        createuser(userEntity)
    }

    suspend fun loginUser(email: String, password: String): Boolean {
        val foundUser = getUserByEmailAndPassword(email, password)
        return (foundUser != null)
    }

    @Query("SELECT * FROM users WHERE email = :email and password = :password LIMIT 1")
    suspend fun getUserByEmailAndPassword(email: String, password: String): UserEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE email = :email)")
    suspend fun emailExists(email: String): Boolean

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Int): UserEntity

}