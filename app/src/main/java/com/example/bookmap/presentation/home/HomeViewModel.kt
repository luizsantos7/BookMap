package com.example.bookmap.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookmap.data.repository.BookRepository
import com.example.bookmap.presentation.home.HomeScreenAction.ClickSearchIcon
import com.example.bookmap.presentation.home.HomeScreenAction.getBookBySearch
import com.example.bookmap.presentation.home.HomeScreenAction.onSearchABook
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun onActionEvent(action: HomeScreenAction) {
        when (action) {
            is ClickSearchIcon -> onClickSearchIcon()
            is onSearchABook -> onSearchBook(bookName = action.bookName)
            is getBookBySearch -> onGetBookByName(name = action.bookName)
        }
    }

    fun getBooks() {
        _uiState.update { it.copy(isLoading = true, showError = false, isContinue = false) }

        viewModelScope.launch {
            bookRepository.buscarTodosLivros()
                .onSuccess { books ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            showError = false,
                            isContinue = true,
                            listBook = books
                        )
                    }
                }
                .onFailure { error ->
                    error.printStackTrace()
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            showError = true,
                            isContinue = false
                        )
                    }
                }
        }
    }

    fun onGetBookByName(name: String) {
        _uiState.update { it.copy(isContinue = false, isLoading = true, showError = false) }

        viewModelScope.launch {
            bookRepository.buscarLivroPorNome(name)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            showError = false,
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

    fun onClickSearchIcon() {
        if (!_uiState.value.searchBook)
            _uiState.update { it.copy(searchBook = true) }
        else
            _uiState.update { it.copy(searchBook = false) }
    }

    fun onSearchBook(bookName: String) {
        _uiState.update { it.copy(searchBookText = bookName) }
    }

}

