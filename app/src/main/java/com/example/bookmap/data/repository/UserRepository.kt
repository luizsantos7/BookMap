package com.example.bookmap.data.repository

import com.example.bookmap.data.dao.UserDao
import com.example.bookmap.data.entity.BookEntity
import com.example.bookmap.data.entity.FavoriteBookEntity
import com.example.bookmap.data.entity.UserEntity
import com.example.bookmap.data.entity.UserFavoriteBookCrossRef
import com.example.bookmap.data.entity.UserWithFavoriteBooks
import com.example.bookmap.data.entity.enum.CountType
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun createUser(user: UserEntity): Boolean {
        return try {
            val emailExists = userDao.emailExists(user.email)
            if (emailExists) {
                false
            } else {
                user.countType = CountType.USER
                userDao.createuser(user)
                true
            }
        } catch (e: Exception) {
            false
        }
    }

    suspend fun updateUser(userEntity: UserEntity) = userDao.updateUser(userEntity)

    suspend fun getUserById(id: Int) = userDao.getUserById(id)
    suspend fun loginUser(email: String, password: String) = userDao.loginUser(email, password)

    //FAVORITE METODOS
    suspend fun toggleFavoriteBook(userId: Int, book: BookEntity) {
        val userWithFavorites = userDao.getUserWithFavoriteBooks(userId)

        if (userWithFavorites == null) {
            return
        }

        val isFavorite = userWithFavorites.favoriteBooks.any { it.id == book.id }

        if (isFavorite) {
            userDao.deleteUserFavoriteBook(userId, book.id)
        } else {
            val localBook = FavoriteBookEntity(
                id = book.id,
                title = book.title,
                coverUrl = book.coverUrl
            )
            userDao.insertFavoriteBook(localBook)
            userDao.insertUserFavoriteBook(
                UserFavoriteBookCrossRef(userId, localBook.id)
            )
        }
    }

//    suspend fun getUserWithFavoriteBooks(userId: Int): UserWithFavoriteBooks {
//        return userDao.getUserWithFavoriteBooks(userId)
//    }
}
