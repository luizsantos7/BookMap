package com.example.bookmap.utils.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FixedButton(
    modifier: Modifier = Modifier,
    primaryButtonText: String = "Primary",
    secundaryButtonText: String? = null,
    primaryClickButton: () -> Unit = {},
    secundaryClickButton: () -> Unit = {},
    primaryButtonColor: Color = Color(0xFF496D78),
    secondaryButtonColor: Color = Color.Gray,
    primaryTextColor: Color = Color.White,
    secondaryTextColor: Color = Color.White,
    enabled : Boolean = true,
) {
    val hasSecondary = secundaryButtonText.isNullOrEmpty()

    if (!hasSecondary) {
        Column(
            modifier =
                modifier
                    .fillMaxWidth()
                    .height(128.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = primaryClickButton,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryButtonColor),
                enabled = enabled
            ) {
                Text(primaryButtonText, color = primaryTextColor)
            }
            Button(
                onClick = secundaryClickButton,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = secondaryButtonColor)
            ) {
                Text(secundaryButtonText, color = secondaryTextColor)
            }
        }
    } else {
        Column(
            modifier =
                modifier
                    .fillMaxWidth()
                    .height(128.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {},
                enabled =enabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryButtonColor)
            ) {
                Text(primaryButtonText, color = primaryTextColor)
            }
        }
    }
}

@Preview
@Composable
private fun ButtonPreview() {
    FixedButton()
}

@Preview
@Composable
private fun DoubleButtonPreview() {
    FixedButton(
        primaryButtonText = "Primary",
        secundaryButtonText = "secundary",
    )
}