package com.example.bookmap.data.dto.response

import com.google.gson.annotations.SerializedName

data class TranslatorResponse(
    val name: String,
    @SerializedName("birth_year")
    val birthYear: Long,
    @SerializedName("death_year")
    val deathYear: Long,
)