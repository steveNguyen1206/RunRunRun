package com.example.travlingfocus.rewardscreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.integerResource
import androidx.lifecycle.ViewModel
import com.example.travlingfocus.R
import com.example.travlingfocus.home.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RewardViewModel @Inject constructor(
//    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {
    // region   timeType
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
// endregion

    // region  Bottom Sheet State
    var isSheetOpen = mutableStateOf(false)

    fun getSheetState(): State<Boolean>{
        return isSheetOpen
    }
    fun openBottomSheet(){
        isSheetOpen.value = true
    }
    fun closeBottomSheet(){
        isSheetOpen.value = false
    }
//endregion

    //region Data
    fun getTotalHours() : Double {
        return 96.7
    }
    //    endregion

    // region   Places
    fun getPlaces(): List<Place>{
        return listOf(
            Place.Place1,
            Place.Place2,
            Place.Place3,
            Place.Place4,
            Place.Place5,
            Place.Place6,
            Place.NextPlace
        )
    }
    val pinColors = listOf<Color>(
        Color(0xFFDE1919),
        Color(0xFF21AAFF),
        Color(0xFFF9C713),
        Color(0xFFF90E5C),
        Color(0xFFF7690B),
        Color(0xFF6ABD6A),
    )
    fun getPinColor(index: Int): Color{
        return pinColors[index % pinColors.size]
    }



    val _selectedPlaceIndex = mutableStateOf(0)
    val selectedPlaceIndex
        get() = _selectedPlaceIndex.value

    fun setSelectedPlaceIndex(index: Int){
        _selectedPlaceIndex.value = index
    }
    //    endregion
}

sealed class Place(
    val imageId: Int,
    val name: String = "",
){
    object Place1: Place(imageId = R.drawable.can_tho, name = "Can Tho")
    object Place2: Place(imageId = R.drawable.da_nang, name = "Da Nang")
    object Place3: Place(imageId = R.drawable.da_lat, name = "Da Lat")
    object Place4: Place(imageId = R.drawable.ha_noi, name = "Ha Noi")
    object Place5: Place(imageId = R.drawable.sa_pa, name = "Sa Pa")
    object Place6: Place(imageId = R.drawable.vung_tau, name = "Vung Tau")
    object NextPlace: Place(imageId = R.drawable.next_place, name = "Next Place")
}