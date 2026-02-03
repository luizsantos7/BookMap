package com.example.bookmap.data.mapper

import com.example.bookmap.data.dto.response.AuthorResponse
import com.example.bookmap.data.entity.AuthorEntity

class AuthorMapper {
    fun mapAuthorResponseToEntity(authorResponse: AuthorResponse): AuthorEntity {
        return AuthorEntity(
            name = authorResponse.name,
            birthYear = authorResponse.birthYear,
            deathYear = authorResponse.deathYear
        )
    }
}