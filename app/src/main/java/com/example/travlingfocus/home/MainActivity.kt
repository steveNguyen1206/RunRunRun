package com.example.travlingfocus.home

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.VisibleForTesting
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.travlingfocus.AppViewModelProvider
import com.example.travlingfocus.Login.AuthViewModel
import com.example.travlingfocus.Login.LoginScreen
import com.example.travlingfocus.navigation.RambleNavGraph
import com.example.travlingfocus.navigation.Routes
import com.example.travlingfocus.ui.theme.TravlingfocusTheme
import com.example.travlingfocus.ui.theme.YellowLight
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))
        super.onCreate(savedInstanceState)
        setContent {
            TravlingfocusTheme {
                val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass

                val navController = rememberNavController()

                // A surface container using the 'background' color from the theme
//                NavHost(navController = navController, startDestination = Routes.Home.route) {
//                    composable(Routes.Home.route) {
//                        val mainViewModel = hiltViewModel<MainViewModel>()
//                        MainScreen(widthSizeClass = widthSizeClass)
//                    }
//                }
                RambleNavGraph(navController = navController, widthSizeClass = widthSizeClass)
            }
        }
    }
}

enum class SplashState { Shown, Completed }

@VisibleForTesting
@Composable
fun MainScreen(
    navigateToScreenRoute: (String) -> Unit,
    widthSizeClass: WindowWidthSizeClass,
    mainViewModel: MainViewModel = hiltViewModel<MainViewModel>(),
    authViewModel: AuthViewModel
)
{
// Layout Container
    // region Animation of Surface/splash screen
    val transitionState = remember { MutableTransitionState(mainViewModel.shownSplash.value) }
    val transition = updateTransition(transitionState, label = "splashTransition")
    val splashAlpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 100) }, label = "splashAlpha"
    ) {
        if (it == SplashState.Shown) 1f else 0f
    }
    val contentAlpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 300) }, label = "contentAlpha"
    ) {
        if (it == SplashState.Shown) 0f else 1f
    }
    val contentTopPadding by transition.animateDp(
        transitionSpec = { spring(stiffness = Spring.StiffnessLow) }, label = "contentTopPadding"
    ) {
        if (it == SplashState.Shown) 100.dp else 0.dp
    }
    val contentColor by transition.animateColor(
        transitionSpec = { tween(durationMillis = 300) }, label = "contentColor"
    ) {
        if (it == SplashState.Shown) YellowLight else MaterialTheme.colorScheme.primary
    }
    // endregion
    Surface(
        modifier = Modifier.windowInsetsPadding(
            WindowInsets.navigationBars.only(WindowInsetsSides.Start + WindowInsetsSides.End)
        ),
        color = contentColor
    ) {
        Box {
            LandingScreen(
                modifier = Modifier.alpha(splashAlpha),
                onTimeout = {
                    transitionState.targetState = SplashState.Completed
                    mainViewModel.shownSplash.value = SplashState.Completed
                }
            )

                MainContent(
                    navigateToScreenRoute = navigateToScreenRoute,
                    modifier = Modifier.alpha(contentAlpha),
                    topPadding = contentTopPadding,
                    widthSize = widthSizeClass,
                    viewModel = mainViewModel,
                    authViewModel = authViewModel,

                )
        }

    }
}

@Composable
private fun MainContent(
    navigateToScreenRoute: (String) -> Unit,
    modifier: Modifier = Modifier,
    topPadding: Dp = 0.dp,
    widthSize: WindowWidthSizeClass,
    viewModel: MainViewModel,
    authViewModel: AuthViewModel,
) {
    Column(modifier = modifier) {
        Spacer(Modifier.padding(top = topPadding))
        val authUiState = authViewModel.authUiState
        if(authUiState.value.isLogined)
        Home(
            navigateToScreenRoute = navigateToScreenRoute,
            widthSize = widthSize,
            viewModel = viewModel,
            modifier = modifier,
            authViewModel = authViewModel
        )
        else {
            LoginScreen(authViewModel = authViewModel)
        }
    }
}
