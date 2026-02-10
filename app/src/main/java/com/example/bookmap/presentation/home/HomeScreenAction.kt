package com.example.bookmap.presentation.home

sealed class HomeScreenAction{

    data object ClickSearchIcon: HomeScreenAction()
    data class onSearchABook(val bookName : String) : HomeScreenAction()
}