package com.example.bookmap.presentation.profile

import com.example.bookmap.data.models.BookDataModel

sealed interface ProfileScreenAction {
    data object LoadProfileData : ProfileScreenAction
    data class removeBook (val book : BookDataModel) : ProfileScreenAction

}