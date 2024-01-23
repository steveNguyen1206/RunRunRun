package com.example.travlingfocus.composable

import android.graphics.Color
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.example.travlingfocus.home.MainViewModel
import java.lang.reflect.Modifier

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SignIn(
    //signIn with email and password
    handleColor: Color,
    activeBarColor: Color,
    inactiveBarColor: Color,
    modifier: Modifier,
    viewModel: MainViewModel,
    openBottomSheet: () -> Unit,
    triggerTimerFromOutSize: Int,
    //background color
) {
    
}