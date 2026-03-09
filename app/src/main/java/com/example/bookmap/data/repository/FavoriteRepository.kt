package com.example.bookmap.data.repository

import com.example.bookmap.data.models.BookDataModel
import com.google.firebase.firestore.CollectionReference

interface FavoriteRepository {
    val userId: String
    val favoritesCollection: CollectionReference
    suspend fun getFavoriteBooks(): List<BookDataModel>
    fun toggleFavoriteBook(book: BookDataModel)
    fun isFavorite(bookId: Long, callback: (Boolean) -> Unit)
    fun addFavorite(book: BookDataModel)
    fun removeFavorite(book: BookDataModel)
}