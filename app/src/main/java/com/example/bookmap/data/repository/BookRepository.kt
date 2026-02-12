package com.example.bookmap.data.repository

import com.example.bookmap.data.api.BookApi
import com.example.bookmap.data.entity.BookEntity
import com.example.bookmap.data.mapper.toEntity
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val bookApi: BookApi
) {
    suspend fun buscarLivroPorNome(name: String): Result<List<BookEntity>> = runCatching {
        bookApi.listarLivrosPorNome(name).results.map { it.toEntity() }
    }

    suspend fun buscarTodosLivros(): Result<List<BookEntity>> = runCatching {
        bookApi.listarLivros().results.map { it.toEntity() }
    }
}