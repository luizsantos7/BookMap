package com.example.bookmap.data.api

import com.example.bookmap.data.dto.response.BookResponse
import com.example.bookmap.data.dto.response.RootResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApi {
    @GET("books")
    suspend fun listarLivros(): RootResponse

    @GET("books/{id}")
    suspend fun listarLivroPorId(@Path("id") id:Int):BookResponse

    @GET("books?languages={language}")
    suspend fun listarLivrosPorLinguagem(@Path("language") id:String):BookResponse

}