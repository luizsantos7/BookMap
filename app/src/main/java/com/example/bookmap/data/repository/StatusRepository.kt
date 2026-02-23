package com.example.bookmap.data.repository

import com.example.bookmap.data.models.BookDataModel
import com.example.bookmap.data.models.BookDetailsDataModel
import com.example.bookmap.data.models.ReadStatusDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StatusRepository @Inject constructor(
    auth: FirebaseAuth,
    firestore: FirebaseFirestore
) {
    private val userId =
        auth.currentUser?.uid ?: throw IllegalStateException("User not authenticated")
    private val booksCollection =
        firestore.collection("users").document(userId).collection("books")

    fun addBook(book: BookDetailsDataModel) {
        booksCollection
            .document(book.id.toString())
            .set(book)
    }

//    fun updateBook(book: BookDetailsDataModel) {
//        booksCollection
//            .document(book.id.toString())
//            .update("read", book.isRead)
//    }

    fun removeBook(bookId: String) {
        booksCollection
            .document(bookId)
            .delete()
    }

    suspend fun getBooks(): List<BookDataModel> {
        val snapshot = booksCollection
            .get()
            .await()
        return snapshot.toObjects(BookDataModel::class.java)
    }
}