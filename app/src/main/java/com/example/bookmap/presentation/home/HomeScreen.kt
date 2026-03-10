package com.example.bookmap.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.bookmap.data.models.BookDataModel
import com.example.bookmap.presentation.home.HomeScreenAction.ClickSearchIcon
import com.example.bookmap.presentation.home.HomeScreenAction.NextPage
import com.example.bookmap.presentation.home.HomeScreenAction.OnFavorite
import com.example.bookmap.presentation.home.HomeScreenAction.OnRetry
import com.example.bookmap.presentation.home.HomeScreenAction.OnSearchABook
import com.example.bookmap.utils.card.BookCard
import com.example.bookmap.utils.components.BookStatusRow
import com.example.bookmap.utils.components.ErrorContent
import com.example.bookmap.utils.components.Footer
import com.example.bookmap.utils.components.NavBarComponent
import com.example.bookmap.utils.constants.THREE
import com.example.bookmap.utils.ui.theme.BackgroundBlack

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreenContent(
        uiState = uiState,
        onSearchClick = { viewModel.onActionEvent(ClickSearchIcon) },
        onSearchBook = { bookName ->
            viewModel.onActionEvent(OnSearchABook(bookName))
        },
        navController = navController,
        modifier = modifier,
        onFavorited = { book -> viewModel.onActionEvent(OnFavorite(book)) },
        onRetry = { viewModel.onActionEvent(OnRetry) },
        onNextPage = { viewModel.onActionEvent(NextPage) },
    )
}

@Composable
@Suppress("LongMethod", "LongParameterList")
private fun HomeScreenContent(
    uiState: HomeUiState,
    navController: NavController,
    onSearchClick: () -> Unit,
    onRetry: () -> Unit,
    onSearchBook: (String) -> Unit,
    modifier: Modifier = Modifier,
    onFavorited: (BookDataModel) -> Unit,
    onNextPage: () -> Unit = { },
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundBlack)
    ) {
        NavBarComponent(
            onClick = onSearchClick,
            isSearchIconVisible = true,
            onValueChange = onSearchBook,
            value = uiState.searchBookText,
        )

        when {
            uiState.isLoading -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .weight(1f)
                ) {
                    CircularProgressIndicator(color = Color.White)
                    Text(
                        text = "Carregando livros...",
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }

            uiState.showError -> {
                ErrorContent(
                    modifier = Modifier.weight(1f),
                    errorMessage = "Erro ao buscar os livros...",
                    onRetry = onRetry
                )
            }

            uiState.filteredBooks.isEmpty() -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                        .weight(1f)
                ) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(64.dp)
                    )
                    Text(
                        text = "Nenhum livro encontrado",
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        text = "Tente buscar por outro termo",
                        color = Color.Gray.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            else -> {
                val listState = rememberLazyListState() // estado da lista para controle de rolagem

                LaunchedEffect(listState) {
                    snapshotFlow {
                        listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                    } // o snapshotFlow observa um valor do compose e emite em flow sempre que ele muda.
                        .collect { lastVisibleItemIndex -> // collect coleta o valor resultado pelo snapshotFlow
                            val totalItems = listState.layoutInfo.totalItemsCount

                            if (lastVisibleItemIndex != null &&
                                lastVisibleItemIndex >= totalItems - THREE &&
                                !uiState.isLoading && !uiState.searchBook
                            ) {
                                onNextPage()
                            }
                        }
                }

                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                ) {
                    item {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                        ) {
                            val visibility = uiState.readingBooks.isNotEmpty() and !uiState.searchBook
                            AnimatedVisibility(visible = visibility) {
                                Text(
                                    text = "Continue Lendo",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                                Spacer(Modifier.height(26.dp))
                                BookStatusRow(
                                    bookList = uiState.readingBooks,
                                    modifier = Modifier.fillMaxWidth(),
                                )
                            }

                        }
                    }
                    items(uiState.filteredBooks) { item ->
                        BookCard(
                            title = item.title,
                            author = item.authors,
                            imageCover = item.coverUrl,
                            onFavorited = { onFavorited(item) },
                            isFavorited = item.isFavorited,
                            onDetails = { navController.navigate("details/${item.id}") }
                        )
                    }

                    if (uiState.listBookNewPageLoading) {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator(color = Color.White)
                                Text("Carregando mais livros...", color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.Bottom
        ) {
            Footer(navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val fakeState = HomeUiState(
        isLoading = false,
        showError = false,
        searchBook = true,
    )

    HomeScreenContent(
        uiState = fakeState,
        navController = NavController(LocalContext.current),
        onSearchClick = {},
        onRetry = {},
        onSearchBook = {},
        onFavorited = {},
        onNextPage = {}
    )
}
