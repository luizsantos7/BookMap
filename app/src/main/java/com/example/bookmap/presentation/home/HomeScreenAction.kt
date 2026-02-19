package com.example.bookmap.presentation.home

import com.example.bookmap.data.models.BookDataModel

sealed interface HomeScreenAction {

    data object ClickSearchIcon: HomeScreenAction
    data class OnSearchABook(val bookName : String) : HomeScreenAction

    data class GetBookBySearch(val bookName: String) : HomeScreenAction
    data object OnRetry : HomeScreenAction
    data class OnFavorited(val book : BookDataModel) : HomeScreenAction
}