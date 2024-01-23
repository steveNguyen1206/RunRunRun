package com.example.travlingfocus.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travlingfocus.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//float constant of 600000f
val DEFAULT_TIMER_VALUE = 600000f

@HiltViewModel
class MainViewModel @Inject constructor(
//    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher

) : ViewModel() {
    val shownSplash = mutableStateOf(SplashState.Shown)

    val timerState = mutableStateOf(TimerType.Timer)

    private val _selectedTag = MutableLiveData<ActivityTag>(ActivityTag.friend)
    val selectedTag: LiveData<ActivityTag>
        get() = _selectedTag
    fun updateTag(tag: ActivityTag) {
        _selectedTag.value = tag
    }

    private  val _timerValue = MutableLiveData<Float>(DEFAULT_TIMER_VALUE)
    val timerValue: LiveData<Float>
        get() = _timerValue

    fun updateTimerValue(value: Float){
        _timerValue.value = value
    }
    val timeOptions = List(10) { (it * 5 + 5).toString() }

//    private val _selectedSouvenir = MutableLiveData<Souvenir>(Souvenir.Sourvenir1)
//    val selectedSouvenir: LiveData<Souvenir>
//        get() = _selectedSouvenir
//    fun updateSelectedSouvenir(souvenir: Souvenir) {
//        _selectedSouvenir.value = souvenir
//    }

    private val _selctedDestination = MutableLiveData<Destinations>(Destinations.HaGiang)
    val selectedDestination: LiveData<Destinations>
        get() = _selctedDestination
    fun updateSelectedDestination(destination: Destinations) {
        _selctedDestination.value = destination
    }
}


enum class ActivityTag {
    friend, study, family, sport, work, other
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

enum class Destinations (val imageId: Int, val place: String)
{
    CanTho(R.drawable.img_cantho, "Cần Thơ"),
    DaLat(R.drawable.img_dalat, "Đà Lạt"),
    DaNang(R.drawable.img_danang, "Đà Nẵng"),
    HaNoi(R.drawable.img_hanoi, "Hà Nội"),
    HoChiMinh(R.drawable.img_hcm, "TP Hồ Chí Minh"),
    Hue(R.drawable.img_hue, "Huế"),
    NhaTrang(R.drawable.img_nhatrang, "Nha Trang"),
    PhuQuoc(R.drawable.img_phuquoc, "Phú Quốc"),
//    QuangBinh(R.drawable.img_quangbinh),
//    QuangNam(R.drawable.img_quangnam),
//    QuangNinh(R.drawable.img_quangninh),
    VungTau(R.drawable.img_vungtau, "Vũng Tàu"),
    HoiAn(R.drawable.img_hoian, "Hội An"),
    HaiPhong(R.drawable.img_haiphong, "Hải Phòng"),
    HaLong(R.drawable.img_halong, "Hạ Long"),
    NinhBinh(R.drawable.img_ninhbinh, "Ninh Bình"),
    HaGiang(R.drawable.img_hagiang, "Hà Giang"),
    SaPa(R.drawable.img_sapa, "Sa Pa"),
    MocChau(R.drawable.img_mocchau, "Mộc Châu"),
    PhanThiet(R.drawable.img_phanthiet, "Phan Thiết"),

}





