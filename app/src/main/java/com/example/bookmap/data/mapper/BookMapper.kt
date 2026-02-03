package com.example.bookmap.data.mapper

import com.example.bookmap.data.dto.response.BookResponse
import com.example.bookmap.data.entity.BookEntity

class BookMapper {
    fun mapBookResponseToEntity(bookResponse: BookResponse): BookEntity {
        return BookEntity(
            id = bookResponse.id,
            title = bookResponse.title,
            authors = bookResponse.authors.map {
                AuthorMapper().mapAuthorResponseToEntity(authorResponse = it)
            },
            summaries = bookResponse.summaries,
            languages = bookResponse.languages
        )
    }
}