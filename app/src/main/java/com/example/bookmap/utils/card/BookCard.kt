package com.example.bookmap.utils.card

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.bookmap.data.entity.AuthorEntity
import com.example.bookmap.utils.constants.EMPTY_STRING

@Composable
fun BookCard(
    title: String = "Book Title",
    author: List<AuthorEntity> = listOf(),
    imageCover: String? = EMPTY_STRING,
    modifier: Modifier = Modifier
) {

    Log.d("BookCard", "Image URL: $imageCover")

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(175.dp)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF15191E)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(89.dp)
                    .height(160.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.DarkGray)
            ) {
                AsyncImage(
                    model = imageCover,
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    onLoading = { Log.d("BookCard", "Loading image...") },
                    onSuccess = { Log.d("BookCard", "Image loaded!") },
                    onError = { Log.e("BookCard", "Error: ${it.result.throwable.message}") }
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = author.joinToString(", ") { it.name },
                        color = Color.LightGray,
                        fontSize = 14.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite Icon",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F1115)
@Composable
private fun BookCardPreview() {
    BookCard(
        title = "Frankenstein; Or, The Modern Prometheus",
        imageCover = "https://www.gutenberg.org/cache/epub/84/pg84.cover.medium.jpg"
    )
}