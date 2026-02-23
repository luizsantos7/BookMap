package com.example.bookmap.data.repository

import android.util.Log
import com.example.bookmap.data.models.BookDataModel
import com.example.bookmap.data.models.BookDetailsDataModel
import com.example.bookmap.data.models.ReadStatusDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class StatusRepository @Inject constructor(
    auth: FirebaseAuth,
    firestore: FirebaseFirestore
) {
    private val userId =
        auth.currentUser?.uid ?: throw IllegalStateException("User not authenticated")
    private val statusCollection =
        firestore.collection("users").document(userId).collection("status")

    fun addStatus(status: ReadStatusDataModel, book: BookDetailsDataModel) {
        statusCollection
            .document(status.name)
            .collection("books")
            .document(book.id.toString())
            .set(book)
    }

    fun removeStatus(book: BookDetailsDataModel) {
        statusCollection
            .document(book.isRead.name)
            .collection("books")
            .document(book.id.toString())
            .delete()
    }

    suspend fun getBooksByStatus(status: ReadStatusDataModel): List<BookDataModel> {
        val snapshot = statusCollection
            .document(status.name)
            .collection("books")
            .get()
            .await()
        return snapshot.toObjects(BookDataModel::class.java)
    }
}