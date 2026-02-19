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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.bookmap.data.models.BookDataModel
import com.example.bookmap.presentation.home.HomeViewModel
import com.example.bookmap.utils.card.BookCard
import com.example.bookmap.utils.components.Footer
import com.example.bookmap.utils.components.NavBarComponent

/**
 * Tela de Favoritos — reutiliza o mesmo ViewModel para acessar os livros favoritados
 * já carregados no uiState (favoritedBooks).
 */
@Composable
fun FavoritesScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel(), // pode ser o mesmo da Home se o estado for compartilhado
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FavoritesScreenContent(
        uiState = uiState,
        navController = navController,
        onFavorited = { book -> viewModel.onActionEvent(com.example.bookmap.presentation.home.HomeScreenAction.OnFavorited(book)) },
        modifier = modifier
    )
}

@Composable
private fun FavoritesScreenContent(
    uiState: com.example.bookmap.presentation.home.HomeUiState,
    navController: NavController,
    modifier: Modifier = Modifier,
    onFavorited: (BookDataModel) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF171D23))
    ) {
        // Cabeçalho simples
        NavBarComponent(onClick = { /* opcional: botão de voltar, etc */ })

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

            uiState.favoritedBooks.isEmpty() -> {
                // Estado vazio: nenhum livro favoritado ainda
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
                // Lista de livros favoritos
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    items(uiState.favoritedBooks) { book ->
                        BookCard(
                            title = book.title,
                            author = book.authors,
                            imageCover = book.coverUrl,
                            onFavorited = { onFavorited(book) }, // permite remover dos favoritos
                            isFavorited = book.isFavorited
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