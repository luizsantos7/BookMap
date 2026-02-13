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

    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE email = :email AND password = :password)")
    suspend fun loginUser(email: String, password: String): Boolean


    // Favoritos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteBook(book: FavoriteBookEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserFavoriteBook(crossRef: UserFavoriteBookCrossRef)

    @Query("DELETE FROM user_favorite_books WHERE userId = :userId AND bookId = :bookId")
    suspend fun deleteUserFavoriteBook(userId: Int, bookId: Int)

    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserWithFavoriteBooks(userId: Int): UserWithFavoriteBooks
}