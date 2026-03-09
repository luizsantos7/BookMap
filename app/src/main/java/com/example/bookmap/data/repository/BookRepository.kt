package com.example.bookmap.data.repository

import com.example.bookmap.data.models.BookDataModel
import com.example.bookmap.data.models.BookDetailsDataModel

interface BookRepository {
    var favoriteBooks: List<BookDataModel>
    suspend fun buscarLivroPorNome(name: String): Result<List<BookDataModel>>
    suspend fun buscarLivroPorId(id: String?): Result<BookDetailsDataModel>
    suspend fun buscarTodosLivros(page: Int = 1): Result<List<BookDataModel>>
}