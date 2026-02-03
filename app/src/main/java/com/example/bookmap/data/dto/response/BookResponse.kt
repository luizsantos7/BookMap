package com.example.bookmap.data.dto.response

data class BookResponse(
    val id: Int,
    val title: String,
    val authors: List<AuthorResponse>,
    val summaries: List<String>,
    val editors: List<String>,
    val translators: List<String>,
    val subjects: List<String>,
    val bookshelves: List<String>,
    val languages: List<String>,
    val copyright: Boolean,
    val mediaType: String,
    val formats: Map<String, String>,
    val downloadCount: Int
)