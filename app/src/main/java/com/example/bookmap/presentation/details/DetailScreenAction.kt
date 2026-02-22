package com.example.bookmap.presentation.details

import com.example.bookmap.data.models.ReadStatusDataModel

sealed interface DetailScreenAction{
    data class LoadBookDetails(val bookId: String?) : DetailScreenAction
    data class OnStatusChange(val status: ReadStatusDataModel ) : DetailScreenAction
}