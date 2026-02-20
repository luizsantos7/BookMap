package com.example.bookmap.presentation.details

sealed interface DetailScreenAction{
    data class LoadBookDetails(val bookId: String?) : DetailScreenAction
}