package com.example.presentation.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.presentation.detail.SetDetailActivity
import com.example.presentation.filter.SetFilter
import com.example.presentation.home.HomeScreen
import com.example.presentation.main.ui.theme.ComposeMovieTheme
import com.example.presentation.myPage.SetMyPageActivity
import com.example.presentation.navigation.NavBarItem
import com.example.presentation.navigation.NavRoutes
import com.example.presentation.search.SearchScreenSetUp

class MainScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMovieTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Log.d("Main", "메인스크린 액티비티 실행")
                    MainScreen()
                }
            }
        }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    Log.d("Main", "MainScreen() 실행")

    // NavHostController 생성
    val navController = rememberNavController()

    Scaffold(
        content = { NavigationHost(navController = navController) },
        bottomBar = { BottomNavigationBar(navController = navController) }
    )
}

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route
    ) {
        composable(NavRoutes.Home.route) {
            HomeScreen(navigateToDetail = { id ->
                navController.navigate(NavRoutes.Detail.route + "/$id")
            })
        }
        composable(NavRoutes.Filter.route) {
            SetFilter(navigateToDetail = { id ->
                navController.navigate(NavRoutes.Detail.route + "/$id")
            })
        }
        composable(NavRoutes.Search.route) {
            SearchScreenSetUp(navigateToDetail = { id ->
                navController.navigate(NavRoutes.Detail.route + "/$id")
            })
        }
        composable(NavRoutes.MyPage.route) {
            SetMyPageActivity(navigateToDetail = { id ->
                navController.navigate(NavRoutes.Detail.route + "/$id")
            })
        }
        composable(
            NavRoutes.Detail.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")!!
            Log.d("park", "MainActivity ID: $id")
            SetDetailActivity(id = id, navigateToDetail = { id ->
                navController.navigate(NavRoutes.Detail.route + "/$id")
            })

        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    BottomNavigation() {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        NavBarItem.BarItems.forEach { navItem ->
            BottomNavigationItem(
                selected = currentRoute == navItem.route,
                onClick = {

                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = false
                    }
                },
                icon = {
                    Icon(
                        imageVector = navItem.image,
                        contentDescription = navItem.title
                    )
                },
                label = {
                    Text(text = navItem.title, fontSize = 9.sp)
                }
            )
        }
    }
}
