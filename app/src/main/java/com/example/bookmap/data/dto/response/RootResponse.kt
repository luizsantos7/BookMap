package com.example.bookmap.data.dto.response

data class RootResponse(
    val count: Long,
    val next: String?,
    val previous: Any?,
    val results: List<BookResponse>,
)