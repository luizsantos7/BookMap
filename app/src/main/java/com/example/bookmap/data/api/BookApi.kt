package com.example.bookmap.data.api

import com.example.bookmap.data.dto.response.BookResponse
import com.example.bookmap.data.dto.response.RootResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApi {
    @GET("books")
    suspend fun listarLivros(): RootResponse

    @GET("books?languages={language}")
    suspend fun listarLivrosPorLinguagem(@Path("language") language:String):RootResponse

    @GET("books")
    suspend fun listarLivrosPorNome(
        @Query("search") name: String
    ): RootResponse

    @GET("books/{id}")
    suspend fun pegarLivroPorId(@Path("id") id: String?): BookResponse
}