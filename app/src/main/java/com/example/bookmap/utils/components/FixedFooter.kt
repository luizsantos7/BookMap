package com.example.bookmap.utils.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Footer(
    modifier: Modifier = Modifier,
    onClick : () -> Unit = {}
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 72.dp)
            .background(Color(0xFF15191E))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.House,
                contentDescription = "Home icon",
                tint = Color.White,
                modifier = Modifier.size(22.dp)
            )
            Text(
                text = "Inicio ",
                color = Color.White
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Bookmark,
                contentDescription = "Home icon",
                tint = Color.White,
                modifier = Modifier.size(22.dp)
            )
            Text(
                text = "Favoritados",
                color = Color.White
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Home icon",
                tint = Color.White,
                modifier = Modifier.size(22.dp)
            )
            Text(
                text = "Perfil",
                color = Color.White
            )
        }
    }

}

@Preview
@Composable
private fun FooterPreview() {
    Footer()
}