package com.example.travlingfocus.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.travlingfocus.composable.Timer
import com.example.travlingfocus.ui.theme.GreenLight
import com.example.travlingfocus.ui.theme.PinkGray

@Composable
fun Home (
    widthSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
)
{
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.statusBarsPadding(),
//        drawerContent = {
//            CraneDrawer()
//        }
    ){
        HomeContent(
            modifier = modifier.padding(it),
            widthSize = widthSize,
//            onExploreItemClicked = onExploreItemClicked,
//            onDateSelectionClicked = onDateSelectionClicked,
//            openDrawer = {
//                scope.launch {
//                    scaffoldState.drawerState.open()
//                }
//            },
            viewModel = viewModel
        )
    }
}

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun HomeContent(
    widthSize: WindowWidthSizeClass,
//    onExploreItemClicked: OnExploreItemClicked,
//    onDateSelectionClicked: () -> Unit,
//    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {

    Box (
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Timer(
            totalTime = 1000L,
            handleColor = GreenLight,
            activeBarColor = GreenLight,
            inactiveBarColor = PinkGray,
            modifier = Modifier.size(200.dp)
        )
    }

}