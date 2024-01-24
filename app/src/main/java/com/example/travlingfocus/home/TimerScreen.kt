package com.example.travlingfocus.home

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.travlingfocus.composable.Timer
import com.example.travlingfocus.ui.theme.GreenLight
import com.example.travlingfocus.ui.theme.PinkGray
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travlingfocus.AppViewModelProvider
import com.example.travlingfocus.Login.AuthViewModel
import kotlinx.coroutines.launch

enum class TimerType {Timer, Stopwatch }
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen (
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    timerType: TimerType = TimerType.Timer,
    tripCreateViewModel: TripCreateViewModel = viewModel(factory = AppViewModelProvider.Factory),
    authViewModel: AuthViewModel = viewModel(factory = AppViewModelProvider.Factory),
){
//                Text(text = "Back Layer")
    val authUiState = authViewModel.authUiState
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var triggerTimerFromOutSize by remember {
        mutableStateOf(0)
    }
    val coroutineScope = rememberCoroutineScope()

    var tripUiState = tripCreateViewModel.tripUiState;

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
        tripDetails = tripUiState.tripDetails,
        onTripValueChange = {
            tripCreateViewModel.updateUiState(it)
        },
        onTripEnd = {
            coroutineScope.launch {
                tripCreateViewModel.saveTrip(authUiState.value.userId)
            }
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
                    .fillMaxSize().windowInsetsPadding(
                        WindowInsets.navigationBars.only(WindowInsetsSides.Start + WindowInsetsSides.End)
                    ),
                viewModel = viewModel,
                travelClick = {
                    isSheetOpen = false
                    triggerTimerFromOutSize = ++triggerTimerFromOutSize
                },
                tripDetails = tripUiState.tripDetails,
                onTripValueChange = {
                    tripCreateViewModel.updateUiState(it)
                }
            )
        }
    }
}
