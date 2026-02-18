package com.example.bookmap.presentation.favorite

import com.example.bookmap.data.entity.UserEntity

sealed interface FavoriteScreenAction{
    data class SetUser(val user : UserEntity) : FavoriteScreenAction
}