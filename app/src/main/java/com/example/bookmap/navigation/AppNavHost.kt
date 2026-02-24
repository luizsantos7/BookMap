package com.example.bookmap.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookmap.presentation.details.DetailScreen
import com.example.bookmap.presentation.favorite.FavoriteScreen
import com.example.bookmap.presentation.home.HomeScreen
import com.example.bookmap.presentation.login.LoginScreen
import com.example.bookmap.presentation.profile.ProfileScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    viewModel: AppNavHostViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = state.startDestination,
    ) {
        composable("login_screen") {
            LoginScreen(
                modifier = modifier,
                navController = navController
            )
        }
        composable("favorite_screen") {
            FavoriteScreen(
                modifier = modifier,
                navController = navController
            )
        }

        composable("details/{bookId}") {
            DetailScreen(
                navController = navController
            )
        }
        composable("profile_screen") {
            ProfileScreen(
                navController = navController
            )
        }
        composable("home_screen") {
            HomeScreen(
                modifier = modifier,
                navController = navController
            )
        }
    }
}