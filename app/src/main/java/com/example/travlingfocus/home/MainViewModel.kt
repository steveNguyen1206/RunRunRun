package com.example.travlingfocus.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travlingfocus.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
//    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {
    val shownSplash = mutableStateOf(SplashState.Shown)

    val timerState = mutableStateOf(TimerType.Timer)

    val tag = mutableStateOf(ActivityTag.frend)

    private  val _timerValue = MutableLiveData<Float>(60000f)
    val timerValue: LiveData<Float>
        get() = _timerValue

    fun updateTimerValue(value: Float){
        _timerValue.value = value
    }

    val timeOptions = List(10) { (it * 5 + 5).toString() }

    val souvenirList = listOf(
        Souvenir.Sourvenir1,
        Souvenir.Sourvenir2,
        Souvenir.Sourvenir3,
        Souvenir.Sourvenir4,
        Souvenir.Sourvenir5,
        Souvenir.Sourvenir6,
        Souvenir.Sourvenir7,
        Souvenir.Sourvenir8,
        Souvenir.Sourvenir9,
        Souvenir.Sourvenir10,
        Souvenir.Sourvenir11,
    )
}

enum class ActivityTag {
    frend, study, family, sport, work, other
}

sealed class Souvenir(
    var imageId: Int
)
{
    object Sourvenir1: Souvenir(imageId = R.drawable.cute_travel_1)
    object Sourvenir2: Souvenir(imageId = R.drawable.cute_travel_2)
    object Sourvenir3: Souvenir(imageId = R.drawable.cute_travel_3)
    object Sourvenir4: Souvenir(imageId = R.drawable.cute_travel_4)
    object Sourvenir5: Souvenir(imageId = R.drawable.cute_travel_5)
    object Sourvenir6: Souvenir(imageId = R.drawable.cute_travel_6)
    object Sourvenir7: Souvenir(imageId = R.drawable.cute_travel_7)
    object Sourvenir8: Souvenir(imageId = R.drawable.cute_travel_8)
    object Sourvenir9: Souvenir(imageId = R.drawable.cute_travel_9)
    object Sourvenir10: Souvenir(imageId = R.drawable.cute_travel_10)
    object Sourvenir11: Souvenir(imageId = R.drawable.cute_travel_11)

}




