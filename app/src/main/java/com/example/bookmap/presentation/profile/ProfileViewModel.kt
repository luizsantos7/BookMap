package com.example.bookmap.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookmap.data.models.BookDataModel
import com.example.bookmap.data.models.ReadStatusDataModel.DROPPED
import com.example.bookmap.data.models.ReadStatusDataModel.PAUSED
import com.example.bookmap.data.models.ReadStatusDataModel.READ
import com.example.bookmap.data.models.ReadStatusDataModel.READING
import com.example.bookmap.data.models.ReadStatusDataModel.UNREAD
import com.example.bookmap.data.repository.StatusRepository
import com.example.bookmap.data.repository.UserRepository
import com.example.bookmap.presentation.profile.ProfileScreenAction.LoadProfileData
import com.example.bookmap.presentation.profile.ProfileScreenAction.removeBook
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

    fun onActionEvent(action: ProfileScreenAction) {
        when (action) {
            is LoadProfileData -> loadProfileData()
            is removeBook -> removeClicked(action.book)
        }
    }

    private fun removeClicked(book: BookDataModel) {
        _UiState.update { state ->
            when (book.isRead) {
                READING -> {
                    statusRepository.removeBook(book.id.toString())
                    state.copy(readingBooks = state.readingBooks.filter { it.id != book.id })
                }

                READ -> {
                    statusRepository.removeBook(book.id.toString())
                    state.copy(readBooks = state.readBooks.filter { it.id != book.id })
                }

                UNREAD -> {
                    statusRepository.removeBook(book.id.toString())
                    state.copy(unreadBooks = state.unreadBooks.filter { it.id != book.id })
                }

                PAUSED -> {
                    statusRepository.removeBook(book.id.toString())
                    state.copy(pausedBooks = state.pausedBooks.filter { it.id != book.id })
                }

                DROPPED -> {
                    statusRepository.removeBook(book.id.toString())
                    state.copy(droppedBooks = state.droppedBooks.filter { it.id != book.id })
                }
            }
        }
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


    private fun loadProfileData() {
        viewModelScope.launch {
            _UiState.update { it.copy(isLoading = true, showError = false) }

            try {
                val books = statusRepository.getBooks()

                _UiState.update { current ->
                    current.copy(
                        readingBooks = books.filter { it.isRead == READING },
                        readBooks = books.filter { it.isRead == READ },
                        unreadBooks = books.filter { it.isRead == UNREAD },
                        pausedBooks = books.filter { it.isRead == PAUSED },
                        droppedBooks = books.filter { it.isRead == DROPPED },
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