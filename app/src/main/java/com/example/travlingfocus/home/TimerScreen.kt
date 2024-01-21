package com.example.travlingfocus.home

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.travlingfocus.R
import com.example.travlingfocus.composable.TimeSelectRow
import com.example.travlingfocus.composable.Timer
import com.example.travlingfocus.composable.totalTime
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

        Timer(
            handleColor = GreenLight,
            activeBarColor = GreenLight,
            inactiveBarColor = PinkGray,
            modifier = modifier.fillMaxWidth(),
            timerType = timerType,
            viewModel = viewModel,
            openBottomSheet = {
                isSheetOpen = true
            }
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
                    .fillMaxSize()
                    .padding(16.dp),
                viewModel = viewModel,

            )
        }
    }
}

@Composable
fun TravelBottomSheet (
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
)
{
    Column(
        modifier = modifier.padding(top = 24.dp),
    )
    {
        var selectedIndex by remember {
            mutableStateOf(0)
        }
        Text(
            text = "Focus Time",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            fontWeight = FontWeight.Bold
        )

        TimeSelectRow(
            modifier = Modifier
                .fillMaxWidth(),
            timeList = viewModel.timeOptions,
            onTimeChose = {
                viewModel.updateTimerValue(it.toFloat() * 60000 / totalTime)
                selectedIndex = viewModel.timeOptions.indexOf(it)
            },
            selectedIndex = selectedIndex
        )
    }
}