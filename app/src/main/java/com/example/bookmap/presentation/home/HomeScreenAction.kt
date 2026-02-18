package com.example.bookmap.presentation.home

import com.example.bookmap.data.entity.BookEntity
import com.example.bookmap.data.entity.UserEntity

sealed interface HomeScreenAction {

    data object ClickSearchIcon: HomeScreenAction
    data class OnSearchABook(val bookName : String) : HomeScreenAction

    data class GetBookBySearch(val bookName: String) : HomeScreenAction
    data object OnRetry : HomeScreenAction
    data class OnFavorited(val book : BookEntity) : HomeScreenAction
    data class SetUser(val user : UserEntity) : HomeScreenAction
}