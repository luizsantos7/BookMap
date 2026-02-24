package com.example.bookmap.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookmap.data.models.ReadStatusDataModel
import com.example.bookmap.data.repository.StatusRepository
import com.example.bookmap.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val statusRepository: StatusRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _UiState = MutableStateFlow(ProfileUiState())
    val uiState = _UiState


    init {
        loadUserData()
    }

    private fun loadUserData() {
        val user = auth.currentUser ?: return

        _UiState.update { it.copy(isLoading = true, showError = false) }

        _UiState.update {
            userRepository.loadUserProfile(
                uid = user.uid,
                onSuccess = { profile ->
                    _UiState.update {
                        it.copy(
                            user = it.user.copy(
                                email = user.email ?: "",
                                profile = profile
                            ),
                            isLoading = false,
                        )
                    }
                },
                onFailure = {
                    _UiState.update {
                        it.copy(
                            isLoading = false,
                            showError = true,
                        )
                    }
                }
            )
            it.copy(
                user = it.user.copy(
                    email = user.email ?: "",
                    profile = it.user.profile
                ),
                isLoading = false,
            )
        }
    }

    fun loadProfileData() {
        viewModelScope.launch {
            _UiState.update { it.copy(isLoading = true, showError = false) }

            try {
                val books = statusRepository.getBooks()

                _UiState.update { current ->
                    current.copy(
                        readingBooks = books.filter { it.isRead == ReadStatusDataModel.READING },
                        readBooks = books.filter { it.isRead == ReadStatusDataModel.READ },
                        unreadBooks = books.filter { it.isRead == ReadStatusDataModel.UNREAD },
                        pausedBooks = books.filter { it.isRead == ReadStatusDataModel.PAUSED },
                        droppedBooks = books.filter { it.isRead == ReadStatusDataModel.DROPPED },
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