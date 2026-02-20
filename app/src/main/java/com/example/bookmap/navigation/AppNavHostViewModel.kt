package com.example.bookmap.navigation

import androidx.lifecycle.ViewModel
import androidx.room.Insert
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AppNavHostViewModel @Inject constructor(
    auth: FirebaseAuth
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        UiState(
            startDestination = if (auth.currentUser != null) "home_screen" else "login_screen"
        )
    )
    val uiState = _uiState.asStateFlow()
}

data class UiState(
    val startDestination: String
)
