package com.example.bookmap.data.models

data class BookDataModel(
    val id: Long,
    val title: String,
    val authors: List<AuthorDataModel>,
    val summaries: List<String>,
    val languages: List<String>,
    val coverUrl: String? = null,
    val isRead: ReadStatusDataModel = ReadStatusDataModel.UNREAD,
    var isFavorited : Boolean = false,
)
