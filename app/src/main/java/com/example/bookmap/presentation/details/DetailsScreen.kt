package com.example.bookmap.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.bookmap.data.models.AuthorDataModel
import com.example.bookmap.data.models.BookDetailsDataModel
import com.example.bookmap.data.models.ReadStatusDataModel
import com.example.bookmap.utils.components.DetailsDescription
import com.example.bookmap.utils.components.Footer
import com.example.bookmap.utils.components.NavBarComponent

@Composable
fun DetailScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DetailScreenContent(
        uiState = uiState,
        navController = navController,
        modifier = modifier,
        onStatusChange = { status ->
            viewModel.onActionEvent(
                DetailScreenAction.OnStatusChange(status)
            )
        }
    )
}

@Composable
private fun DetailScreenContent(
    uiState: DetailUiState,
    navController: NavController,
    modifier: Modifier = Modifier,
    onStatusChange: (ReadStatusDataModel) -> Unit
) {

    val book = uiState.book

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF171D23))
    ) {
        NavBarComponent()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF3D3D3D),
                                    Color(0xFF171D23)
                                ),
                                startY = 0f,
                                endY = Float.POSITIVE_INFINITY
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = book.coverUrl,
                        contentDescription = "Capa do livro",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .height(220.dp)
                            .width(150.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.Gray),
                    )
                }
            }


            item {
                DetailsDescription(
                    book = book,
                    navController = navController,
                    onStatusChange = onStatusChange
                )
            }

        }
        Column(verticalArrangement = Arrangement.Bottom) {
            Footer(navController = navController)
        }
    }
}


