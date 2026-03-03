package com.example.bookmap.utils.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.bookmap.utils.ui.theme.Blue

@Composable
fun SignUpPrompt(onSignUpClick: () -> Unit) {
    Row {
        Text(
            text = "Não tem uma conta? ",
            color = Color.White,
        )
        Text(
            text = "Cadastre-se",
            color = Blue,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onSignUpClick() }
        )
    }
}
