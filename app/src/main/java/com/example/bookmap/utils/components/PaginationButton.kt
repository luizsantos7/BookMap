package com.example.bookmap.utils.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PaginationButtons(
    currentPage: Int,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
    isContinue: Boolean = true,
) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onPreviousClick,
            enabled = currentPage > 1,
            shape = RoundedCornerShape(7.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFA19C7D),
                contentColor = Color.White
            )
        ) {
            Text("Anterior")
        }
        Text(
            text = "Página $currentPage",
            style = MaterialTheme.typography.bodyLarge
        )

        Button(
            onClick = onNextClick,
            enabled = isContinue,
            shape = RoundedCornerShape(7.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF496D78),
                contentColor = Color.White
            ),
        ) {
            Text("Próxima")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Pagination() {
    PaginationButtons(
        currentPage = 1,
        onPreviousClick = { /*TODO*/ },
        onNextClick = { /*TODO*/ },
    )
}