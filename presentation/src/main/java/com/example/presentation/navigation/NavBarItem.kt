package com.example.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search

object NavBarItem {
    val BarItems = listOf(
        BarItem(
            title = "Home",
            image = Icons.Filled.Home,
            route = "home"
        ),

        BarItem(
            title = "Filter",
            image= Icons.Filled.List,
            route = "filter"
        ),
        BarItem(
            title ="Search",
            image = Icons.Filled.Search,
            route ="search"
        ),
        BarItem(
            title = "My",
            image = Icons.Filled.AccountCircle,
            route = "myPage"
        )
    )
}