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

        var selectedSouvenirIndex by remember {
            mutableStateOf(0)
        }

        Text(
            text = "Focus Time",
            fontSize = 18.sp,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            fontWeight = FontWeight.Bold
        )

        SelectRow(
            modifier = Modifier
                .fillMaxWidth(),
            list = viewModel.timeOptions,
            onChose = {
                viewModel.updateTimerValue(it.toFloat() * 60000)
                selectedIndex = viewModel.timeOptions.indexOf(it)
            },
            selectedIndex = selectedIndex,
            fontSize = 18,
            unSelectedBackground = GrayContainer,
        )

        Text(
            text = "Tag",
            fontSize = 18.sp,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            fontWeight = FontWeight.Bold
        )

        SelectRow(
            modifier = Modifier
                .fillMaxWidth(),
            list = ActivityTag.values().map{it.name},
            onChose = {
                viewModel.tag.value = ActivityTag.valueOf(it)
            },
            selectedIndex = viewModel.tag.value.ordinal,
            fontSize = 16,
            unSelectedBackground = GrayContainer,
        )

        Text(
            text = "Souvenir",
            fontSize = 18.sp,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            fontWeight = FontWeight.Bold
        )
        
        LazyVerticalGrid(
            columns = GridCells.Adaptive(120.dp),
            content = {
                items(viewModel.souvenirList.size) { index ->
                    val souvenir = viewModel.souvenirList[index]
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(120.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .border(
                                width =  2.dp,
                                color = if (selectedSouvenirIndex == index) GreenLight else Color.Transparent,
                                shape = RoundedCornerShape(20.dp)
                            ).background(MaterialTheme.colorScheme.tertiary),
                        contentAlignment = Alignment.Center
                    ) {

                        GifImage(
                            data = souvenir.imageId,
                            mayBeginGifAnimation = false,
                            onGifClick = {
                                selectedSouvenirIndex = index
                            },
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
            }
        )

    }
}