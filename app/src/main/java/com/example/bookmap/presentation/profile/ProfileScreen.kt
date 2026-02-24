package com.example.bookmap.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.bookmap.data.models.AuthorDataModel
import com.example.bookmap.data.models.BookDataModel
import com.example.bookmap.data.models.Profile
import com.example.bookmap.data.models.UserRegisterDataModel
import com.example.bookmap.presentation.profile.ProfileScreenAction.LoadProfileData
import com.example.bookmap.presentation.profile.ProfileScreenAction.removeBook
import com.example.bookmap.utils.components.BookStatusRow
import com.example.bookmap.utils.components.ErrorContent
import com.example.bookmap.utils.components.Footer
import com.example.bookmap.utils.components.NavBarComponent
import com.google.firebase.auth.FirebaseAuth

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
        removeBookClick =  { book -> viewModel.onActionEvent(removeBook(book)) }
    )

    LaunchedEffect(Unit) {
        viewModel.onActionEvent(LoadProfileData)
    }


}

@Composable
private fun ProfileScreenContent(
    uiState: ProfileUiState,
    navController: NavController,
    modifier: Modifier = Modifier,
    removeBookClick : (BookDataModel) -> Unit = { }
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
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .weight(1f)
                ) {
                    item {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(com.example.bookmap.R.drawable.icone_perfil),
                                contentDescription = "Foto de perfil",
                                modifier = Modifier
                                    .size(165.dp)
                                    .clip(CircleShape)
                            )
                            Text(
                                text = uiState.user.profile.name.ifEmpty { "Usuario" },
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
                        }
                    }

                    if (readingBooks.isNotEmpty()) {
                        item {
                            Column(
                                Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Lendo",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                                BookStatusRow(
                                    bookList = readingBooks,
                                    modifier = Modifier.fillMaxWidth(),
                                    removeBookClick = removeBookClick
                                )
                            }
                        }
                    }

                    if (readBooks.isNotEmpty()) {
                        item {
                            Column(
                                Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Lidos",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                                BookStatusRow(
                                    bookList = readBooks,
                                    modifier = Modifier.fillMaxWidth(),
                                    removeBookClick = removeBookClick
                                )
                            }
                        }
                    }

                    if (unreadBooks.isNotEmpty()) {
                        item {
                            Column(
                                Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Pretende Ler",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                                BookStatusRow(
                                    bookList = unreadBooks,
                                    modifier = Modifier.fillMaxWidth(),
                                    removeBookClick = removeBookClick
                                )
                            }
                        }
                    }

                    if (pausedBooks.isNotEmpty()) {
                        item {
                            Column(
                                Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Pausados",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                                BookStatusRow(
                                    bookList = pausedBooks,
                                    modifier = Modifier.fillMaxWidth(),
                                    removeBookClick = removeBookClick
                                )
                            }
                        }
                    }

                    if (droppedBooks.isNotEmpty()) {
                        item {
                            Column(
                                Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Desistidos",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                                BookStatusRow(
                                    bookList = droppedBooks,
                                    modifier = Modifier.fillMaxWidth(),
                                    removeBookClick = removeBookClick
                                )
                            }
                        }
                    }

                    item {
                        Button(
                            onClick = {
                                FirebaseAuth.getInstance().signOut()
                                navController.navigate("login_screen") {
                                    popUpTo(0)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(75.dp)
                                .padding(horizontal = 95.dp, vertical = 15.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFA19C7D),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Sair da conta")
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



@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProfileScreenPreview_Fake() {
    val fakeUser = UserRegisterDataModel(
        email = "ana.leitora@email.com",
        confirmEmail = "ana.leitora@email.com",
        password = "123456",
        profile = Profile(
            name = "Ana Leitora",
            birthday = "10/05/1998",
            gender = "Feminino"
        )
    )

    val fakeBooks = listOf(
        BookDataModel(
            id = 1,
            title = "A Revolução dos Bichos",
            authors = listOf(
                AuthorDataModel(
                    name = "George Orwell",
                    birthYear = 1903,
                    deathYear = 1950
                )
            ),
            coverUrl = "https://example.com/revolucao.jpg"
        )
    )
    BookDataModel(
        id = 2,
        title = "1984",
        authors = listOf(
            AuthorDataModel(
                name = "George Orwell",
                birthYear = 1903,
                deathYear = 1950
            )
        ),
        coverUrl = "https://example.com/1984.jpg"
    )
    BookDataModel(
        id = 3,
        title = "Dom Casmurro",
        authors = listOf(
            AuthorDataModel(
                name = "Machado de Assis",
                birthYear = 1839,
                deathYear = 1908
            )
        ),
        coverUrl = "https://example.com/domcasmurro.jpg"
    )


    val fakeUiState = ProfileUiState(
        isLoading = false,
        showError = false,
        errorMessage = "",
        user = fakeUser,
        readingBooks = fakeBooks.take(1),
    )

    val navController = rememberNavController()

    ProfileScreenContent(
        uiState = fakeUiState,
        navController = navController,
        modifier = Modifier
    )
}