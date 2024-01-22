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

    private val _selectedTag = MutableLiveData<ActivityTag>(ActivityTag.frend)
    val selectedTag: LiveData<ActivityTag>
        get() = _selectedTag
    fun updateTag(tag: ActivityTag) {
        _selectedTag.value = tag
    }

    private  val _timerValue = MutableLiveData<Float>(60000f)
    val timerValue: LiveData<Float>
        get() = _timerValue

    fun updateTimerValue(value: Float){
        _timerValue.value = value
    }
    val timeOptions = List(10) { (it * 5 + 5).toString() }

    private val _selectedSouvenir = MutableLiveData<Souvenir>(Souvenir.Sourvenir1)
    val selectedSouvenir: LiveData<Souvenir>
        get() = _selectedSouvenir
    fun updateSelectedSouvenir(souvenir: Souvenir) {
        _selectedSouvenir.value = souvenir
    }
}


enum class ActivityTag {
    frend, study, family, sport, work, other
}

enum class Souvenir(val imageId: Int)
{
    Sourvenir1(R.drawable.cute_travel_1),
    Sourvenir2(R.drawable.cute_travel_2),
    Sourvenir3(R.drawable.cute_travel_3),
    Sourvenir4(R.drawable.cute_travel_4),
    Sourvenir5(R.drawable.cute_travel_5),
    Sourvenir6(R.drawable.cute_travel_6),
    Sourvenir7(R.drawable.cute_travel_7),
    Sourvenir8(R.drawable.cute_travel_8),
    Sourvenir9(R.drawable.cute_travel_9),
    Sourvenir10(R.drawable.cute_travel_10),
    Sourvenir11(R.drawable.cute_travel_11),
}

//sealed class Souvenir(var imageId: Int)
//{
//    object Sourvenir1: Souvenir(imageId = R.drawable.cute_travel_1)
//    object Sourvenir2: Souvenir(imageId = R.drawable.cute_travel_2)
//    object Sourvenir3: Souvenir(imageId = R.drawable.cute_travel_3)
//    object Sourvenir4: Souvenir(imageId = R.drawable.cute_travel_4)
//    object Sourvenir5: Souvenir(imageId = R.drawable.cute_travel_5)
//    object Sourvenir6: Souvenir(imageId = R.drawable.cute_travel_6)
//    object Sourvenir7: Souvenir(imageId = R.drawable.cute_travel_7)
//    object Sourvenir8: Souvenir(imageId = R.drawable.cute_travel_8)
//    object Sourvenir9: Souvenir(imageId = R.drawable.cute_travel_9)
//    object Sourvenir10: Souvenir(imageId = R.drawable.cute_travel_10)
//    object Sourvenir11: Souvenir(imageId = R.drawable.cute_travel_11)
//    companion object {
//        fun values(): Any {
//            return listOf(
//                Sourvenir1,
//                Sourvenir2,
//                Sourvenir3,
//                Sourvenir4,
//                Sourvenir5,
//                Sourvenir6,
//                Sourvenir7,
//                Sourvenir8,
//                Sourvenir9,
//                Sourvenir10,
//                Sourvenir11,
//            )
//        }
//    }
//}




