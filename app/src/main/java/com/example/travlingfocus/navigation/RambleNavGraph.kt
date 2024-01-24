package com.example.travlingfocus.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.travlingfocus.home.MainScreen
import com.example.travlingfocus.home.MainViewModel
import com.example.travlingfocus.rewardscreen.RewardScreen
import com.example.travlingfocus.timline.TimeLineScreen

@Composable
fun RambleNavGraph (
    navController: NavHostController,
    widthSizeClass: WindowWidthSizeClass,
){
    NavHost(navController = navController, startDestination = Routes.Home.route) {
        composable(Routes.Home.route) {
            MainScreen(
                navigateToScreenRoute = {
                    navController.navigate(it)
                },
                widthSizeClass = widthSizeClass
            )
        }

        composable(Routes.TimeLine.route) {
            TimeLineScreen(
                navigateUp = { navController.navigateUp() },
                canNavigateBack = true,
            )
        }

        composable(Routes.PassPort.route) {
            RewardScreen(
                navigateUp = { navController.navigateUp() },
                canNavigateBack = true,
            )
        }
    }
}


// sealed class: class with a fixed number of subclasses
sealed class Routes(val route: String){
    object Home: Routes("home")
    object PassPort: Routes("passport")
    object TimeLine: Routes("timeline")
    object Ranking: Routes("ranking")
    object Friend: Routes("friends")
    object Setting: Routes("setting")
}