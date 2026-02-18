package com.example.bookmap.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.bookmap.data.entity.FavoriteBookEntity
import com.example.bookmap.data.entity.UserEntity
import com.example.bookmap.data.entity.UserFavoriteBookCrossRef
import com.example.bookmap.data.entity.UserWithFavoriteBooks

@Dao
interface UserDao {
    // Usuários
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createuser(user: UserEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE email = :email)")
    suspend fun emailExists(email: String): Boolean

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Int): UserEntity

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun loginUser(email: String, password: String): UserEntity?


}