package com.example.bookmap.data.models

data class BookDetailsDataModel(
    val id: Long = 0L,
    val title: String = "",
    val authors: List<AuthorDataModel> = emptyList(),
    val summaries: List<String> = emptyList(),
    val languages: List<String> = emptyList(),
    val copyright: Boolean = false,
    val coverUrl: String? = null,
    val isRead: ReadStatusDataModel = ReadStatusDataModel.UNREAD,
)