package com.example.bookmap.data.repository

import com.example.bookmap.data.models.BookDataModel
import com.example.bookmap.data.models.BookDetailsDataModel
import com.google.firebase.firestore.CollectionReference

interface StatusRepository {
    val userId: String
    val booksCollection: CollectionReference
    fun addBook(book: BookDetailsDataModel)
    fun removeBook(bookId: String)

    suspend fun getBooks(): List<BookDataModel>
}