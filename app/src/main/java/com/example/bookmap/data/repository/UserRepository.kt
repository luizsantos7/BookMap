package com.example.bookmap.data.repository

import com.example.bookmap.data.models.UserRegisterDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    fun createUser(user: UserRegisterDataModel, onSuccess: () -> Unit, onFailure: () -> Unit) {
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnSuccessListener {
                val uid = auth.currentUser?.uid ?: run {
                    onFailure()
                    return@addOnSuccessListener
                }

                firestore.collection("users")
                    .document(uid)
                    .collection("profile")
                    .add(user.profile)
                    .addOnSuccessListener {
                        onSuccess()
                    }
                    .addOnFailureListener {
                        onFailure()
                    }
            }
            .addOnFailureListener {
                onFailure()
            }
    }

    fun loginUser(email: String, password: String, onSuccess: () -> Unit, onFailure: () -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure()
            }
    }

    //FAVORITE METODOS
//    suspend fun toggleFavoriteBook(userId: Int, book: BookEntity) {
//        val userWithFavorites = favoriteDao.getUserWithFavoriteBooks(userId)
//
//        if (userWithFavorites == null) return
//
//        val isFavorite = userWithFavorites.favoriteBooks.any { it.id == book.id }
//
//        if (isFavorite) {
//            favoriteDao.deleteUserFavoriteBook(userId, book.id)
//        } else {
//            val favoriteBook = FavoriteBookEntity(
//                id = book.id,
//                title = book.title,
//                coverUrl = book.coverUrl,
//                isRead = ReadStatus.valueOf(book.isRead.name)
//            )
//
//            favoriteDao.insertFavoriteBook(favoriteBook)
//            favoriteDao.insertUserFavoriteCrossRef(
//                UserFavoriteBookCrossRef(userId = userId, bookId = book.id)
//            )
//        }
//    }
//
//    suspend fun getUserFavorites(userId: Int): List<FavoriteBookEntity> {
//        val userWithFavorites = favoriteDao.getUserWithFavoriteBooks(userId)
//        return userWithFavorites?.favoriteBooks ?: emptyList()
//    }

}
