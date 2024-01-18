package com.example.littlelemon.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.littlelemon.model.AppViewModel
import com.example.littlelemon.screens.Home
import com.example.littlelemon.screens.Onboarding
import com.example.littlelemon.screens.Profile

@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: AppViewModel
) {
    NavHost(
        navController = navController,
        startDestination = if (viewModel.isUserDataPresent()) HomeScreen.route else OnboardingScreen.route
    ) {
        composable(OnboardingScreen.route) {
            Onboarding(navController, viewModel)
        }
        composable(HomeScreen.route) {
            Home(navController)
        }
        composable(ProfileScreen.route) {
            Profile(navController, viewModel)
        }
    }
}