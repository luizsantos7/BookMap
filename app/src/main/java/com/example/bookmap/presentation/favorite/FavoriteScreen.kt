package com.example.bookmap.presentation.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.bookmap.presentation.SharedUserViewModel
import com.example.bookmap.presentation.favorite.FavoriteScreenAction.SetUser
import com.example.bookmap.utils.components.ErrorContent
import com.example.bookmap.utils.components.Footer
import com.example.bookmap.utils.components.NavBarComponent

@Composable
fun FavoriteScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel(),
    sharedUserViewModel: SharedUserViewModel,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val currentUser by sharedUserViewModel.currentUser.collectAsState()

    LaunchedEffect(currentUser) {
        currentUser?.let { user ->
            viewModel.onActionEvent(SetUser(user))
        }
    }

    HomeScreenContent(
        uiState = uiState,
        navController = navController,
        modifier = modifier
    )

}

@Composable
private fun HomeScreenContent(
    uiState: FavoriteUiState,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF171D23))
    ) {
        NavBarComponent(hasSearch = false)

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
                        text = "Carregando favoritos...",
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }

            uiState.showError -> {
                ErrorContent(
                    modifier = Modifier.weight(1f),
                    errorMessage = uiState.errorMessage,
                    onRetry = {}
                )
            }

            else -> {
            }
        }
        Column(
            verticalArrangement = Arrangement.Bottom
        ) {
            Footer(navController = navController)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF171D23)
@Composable
private fun FavoriteScreenPreview() {
    val navController = androidx.navigation.testing.TestNavHostController(
        androidx.compose.ui.platform.LocalContext.current
    )

    val fakeUiState = FavoriteUiState(
        isLoading = true,
        showError = false,
        errorMessage = ""
    )

    HomeScreenContent(
        uiState = fakeUiState,
        navController = navController
    )
}