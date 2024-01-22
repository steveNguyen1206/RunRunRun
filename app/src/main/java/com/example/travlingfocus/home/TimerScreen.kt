package com.example.travlingfocus.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travlingfocus.R
import com.example.travlingfocus.composable.GifImage
import com.example.travlingfocus.composable.SelectRow
import com.example.travlingfocus.composable.Timer
import com.example.travlingfocus.composable.totalTime
import com.example.travlingfocus.ui.theme.GrayContainer
import com.example.travlingfocus.ui.theme.GreenLight
import com.example.travlingfocus.ui.theme.PinkGray

enum class TimerType {Timer, Stopwatch }
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen (
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    timerType: TimerType = TimerType.Timer
){
//                Text(text = "Back Layer")
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var triggerTimerFromOutSize by remember {
        mutableStateOf(0)
    }

        Timer(
            handleColor = GreenLight,
            activeBarColor = GreenLight,
            inactiveBarColor = PinkGray,
            modifier = modifier.fillMaxWidth(),
            timerType = timerType,
            viewModel = viewModel,
            openBottomSheet = {
                isSheetOpen = true
            },
            triggerTimerFromOutSize = triggerTimerFromOutSize,
        )

    if(isSheetOpen)
    {
        ModalBottomSheet(
            onDismissRequest = {
                isSheetOpen = false
            },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface,

        ) {
            TravelBottomSheet(
                modifier = Modifier
                    .fillMaxSize().windowInsetsPadding(
                        WindowInsets.navigationBars.only(WindowInsetsSides.Start + WindowInsetsSides.End)
                    ),
                viewModel = viewModel,
                travelClick = {
                    isSheetOpen = false
                    triggerTimerFromOutSize = ++triggerTimerFromOutSize
                }
            )
        }
    }
}
