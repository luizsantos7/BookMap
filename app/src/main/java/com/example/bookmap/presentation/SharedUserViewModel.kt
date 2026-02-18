package com.example.bookmap.presentation

import androidx.lifecycle.ViewModel
import com.example.bookmap.data.entity.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SharedUserViewModel @Inject constructor() : ViewModel() {
    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser: StateFlow<UserEntity?> = _currentUser.asStateFlow()

    fun setUser(user: UserEntity) {
        _currentUser.value = user
    }

    fun clearUser() {
        _currentUser.value = null
    }
}