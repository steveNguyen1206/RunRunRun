package com.example.travlingfocus.rewardscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.travlingfocus.home.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RewardViewModel @Inject constructor(
//    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {
    var timeType by mutableStateOf(TimeType.Day)

    fun changeTimeType(timeType: TimeType){
        this.timeType = timeType
    }

    fun getTimeString(): String{
        return when(timeType){
            TimeType.Day -> "Day"
            TimeType.Month -> "Month"
            TimeType.Year -> "Year"
        }
    }
}