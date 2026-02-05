package com.example.bookmap.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bookmap.data.entity.AuthorEntity
import com.example.bookmap.data.entity.BookEntity
import com.example.bookmap.presentation.Book.BookUiState
import com.example.bookmap.presentation.Book.BookViewModel

@Composable
fun BookScreen(
    viewModel: BookViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.bookState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getBooks(id = 23000)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            is BookUiState.Initial -> Text("Nenhum livro carregado ainda.")

            is BookUiState.Loading -> CircularProgressIndicator()

            is BookUiState.Success -> {
                val book = (uiState as BookUiState.Success).bookEntity
                BookDetailsContent(book)
            }

            is BookUiState.Error -> {
                val message = (uiState as BookUiState.Error).message
                Text(
                    text = "Erro: $message",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun BookDetailsContent(book : BookEntity) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = book.title,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Autores
        if (book.authors.isNotEmpty()) {
            item {
                Text(
                    text = "Autores:",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }
            items(book.authors) { author: AuthorEntity ->
                Text(
                    text = "• ${author.name}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // Línguas
        if (book.languages.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Idiomas:",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }
            items(book.languages) { lang ->
                Text(
                    text = "• $lang",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // Sumários
        if (book.summaries.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Sinopses:",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }
            items(book.summaries) { summary ->
                Text(
                    text = "• $summary",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // ID (opcional para debug)
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "ID: ${book.id}",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
        }
    }
}