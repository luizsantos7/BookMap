package com.example.bookmap.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookmap.data.entity.BookEntity
import com.example.bookmap.data.entity.enum.CountType
import com.example.bookmap.data.repository.BookRepository
import com.example.bookmap.presentation.home.HomeScreenAction.ClickSearchIcon
import com.example.bookmap.presentation.home.HomeScreenAction.GetBookBySearch
import com.example.bookmap.presentation.home.HomeScreenAction.OnFavorited
import com.example.bookmap.presentation.home.HomeScreenAction.OnRetry
import com.example.bookmap.presentation.home.HomeScreenAction.OnSearchABook
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val bookRepository: BookRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState

    private var searchJob: Job? = null

    init {
        getBooks()
    }

    fun onActionEvent(action: HomeScreenAction) {
        when (action) {
            is ClickSearchIcon -> onClickSearchIcon()
            is OnSearchABook -> onSearchBook(bookName = action.bookName)
            is GetBookBySearch -> onGetBookByName()
            is OnFavorited -> favoriteBook(action.book)
            OnRetry -> getBooks()
        }
    }

    private fun favoriteBook(book: BookEntity) {
        if (_uiState.value.user.countType == CountType.USER) {
            book.isFavorited = true
            _uiState.value.user.favoritedBooks.add(book)
        }
    }

    private fun getBooks() {
        _uiState.update { it.copy(isLoading = true, showError = false, isContinue = false) }

        viewModelScope.launch {
            bookRepository.buscarTodosLivros()
                .onSuccess { books ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            showError = false,
                            isContinue = true,
                            listBook = books,
                            filteredBooks = books
                        )
                    }
                }
                .onFailure { error ->
                    error.printStackTrace()
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            showError = true,
                            isContinue = false,
                            errorMessage = error.message ?: "Erro ao carregar livros"
                        )
                    }
                }
        }
    }

    private fun onGetBookByName() {
        if (uiState.value.searchBookText.isBlank()) {
            getBooks()
            return
        }
        _uiState.update { it.copy(isLoading = true, showError = false) }

        viewModelScope.launch {
            bookRepository.buscarLivroPorNome(uiState.value.searchBookText)
                .onSuccess { books ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            showError = false,
                            filteredBooks = books,
                            isContinue = true
                        )
                    }
                }
                .onFailure {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            showError = true,
                            isContinue = false,
                            errorMessage = "Nenhum livro foi encontrado!"
                        )
                    }
                }
        }
    }

    private fun onClickSearchIcon() {
        if (_uiState.value.searchBook) {
            _uiState.update {
                it.copy(
                    searchBook = false,
                    searchBookText = ""
                )
            }
            getBooks()
        } else {
            _uiState.update { it.copy(searchBook = true) }
        }
    }

    private fun onSearchBook(bookName: String) {
        _uiState.update { it.copy(searchBookText = bookName) }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            onGetBookByName()
        }
    }
}