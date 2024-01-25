package com.example.travlingfocus.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.travlingfocus.AppViewModelProvider
import com.example.travlingfocus.Login.AuthViewModel
import com.example.travlingfocus.Login.LoginScreen
import com.example.travlingfocus.home.MainScreen
import com.example.travlingfocus.home.MainViewModel
import com.example.travlingfocus.rewardscreen.RewardScreen
import com.example.travlingfocus.timline.TimeLineScreen

@Composable
fun RambleNavGraph (
    navController: NavHostController,
    widthSizeClass: WindowWidthSizeClass,
){
    val authViewModel :AuthViewModel = viewModel(factory = AppViewModelProvider.Factory)
    NavHost(navController = navController, startDestination = Routes.Home.route) {
        composable(Routes.Home.route) {
            MainScreen(
                navigateToScreenRoute = {
                    navController.navigate(it)
                },
                widthSizeClass = widthSizeClass,
                authViewModel = authViewModel,
            )
        }

        composable(Routes.TimeLine.route) {
            TimeLineScreen(
                navigateUp = { navController.navigateUp() },
                canNavigateBack = true,
                authViewModel = authViewModel,
            )
        }

        composable(Routes.PassPort.route) {
            RewardScreen(
                navigateUp = { navController.navigateUp() },
                canNavigateBack = true,
            )
        }

        composable(Routes.Login.route) {
            LoginScreen(
                authViewModel = authViewModel,
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
    object Login: Routes("login")
    object SignUp: Routes("signup")
}