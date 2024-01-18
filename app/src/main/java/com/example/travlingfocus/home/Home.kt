package com.example.travlingfocus.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
//    Layout đã được chia sẵn
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.statusBarsPadding(),
        backgroundColor = MaterialTheme.colorScheme.primary,
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

    val coroutineScope = rememberCoroutineScope()
    BackdropScaffold(
        modifier = modifier,
        scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed),
//        frontLayerShape = BottomSheetShape,
//        frontLayerScrimColor = Color.Unspecified,
        appBar = {},
        backLayerContent = {

        },
        frontLayerContent = {}) {

    }
}

@Composable
private fun HomeTabBar(
    openDrawer: () -> Unit,
//    tabSelected: CraneScreen,
//    onTabSelected: (CraneScreen) -> Unit,
    modifier: Modifier = Modifier
){
    myTabBar(
        modifier = modifier,
        openDrawer = openDrawer,
    )
}

@Composable
private  fun myTabBar(
    modifier: Modifier = Modifier,
    openDrawer: () -> Unit,
) {

}

//    Box (
//        modifier = modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center,
//    ) {
//        Timer(
//            totalTime = 1000L,
//            handleColor = GreenLight,
//            activeBarColor = GreenLight,
//            inactiveBarColor = PinkGray,
//            modifier = Modifier.size(200.dp)
//        )
//    }
