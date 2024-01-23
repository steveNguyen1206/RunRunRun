package com.example.travlingfocus.rewardscreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
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
    val timeOptions : List<String> = listOf("Day", "Week", "Month", "Year", "All Time")

    var timeType by mutableStateOf(TimeType.Day)
    fun chooseOption(option: String){
        when(option){
            "Day" -> timeType = TimeType.Day
            "Week" -> timeType = TimeType.Week
            "Month" -> timeType = TimeType.Month
            "Year" -> timeType = TimeType.Year
            "All Time" -> timeType = TimeType.All
        }
    }

    fun getTimeString(): String{
        return when(timeType){
            TimeType.Day -> "Day"
            TimeType.Week -> "Week"
            TimeType.Month -> "Month"
            TimeType.Year -> "Year"
            TimeType.All -> "All Time"
        }
    }


//  Bottom Sheet State
    var isSheetOpen = mutableStateOf(true)

    fun getSheetState(): State<Boolean>{
        return isSheetOpen
    }
    fun openBottomSheet(){
        isSheetOpen.value = true
    }
    fun closeBottomSheet(){
        isSheetOpen.value = false
    }

    // Data
    fun getTotalHours() : Double {
        return 96.7
    }
}