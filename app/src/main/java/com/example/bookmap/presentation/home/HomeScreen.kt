package com.example.bookmap.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bookmap.utils.components.Footer
import com.example.bookmap.utils.components.NavBarComponent

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF171D23))
    ) {
        NavBarComponent()

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Footer(
                modifier = Modifier,
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun HomePrev() {
    val navController: NavHostController = rememberNavController()
    HomeScreen(
        navController = navController
    )
}