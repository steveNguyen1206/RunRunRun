package com.example.travlingfocus.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.travlingfocus.composable.MyTab
import com.example.travlingfocus.composable.MyTabBar
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

        var timerState = remember {
            viewModel.timerState
        }
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
    Scaffold(
        modifier = modifier,
        backgroundColor = Color.Transparent,
//        scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed),
//        backLayerBackgroundColor = Color.Transparent,
//        frontLayerShape = BottomSheetShape,
//        frontLayerScrimColor = Color.Unspecified,
        topBar = {
            HomeTabBar(
                onTimerClick = {
                    viewModel.timerState.value = TimerType.Timer
                },
                onStopWatchClick = {
                    viewModel.timerState.value = TimerType.Stopwatch
                },
            )
        },

        content = {
            TimerScreen(
                modifier = Modifier.padding(it),
                viewModel = viewModel
            )

        })

//        backLayerContent = {
//           TimerScreen(viewModel = viewModel)
//
//        },
//        frontLayerContent = {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(top = 200.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(text = "Front Layer")
//            }
//        }) {
//
//    }
}

@Composable
private fun HomeTabBar(
//    openDrawer: () -> Unit,
//    tabSelected: CraneScreen,
//    onTabSelected: (CraneScreen) -> Unit,
    onTimerClick: () -> Unit,
    onStopWatchClick: () -> Unit,
    modifier: Modifier = Modifier
){
    MyTabBar(
        modifier = modifier
            .wrapContentWidth()
            .sizeIn(maxHeight = 500.dp),
//        onMenuClicked = openDrawer,
    ){
        MyTab(it,
            onTimerClick = onTimerClick,
            onStopWatchClick = onStopWatchClick)

    }
}


