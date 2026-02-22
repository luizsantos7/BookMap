package com.example.bookmap.utils.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bookmap.data.models.AuthorDataModel
import com.example.bookmap.data.models.BookDetailsDataModel
import com.example.bookmap.data.models.ReadStatusDataModel

@Composable
fun DetailsDescription(
    book: BookDetailsDataModel,
    modifier: Modifier = Modifier,
    onStatusChange: (ReadStatusDataModel) -> Unit,
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = book.title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        if (book.authors.isNotEmpty()) {
            Text(
                text = "Autor${if (book.authors.size > 1) "es" else ""}: " +
                        book.authors.joinToString(", ") { it.name },
                style = MaterialTheme.typography.bodySmall.copy(color = Color.LightGray),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        if (book.languages.isNotEmpty()) {
            Text(
                text = "Idiomas: ${book.languages.joinToString(", ")}",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.LightGray),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        if (book.summaries.isNotEmpty()) {
            book.summaries.forEach { summary ->
                Text(
                    text = summary,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                    textAlign = TextAlign.Start
                )
            }
        }

        ReadStatusSelector(
            currentStatus = book.isRead,
            onStatusChanged = { selectedStatus ->
                onStatusChange(selectedStatus)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 72.dp, vertical = 16.dp)
        )

        Button(
            onClick = {navController.navigate("home_screen")},
            shape = RoundedCornerShape(10.dp),) {
            Text(text = "Voltar")
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = if (book.copyright) "Livro protegido por direitos autorais"
                else "Domínio público",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color.LightGray
                ),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF171D23)
@Composable
private fun DetailsDescriptionPreview() {
    val mockBook = BookDetailsDataModel(
        id = 1L,
        title = "O Senhor dos Anéis: A Sociedade do Anel",
        authors = listOf(
            AuthorDataModel(name = "J.R.R. Tolkien"),
            AuthorDataModel(name = "Christopher Tolkien")
        ),
        summaries = listOf(
            "Um grupo improvável parte para destruir o Um Anel, objeto de poder corrompido que ameaça toda a Terra‑média.",
            "Primeiro volume da trilogia clássica de fantasia que moldou o gênero moderno."
        ),
        languages = listOf("Português", "Inglês"),
        copyright = false,
        isRead = ReadStatusDataModel.UNREAD,
    )

}