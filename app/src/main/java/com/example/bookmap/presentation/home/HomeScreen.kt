package com.example.bookmap.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.bookmap.data.entity.AuthorEntity
import com.example.bookmap.data.entity.BookEntity
import com.example.bookmap.data.entity.enum.ReadStatus
import com.example.bookmap.utils.card.BookCard
import com.example.bookmap.utils.components.ErrorContent
import com.example.bookmap.utils.components.Footer
import com.example.bookmap.utils.components.NavBarComponent
import com.example.bookmap.utils.components.OutlineTextComponent
import com.example.bookmap.utils.ui.theme.UnfocusField
import com.example.bookmap.utils.ui.theme.focusFieldBorder

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getBooks()
    }

    HomeScreenContent(
        uiState = uiState,
        onSearchClick = { viewModel.onActionEvent(HomeScreenAction.ClickSearchIcon) },
        onSearchBook = { bookName ->
            viewModel.onActionEvent(HomeScreenAction.onSearchABook(bookName))
        },
        modifier = modifier,
        onRetry = { viewModel.getBooks() }
    )


}

@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    onSearchClick: () -> Unit,
    onRetry: () -> Unit,
    onSearchBook: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF171D23))
    ) {
        NavBarComponent(onClick = onSearchClick)

        AnimatedVisibility(
            visible = uiState.searchBook,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> -fullHeight }
            ) + expandVertically(),
            exit = slideOutVertically(
                targetOffsetY = { fullHeight -> -fullHeight }
            ) + shrinkVertically()
        ) {
            OutlineTextComponent(
                value = uiState.searchBookText,
                onValueChange = onSearchBook,
                textColor = Color.Gray,
                backgroundColor = UnfocusField,
                focusedBorderColor = focusFieldBorder,
                unfocusedBorderColor = UnfocusField,
                placeholder = "Buscar livros, autores...",
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = when {
                uiState.isLoading || uiState.showError -> Arrangement.Center
                else -> Arrangement.Top
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                uiState.isLoading -> {
                    item {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            CircularProgressIndicator(color = Color.White)
                            Text(
                                text = "Carregando livros...",
                                color = Color.Gray,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                        }
                    }
                }

                uiState.showError -> {
                    item {
                        ErrorContent(
                            errorMessage = uiState.errorMessage,
                            onRetry = onRetry
                        )
                    }
                }
                else -> {
                    items(uiState.listBook) { item ->
                        BookCard(
                            title = item.title,
                            author = item.authors,
                            imageCover = item.coverUrl
                        )
                    }
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.Bottom
        ) {
            Footer(onClick = {})
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF171D23)
@Composable
private fun HomePrev() {
    val fakeUiState = HomeUiState(
        searchBook = false,
        listBook = listOf(
            BookEntity(
                id = 1,
                title = "Frankenstein",
                authors = listOf(
                    AuthorEntity(
                        name = "Mary Shelley",
                        birthYear = 1797,
                        deathYear = 1851
                    )
                ),
                coverUrl = "https://www.gutenberg.org/cache/epub/84/pg84.cover.medium.jpg",
                summaries = emptyList(),
                languages = listOf("en"),
                isRead = ReadStatus.UNREAD
            ),
            BookEntity(
                id = 2,
                title = "Pride and Prejudice",
                authors = listOf(
                    AuthorEntity(
                        name = "Jane Austen",
                        birthYear = 1775,
                        deathYear = 1817
                    )
                ),
                coverUrl = "https://www.gutenberg.org/cache/epub/1342/pg1342.cover.medium.jpg",
                summaries = emptyList(),
                languages = listOf("en"),
                isRead = ReadStatus.UNREAD
            )
        )
    )

    HomeScreenContent(
        uiState = fakeUiState,
        onSearchClick = {},
        onSearchBook = {},
        onRetry =  {}
    )
}