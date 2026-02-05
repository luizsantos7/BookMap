package com.example.bookmap.data.mapper

import com.example.bookmap.data.dto.response.BookResponse
import com.example.bookmap.data.entity.BookEntity

fun BookResponse.toEntity(): BookEntity {
    val coverUrl = formats["image/jpeg"]

    return BookEntity(
        id = id,
        title = title,
        authors = authors.map {
            AuthorMapper().mapAuthorResponseToEntity(authorResponse = it)
        },
        summaries = summaries,
        languages = languages,
        coverUrl = coverUrl
    )
}