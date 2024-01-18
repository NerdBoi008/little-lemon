package com.example.littlelemon.navigation

interface Destinations {
    val route: String
}

object HomeScreen: Destinations {
    override val route: String
        get() = "Home"
}

object ProfileScreen: Destinations {
    override val route: String
        get() = "Profile"
}

object OnboardingScreen: Destinations {
    override val route: String
        get() = "Onboarding"
}