package com.example.bookmap.data.repository

import android.util.Log
import androidx.room.Query
import com.example.bookmap.data.dao.FavoriteDao
import com.example.bookmap.data.dao.UserDao
import com.example.bookmap.data.entity.BookEntity
import com.example.bookmap.data.entity.FavoriteBookEntity
import com.example.bookmap.data.entity.UserEntity
import com.example.bookmap.data.entity.UserFavoriteBookCrossRef
import com.example.bookmap.data.entity.UserWithFavoriteBooks
import com.example.bookmap.data.entity.enum.CountType
import com.example.bookmap.data.entity.enum.ReadStatus
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val favoriteDao: FavoriteDao
) {
    suspend fun createUser(user: UserEntity): Boolean {
        return try {
            val emailExists = userDao.emailExists(user.email)
            if (emailExists) {
                false
            } else {
                user.countType = CountType.USER.name
                userDao.createuser(user)
                true
            }
        } catch (e: Exception) {
            false
        }
    }
    suspend fun loginUser(email: String, password: String): UserEntity? {
        val userFound = userDao.loginUser(email, password)
        if (userFound != null) {
            userFound.countType = CountType.USER.name
            return userFound
        }
        return null
    }

    //FAVORITE METODOS
    suspend fun toggleFavoriteBook(userId: Int, book: BookEntity) {
        val userWithFavorites = favoriteDao.getUserWithFavoriteBooks(userId)

        val isFavorite = userWithFavorites?.favoriteBooks?.any { it.id == book.id } == true

        if (isFavorite) {
            favoriteDao.deleteUserFavoriteBook(userId, book.id)
        } else {
            val favoriteBook = FavoriteBookEntity(
                id = book.id,
                title = book.title,
                coverUrl = book.coverUrl,
                isRead = ReadStatus.valueOf(book.isRead.name)
            )

            favoriteDao.insertFavoriteBook(favoriteBook)
            favoriteDao.insertUserFavoriteCrossRef(
                UserFavoriteBookCrossRef(userId = userId, bookId = book.id)
            )
            Log.d("DB_TEST", "Vínculo criado: userId=$userId, bookId=${book.id}")
        }
    }

    suspend fun getUserFavorites(userId: Int): List<FavoriteBookEntity> {
        val userWithFavorites = favoriteDao.getUserWithFavoriteBooks(userId)
        return userWithFavorites?.favoriteBooks ?: emptyList()
    }
}
