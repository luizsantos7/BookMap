package com.example.bookmap.data.mapper

import com.example.bookmap.data.dto.response.AuthorResponse
import com.example.bookmap.data.dto.response.BookResponse
import com.example.bookmap.data.models.AuthorDataModel
import com.example.bookmap.data.models.BookDataModel
import javax.inject.Inject

class BookMapper @Inject constructor() {

    fun mapResponseToDataModel(response: BookResponse, favoriteBooks: List<BookDataModel>): BookDataModel {
        return BookDataModel(
            id = response.id,
            title = response.title,
            authors = response.authors.map { mapAuthor(it) },
            summaries = response.summaries,
            languages = response.languages,
            coverUrl = response.formats.imageJpeg,
            isFavorited = favoriteBooks.any { it.id == response.id }
        )
    }

    private fun mapAuthor(author: AuthorResponse): AuthorDataModel {
        return AuthorDataModel(
            name = author.name,
            birthYear = author.birthYear,
            deathYear = author.deathYear
        )
    }
}