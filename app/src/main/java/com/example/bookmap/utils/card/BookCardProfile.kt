package com.example.bookmap.utils.card

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.bookmap.utils.constants.EMPTY_STRING
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BookCardProfile(
    modifier: Modifier = Modifier,
    imageCover: String? = EMPTY_STRING,
    removeBookClick: () -> Unit = { },
    clickable: Boolean = false
) {
    var visivel by remember { mutableStateOf(true) }

    if (clickable) {
        AnimatedVisibility(
            visible = visivel,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Card(
                modifier = modifier
                    .width(120.dp)
                    .height(160.dp)
                    .padding(horizontal = 6.dp)
                    .clickable(onClick = {
                        visivel = false

                        GlobalScope.launch {
                            delay(300)
                            removeBookClick()
                        }
                    }),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF15191E)),
                shape = RoundedCornerShape(6.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                AsyncImage(
                    model = imageCover,
                    contentDescription = "Book Cover",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    onLoading = { Log.d("BookCard", "Loading image...") },
                    onSuccess = { Log.d("BookCard", "Image loaded!") },
                    onError = { Log.e("BookCard", "Error: ${it.result.throwable.message}") }
                )
            }
        }
    } else {
        Card(
            modifier = modifier
                .width(120.dp)
                .height(160.dp)
                .padding(horizontal = 6.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF15191E)),
            shape = RoundedCornerShape(6.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            AsyncImage(
                model = imageCover,
                contentDescription = "Book Cover",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                onLoading = { Log.d("BookCard", "Loading image...") },
                onSuccess = { Log.d("BookCard", "Image loaded!") },
                onError = { Log.e("BookCard", "Error: ${it.result.throwable.message}") }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun CardBookPreview() {
    BookCardProfile()
}