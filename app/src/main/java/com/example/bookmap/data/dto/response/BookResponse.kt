package com.example.bookmap.data.dto.response

import com.google.gson.annotations.SerializedName

data class BookResponse(
    val id: Long,
    val title: String,
    val authors: List<AuthorResponse>,
    val summaries: List<String>,
    val editors: List<Any?>,
    val translators: List<TranslatorResponse>,
    val subjects: List<String>,
    val bookshelves: List<String>,
    val languages: List<String>,
    val copyright: Boolean,
    @SerializedName("media_type")
    val mediaType: String,
    val formats: Formats,
    @SerializedName("download_count")
    val downloadCount: Long,
)