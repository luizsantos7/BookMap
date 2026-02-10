package com.example.bookmap.data.repository

import com.example.bookmap.data.api.BookApi
import com.example.bookmap.data.entity.BookEntity
import com.example.bookmap.data.mapper.toEntity
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val bookApi: BookApi
) {
    suspend fun buscarLivroPorId(id: Int): Result<BookEntity> {
        return try {
            val response = bookApi.listarLivroPorId(id)
            val book = response.toEntity()
            Result.success(book)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun buscarTodosLivros(): Result<List<BookEntity>> = runCatching {
        bookApi.listarLivros().results.map { it.toEntity() }
    }
}