package com.example.bookmap.presentation.favorite

import com.example.bookmap.data.models.BookDataModel

sealed interface FavoriteScreenAction {
    data class OnRemoveFromFavorite(val book: BookDataModel) : FavoriteScreenAction

}