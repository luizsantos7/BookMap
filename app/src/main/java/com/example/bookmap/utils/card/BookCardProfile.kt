package com.example.bookmap.utils.card

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.bookmap.utils.constants.EMPTY_STRING

@Composable
fun BookCardProfile(
    modifier: Modifier = Modifier,
    imageCover: String? = EMPTY_STRING,
) {
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

@Preview(showBackground = true)
@Composable
private fun CardBookPreview() {
    BookCardProfile()
}