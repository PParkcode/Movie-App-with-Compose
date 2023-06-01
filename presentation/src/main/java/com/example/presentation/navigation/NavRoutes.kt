package com.example.presentation.navigation

sealed class NavRoutes(val route: String) {

    object Home: NavRoutes("home")
    object Filter: NavRoutes("filter")
    object Search: NavRoutes("search")
    object MyPage: NavRoutes("myPage")
}