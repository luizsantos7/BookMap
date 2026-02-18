package com.example.bookmap.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookmap.presentation.SharedUserViewModel
import com.example.bookmap.presentation.home.HomeScreen
import com.example.bookmap.presentation.login.LoginScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val sharedUserViewModel: SharedUserViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "login_screen",
    ) {
        composable("login_screen") {
            LoginScreen(
                modifier = modifier,
                navController = navController,
                sharedUserViewModel = sharedUserViewModel
            )
        }
        composable("home_screen") {
            HomeScreen(
                modifier = modifier,
                navController = navController,
                sharedUserViewModel = sharedUserViewModel
            )
        }
    }
}