package com.example.bookmap.data.mapper

import com.example.bookmap.data.dto.response.BookResponse
import com.example.bookmap.data.entity.AuthorEntity
import com.example.bookmap.data.entity.BookEntity
import com.example.bookmap.data.entity.enum.ReadStatus


fun BookResponse.toEntity(): BookEntity {
    return BookEntity(
        id = id.toInt(),
        title = title,
        authors = authors.map {
            AuthorEntity(
                name = it.name,
                birthYear = it.birthYear?.toInt(),
                deathYear = it.deathYear?.toInt()
            )
        },
        summaries = summaries,
        languages = languages,
        coverUrl = formats.imageJpeg,
        isRead = ReadStatus.UNREAD
    )
}