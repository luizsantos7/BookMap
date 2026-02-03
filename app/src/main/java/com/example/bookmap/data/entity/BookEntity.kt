package com.example.bookmap.data.entity

data class BookEntity(
    val id: Int,
    val title: String,
    val authors: List<AuthorEntity>,
    val summaries: List<String>,
    val languages: List<String>,
)