package com.example.bookmap.data.api

import com.example.bookmap.data.dto.response.BookResponse
import retrofit2.http.GET

interface BookApi {
    @GET("books")
    fun listarLivros(): List<BookResponse>
}