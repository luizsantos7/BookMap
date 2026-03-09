package com.example.bookmap.data.repository

import com.example.bookmap.data.models.BookDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    auth: FirebaseAuth,
    firestore: FirebaseFirestore
) : FavoriteRepository {
    override val userId = auth.currentUser?.uid ?: throw IllegalStateException("User not authenticated")
    override val favoritesCollection =
        firestore.collection("users").document(userId).collection("favorites")

    override suspend fun getFavoriteBooks(): List<BookDataModel> {
        val snapshot = favoritesCollection.get().await()
        return snapshot.toObjects(BookDataModel::class.java)
    }

    override fun toggleFavoriteBook(book: BookDataModel) {
        isFavorite(bookId = book.id) { isFav ->
            if (isFav) {
                removeFavorite(book)
            } else {
                addFavorite(book)
            }
        }
    }

    override fun isFavorite(bookId: Long, callback: (Boolean) -> Unit) {
        favoritesCollection.document(bookId.toString())
            .get()
            .addOnSuccessListener { doc ->
                callback(doc.exists())
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    override fun addFavorite(book: BookDataModel) {
        favoritesCollection
            .document(book.id.toString())
            .set(book)
        book.isFavorited = true
    }

    override fun removeFavorite(book: BookDataModel) {
        favoritesCollection.document(book.id.toString()).delete()
        book.isFavorited = false
    }
}
