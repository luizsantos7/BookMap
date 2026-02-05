package com.example.bookmap.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookmap.utils.components.FixedButton

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = androidx.compose.ui.res.painterResource(id = com.example.bookmap.R.drawable.login),
        contentDescription = "Login Image",
        contentScale = ContentScale.Crop
    )
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black,
                            Color.Transparent
                        ),
                        startY = Float.POSITIVE_INFINITY,
                        endY = 0f
                    )
                )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            FixedButton(
                primaryButtonText = "Registrar",
                secundaryButtonText = "Continuar como convidado",
                primaryClickButton = {} ,
                secundaryClickButton = {},
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }
    }
}


@Preview
@Composable
private fun SDsd() {
    LoginScreen()
}