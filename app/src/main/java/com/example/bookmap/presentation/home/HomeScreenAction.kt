package com.example.bookmap.presentation.home

sealed interface HomeScreenAction {

    data object ClickSearchIcon: HomeScreenAction
    data class OnSearchABook(val bookName : String) : HomeScreenAction

    data class GetBookBySearch(val bookName: String) : HomeScreenAction
    data object OnRetry : HomeScreenAction
}