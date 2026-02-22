package com.example.bookmap.data.models

import com.example.bookmap.utils.constants.EMPTY_STRING

data class BookDataModel(
    val id: Long = 0L,
    val title: String = EMPTY_STRING,
    val authors: List<AuthorDataModel> = emptyList(),
    val summaries: List<String> = emptyList(),
    val languages: List<String> = emptyList(),
    val coverUrl: String? = null,
    var isRead: ReadStatusDataModel = ReadStatusDataModel.UNREAD,
    var isFavorited : Boolean = false,
)
