package com.example.travlingfocus.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
//    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {
    val shownSplash = mutableStateOf(SplashState.Shown)

    val timerState = mutableStateOf(TimerType.Timer)

    val mayBeginGifAnimation = mutableStateOf(false)

    private  val _timerValue = MutableLiveData<Float>(60000f)
    val timerValue: LiveData<Float>
        get() = _timerValue

    fun updateTimerValue(value: Float){
        _timerValue.value = value
    }

    val timeOptions = List(10) { (it * 5 + 5).toString() }
}

