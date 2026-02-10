package com.example.bookmap.data.entity

import com.example.bookmap.data.entity.enum.ReadStatus

data class BookEntity(
    val id: Int,
    val title: String,
    val authors: List<AuthorEntity>,
    val summaries: List<String>,
    val languages: List<String>,
    val coverUrl: String? = null,
    val isRead: ReadStatus = ReadStatus.UNREAD
)

