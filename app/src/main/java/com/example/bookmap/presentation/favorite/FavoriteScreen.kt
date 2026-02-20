package com.example.bookmap.presentation.favorite


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.bookmap.data.models.AuthorDataModel
import com.example.bookmap.data.models.BookDataModel
import com.example.bookmap.data.models.ReadStatusDataModel
import com.example.bookmap.utils.card.BookCard
import com.example.bookmap.utils.components.Footer
import com.example.bookmap.utils.components.NavBarComponent


@Composable
fun FavoriteScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FavoritesScreenContent(
        uiState = uiState,
        navController = navController,
        onFavorited = { book -> viewModel.onActionEvent(FavoriteScreenAction.OnRemoveFromFavorite(book)) },
        modifier = modifier
    )
}

@Composable
private fun FavoritesScreenContent(
    uiState: FavoriteUiState,
    navController: NavController,
    modifier: Modifier = Modifier,
    onFavorited: (BookDataModel) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF171D23))
    ) {
        NavBarComponent(onClick = {  })

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
                    androidx.compose.material3.CircularProgressIndicator(color = Color.White)
                    Text(
                        text = "Carregando favoritos...",
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }

            uiState.listBook.isEmpty() -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                        .weight(1f)
                ) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = "Nenhum livro favoritado",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Você pode favoritar livros na tela inicial",
                        color = Color.Gray.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    items(uiState.listBook) { book ->
                        BookCard(
                            title = book.title,
                            author = book.authors,
                            imageCover = book.coverUrl,
                            onFavorited = { onFavorited(book) },
                            isFavorited = book.isFavorited,
                            onDetails = {navController.navigate("details/${book.id}")}
                        )
                    }
                }
            }
        }

        Column(verticalArrangement = Arrangement.Bottom) {
            Footer(navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteScreenEmptyPreview() {
    FavoritesScreenContent(
        uiState = FavoriteUiState(listBook = emptyList(), isLoading = false),
        navController = NavController(LocalContext.current),
        onFavorited = {}
    )
}

@Preview(showBackground = true)
@Composable
fun FavoriteScreenLoadingPreview() {
    FavoritesScreenContent(
        uiState = FavoriteUiState(listBook = emptyList(), isLoading = true),
        navController = NavController(LocalContext.current),
        onFavorited = {}
    )
}