package com.example.bookmap.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookmap.data.models.BookDataModel
import com.example.bookmap.data.models.ReadStatusDataModel
import com.example.bookmap.data.repository.BookRepository
import com.example.bookmap.data.repository.FavoriteRepository
import com.example.bookmap.data.repository.StatusRepository
import com.example.bookmap.presentation.home.HomeScreenAction.BackPage
import com.example.bookmap.presentation.home.HomeScreenAction.ClickSearchIcon
import com.example.bookmap.presentation.home.HomeScreenAction.GetBookBySearch
import com.example.bookmap.presentation.home.HomeScreenAction.NextPage
import com.example.bookmap.presentation.home.HomeScreenAction.OnFavorited
import com.example.bookmap.presentation.home.HomeScreenAction.OnRetry
import com.example.bookmap.presentation.home.HomeScreenAction.OnSearchABook
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val bookRepository: BookRepository,
    val favoriteRepository: FavoriteRepository,
    val statusRepository: StatusRepository,
    private val auth: FirebaseAuth
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState

    private val pageCache = mutableMapOf<Int, List<BookDataModel>>()

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
            NextPage -> nextPage()
            BackPage -> returnPage()
        }
    }

    private fun favoriteBook(book: BookDataModel) {
        auth.currentUser ?: return
        viewModelScope.launch {
            favoriteRepository.toggleFavoriteBook(book)
            _uiState.update { ui ->
                ui.copy(
                    filteredBooks = ui.filteredBooks.map {
                        if (it.id == book.id) it.copy(isFavorited = !it.isFavorited) else it
                    },
                    listBook = ui.listBook.map {
                        if (it.id == book.id) it.copy(isFavorited = !it.isFavorited) else it
                    }
                )
            }
        }
    }

    private fun getBooks() {
        _uiState.update { it.copy(isLoading = true, showError = false, isContinue = false) }

        viewModelScope.launch {
            val books = statusRepository.getBooks()

            _uiState.update {
                it.copy(
                    readingBooks = books.filter { it.isRead == ReadStatusDataModel.READING })
            }
            val favorites = favoriteRepository.getFavoriteBooks()
            val favoriteIds = favorites.map { it.id }.toSet()

            bookRepository.buscarTodosLivros(_uiState.value.page)
                .onSuccess { books ->
                    val booksWithFav = books.map { book ->
                        book.copy(isFavorited = favoriteIds.contains(book.id))
                    }

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            listBook = booksWithFav,
                            filteredBooks = booksWithFav,
                            showError = false,
                            isContinue = true
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            showError = true,
                            errorMessage = error.message ?: "Erro ao atualizar favoritos"
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

    private fun nextPage() {
        _uiState.update { it.copy(isLoading = true, showError = false) }

        viewModelScope.launch {
            val nextPage = uiState.value.page + 1

            val cachedBooks = pageCache[nextPage]
            if (cachedBooks != null) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        listBook = cachedBooks,
                        filteredBooks = cachedBooks,
                        page = nextPage
                    )
                }
                return@launch
            }

            bookRepository.buscarTodosLivros(nextPage)
                .onSuccess { books ->
                    if (books.isEmpty()) {
                        _uiState.update { it.copy(isLoading = false, isContinue = false) }
                    } else {
                        pageCache[nextPage] = books

                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                listBook = books,
                                filteredBooks = books,
                                page = nextPage
                            )
                        }
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            showError = true,
                            errorMessage = error.message ?: "Erro ao carregar mais livros"
                        )
                    }
                }
        }
    }

    private fun returnPage() {
        val currentPage = uiState.value.page
        if (currentPage <= 1) return

        _uiState.update { it.copy(isLoading = true, showError = false) }

        viewModelScope.launch {
            val previousPage = currentPage - 1

            val cachedBooks = pageCache[previousPage]
            if (cachedBooks != null) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        listBook = cachedBooks,
                        filteredBooks = cachedBooks,
                        page = previousPage
                    )
                }
                return@launch
            }

            bookRepository.buscarTodosLivros(previousPage)
                .onSuccess { books ->
                    pageCache[previousPage] = books
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            listBook = books,
                            filteredBooks = books,
                            page = previousPage
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            showError = true,
                            errorMessage = error.message ?: "Erro ao carregar livros anteriores"
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