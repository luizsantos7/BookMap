package com.example.bookmap.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bookmap.data.models.ReadStatusDataModel
import com.example.bookmap.utils.components.BookStatusRow
import com.example.bookmap.utils.components.ErrorContent
import com.example.bookmap.utils.components.Footer
import com.example.bookmap.utils.components.NavBarComponent

@Composable
fun ProfileScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileScreenContent(
        uiState = uiState,
        navController = navController,
        modifier = modifier,
    )

    LaunchedEffect(Unit) {
        viewModel.loadProfileData()
    }

}

@Composable
private fun ProfileScreenContent(
    uiState: ProfileUiState,
    navController: NavController,
    modifier: Modifier = Modifier,
) {

    val readingBooks = uiState.readingBooks
    val readBooks = uiState.readBooks
    val unreadBooks = uiState.unreadBooks
    val pausedBooks = uiState.pausedBooks
    val droppedBooks = uiState.droppedBooks

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF171D23))
    ) {
        NavBarComponent()

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
                        text = "Carregando perfil...",
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }

            uiState.showError -> {
                ErrorContent(
                    modifier = Modifier.weight(1f),
                    errorMessage = uiState.errorMessage,
                )
            }

            else -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .weight(1f)
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            8.dp,
                            Alignment.CenterVertically
                        ),
                    ) {
                        Image(
                            painter = painterResource(com.example.bookmap.R.drawable.icone_perfil),
                            contentDescription = "Foto de perfil",
                            modifier = Modifier
                                .size(165.dp)
                                .clip(CircleShape)
                        )
                        Text(
                            text = uiState.user.profile.name.ifEmpty { "Usuário sem nome" },
                            color = Color.White,
                            modifier = Modifier.padding(top = 16.dp),
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = uiState.user.email.ifEmpty { "Email não disponível" },
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 8.dp),
                            style = MaterialTheme.typography.labelLarge
                        )
                        if (readingBooks.isNotEmpty()) {
                            Text(text = "Lendo",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                            )
                            BookStatusRow(
                                bookList = readingBooks,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        if (readBooks.isNotEmpty()) {
                            Text(text = "Lidos",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                            )
                            BookStatusRow(
                                bookList = readBooks,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        if (unreadBooks.isNotEmpty()) {
                            Text(text = "Pretende Ler",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                            )
                            BookStatusRow(
                                bookList = unreadBooks,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        if (pausedBooks.isNotEmpty()) {
                            Text(text = "Pausados",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                            )
                            BookStatusRow(
                                bookList = pausedBooks,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        if (droppedBooks.isNotEmpty()) {
                            Text(text = "Desistidos",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                            )
                            BookStatusRow(
                                bookList = droppedBooks,
                                modifier = Modifier.fillMaxWidth()
                            )
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    val fakeUiState = ProfileUiState(
        isLoading = false,
        showError = false,
        errorMessage = "Quebrou o usuario"
    )

    val navController = rememberNavController()

    ProfileScreenContent(
        uiState = fakeUiState,
        navController = navController,
        modifier = Modifier
    )
}