package com.example.bookmap.presentation.details

import com.example.bookmap.data.models.AuthorDataModel
import com.example.bookmap.data.models.BookDetailsDataModel
import com.example.bookmap.data.models.ReadStatusDataModel

data class DetailUiState (
    val book : BookDetailsDataModel = BookDetailsDataModel(
        id = 1L,
        title = "O Senhor dos Anéis:A Sociedade do Anel",
        authors = mockAuthors,
        summaries = mockSummaries,
        languages = listOf("Português", "Inglês"),
        copyright = false,
        coverUrl = "https://example.com/lotr_cover.jpg",
        isRead = ReadStatusDataModel.UNREAD,
    )
)

private val mockAuthors = listOf(
    AuthorDataModel(name = "J.R.R, Tolkien"),
    AuthorDataModel(name = "Christopher Tolkien")
)

private val mockSummaries = listOf(
    "Uma aventura épica na Terra‑média, acompanhando a jornada do Um Anel.",
    "Um dos maiores clássicos da literatura fantástica moderna."
)