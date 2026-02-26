package com.example.bookmap.utils.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookmap.data.models.AuthorDataModel
import com.example.bookmap.data.models.BookDataModel
import com.example.bookmap.data.models.ReadStatusDataModel
import com.example.bookmap.utils.card.BookCardProfile

@Composable
fun BookStatusRow(
    modifier: Modifier = Modifier,
    bookList: List<BookDataModel>,
    removeBookClick : (BookDataModel) -> Unit = {},
    clickable: Boolean = false
) {
    Column() {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height( 200.dp)
                .padding(horizontal = 10.dp)
            ,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(bookList) { book ->
                BookCardProfile(
                    clickable = clickable,
                    imageCover = book.coverUrl,
                    removeBookClick = { removeBookClick(book) })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookStatusRowPreview() {
    val sampleBooks = listOf(
        BookDataModel(
            id = 1L,
            title = "O Senhor dos Anéis",
            authors = listOf(AuthorDataModel(name = "J.R.R. Tolkien")),
            coverUrl = "https://picsum.photos/200/300"
        ),
        BookDataModel(
            id = 2L,
            title = "1984",
            authors = listOf(AuthorDataModel(name = "George Orwell")),
            coverUrl = "https://picsum.photos/200/301" // outra imagem de teste
        ),
        BookDataModel(
            id = 3L,
            title = "Dom Quixote",
            authors = listOf(AuthorDataModel(name = "Miguel de Cervantes")),
            coverUrl = "https://picsum.photos/200/302"
        )
    )

    BookStatusRow(
        bookList = sampleBooks,
        removeBookClick = { /* Apenas preview, nenhuma ação */ }
    )
}