package com.example.bookmap.data.repository

import com.example.bookmap.data.api.BookApi
import com.example.bookmap.data.mapper.BookMapper
import com.example.bookmap.data.models.BookDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val bookApi: BookApi,
    private val bookMapper: BookMapper,
    auth: FirebaseAuth,
    firestore: FirebaseFirestore
) {

    private var favoriteBooks: List<BookDataModel> = listOf()

    init {
        val userId = auth.currentUser?.uid.orEmpty()

        val query = firestore.collection("users")
            .document(userId)
            .collection("favorites")
            .orderBy("ts", Query.Direction.DESCENDING)

        query.addSnapshotListener { snapshots, exception ->
            if (exception != null) return@addSnapshotListener

            favoriteBooks = snapshots?.toObjects(BookDataModel::class.java) ?: emptyList()
        }
    }

    suspend fun buscarLivroPorNome(name: String): Result<List<BookDataModel>> = runCatching {
        bookApi.listarLivrosPorNome(name).results.map {
            bookMapper.mapResponseToDataModel(it, favoriteBooks)
        }
    }

    suspend fun buscarTodosLivros(): Result<List<BookDataModel>> = runCatching {
        bookApi.listarLivros().results.map {
            bookMapper.mapResponseToDataModel(it, favoriteBooks)
        }
    }
}