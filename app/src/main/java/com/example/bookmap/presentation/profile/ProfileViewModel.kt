package com.example.bookmap.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookmap.data.models.ReadStatusDataModel
import com.example.bookmap.data.repository.StatusRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val statusRepository: StatusRepository
) : ViewModel() {
    private val _UiState = MutableStateFlow(ProfileUiState())
    val uiState = _UiState


    init {
        loadUserData()
    }

    private fun loadUserData() {
        val user = auth.currentUser
        _UiState.update { it.copy(isLoading = true, showError = false) }

        _UiState.update {
            it.copy(
                user = it.user.copy(
                    email = user?.email ?: "",
                    profile = it.user.profile.copy(
                        name = user?.displayName ?: ""
                    )
                ),
                isLoading = false,
            )
        }
    }

    fun loadProfileData() {
        viewModelScope.launch {
            _UiState.update { it.copy(isLoading = true, showError = false) }

            try {
                val reading = statusRepository.getBooksByStatus(ReadStatusDataModel.READING)
                val read = statusRepository.getBooksByStatus(ReadStatusDataModel.READ)
                val unread = statusRepository.getBooksByStatus(ReadStatusDataModel.UNREAD)
                val paused = statusRepository.getBooksByStatus(ReadStatusDataModel.PAUSED)
                val dropped = statusRepository.getBooksByStatus(ReadStatusDataModel.DROPPED)

                _UiState.update { current ->
                    current.copy(
                        readingBooks = reading,
                        readBooks = read,
                        unreadBooks = unread,
                        pausedBooks = paused,
                        droppedBooks = dropped,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _UiState.update { current ->
                    current.copy(
                        isLoading = false,
                        showError = true,
                        errorMessage = e.message ?: "Erro ao carregar dados"
                    )
                }
            }
        }
    }
}