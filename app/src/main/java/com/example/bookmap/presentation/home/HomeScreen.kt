package com.example.bookmap.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.bookmap.data.models.BookDataModel
import com.example.bookmap.presentation.home.HomeScreenAction.*
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
        onFavorited = { book -> viewModel.onActionEvent(OnFavorited(book)) },
        onRetry = { viewModel.onActionEvent(OnRetry) },
    )
}

@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    navController: NavController,
    onSearchClick: () -> Unit,
    onRetry: () -> Unit,
    onSearchBook: (String) -> Unit,
    modifier: Modifier = Modifier,
    onFavorited: (BookDataModel) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF171D23))
    ) {
        NavBarComponent(onClick = onSearchClick)

        AnimatedVisibility(
            visible = uiState.searchBook,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlineTextComponent(
                    value = uiState.searchBookText,
                    onValueChange = onSearchBook,
                    textColor = Color.Gray,
                    backgroundColor = UnfocusField,
                    focusedBorderColor = focusFieldBorder,
                    unfocusedBorderColor = UnfocusField,
                    placeholder = "Buscar livros, autores...",
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

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
                    errorMessage = uiState.errorMessage,
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                ) {
                    items(uiState.filteredBooks) { item ->
                        BookCard(
                            title = item.title,
                            author = item.authors,
                            imageCover = item.coverUrl,
                            onFavorited = { onFavorited(item) },
                            isFavorited = item.isFavorited
                        )
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