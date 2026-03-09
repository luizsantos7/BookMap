package com.example.bookmap.data.repository

import com.example.bookmap.data.models.BookDataModel
import com.example.bookmap.data.models.BookDetailsDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StatusRepositoryImpl @Inject constructor(
    auth: FirebaseAuth,
    firestore: FirebaseFirestore
) : StatusRepository {
    override val userId =
        auth.currentUser?.uid ?: throw IllegalStateException("User not authenticated")
    override val booksCollection =
        firestore.collection("users").document(userId).collection("books")

    override fun addBook(book: BookDetailsDataModel) {
        booksCollection
            .document(book.id.toString())
            .set(book)
    }

    override fun removeBook(bookId: String) {
        booksCollection
            .document(bookId)
            .delete()
    }

    override suspend fun getBooks(): List<BookDataModel> {
        val snapshot = booksCollection
            .get()
            .await()
        return snapshot.toObjects(BookDataModel::class.java)
    }
}
