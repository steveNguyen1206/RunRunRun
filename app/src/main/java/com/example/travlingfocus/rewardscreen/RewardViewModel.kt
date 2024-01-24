package com.example.travlingfocus.rewardscreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.integerResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travlingfocus.R
import com.example.travlingfocus.data.Trip
import com.example.travlingfocus.data.TripsRepository
import com.example.travlingfocus.home.ActivityTag
import com.example.travlingfocus.home.Destinations
import com.example.travlingfocus.home.SplashState
import com.example.travlingfocus.home.toActivityTag
import com.example.travlingfocus.home.toActivityTagString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.math.RoundingMode
import java.math.RoundingMode.HALF_EVEN
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import kotlin.math.log

class RewardViewModel (
    private val tripsRepository: TripsRepository
) : ViewModel() {
    var rewardUiState by mutableStateOf(RewardScreenUiState())
        private set

    init {
        viewModelScope.launch {
            rewardUiState = rewardUiState.updateData(tripsRepository)
        }
    }

    suspend fun updateOption(option: String){
        rewardUiState = rewardUiState
            .changeOption(option.toTimeType())
            .updateData(tripsRepository)
    }

    fun openSheet(){
        rewardUiState = rewardUiState.copy(isOpenSheet = true)
    }

    fun closeSheet(){
        rewardUiState = rewardUiState.copy(isOpenSheet = false)
    }

    suspend fun updateData(){
        rewardUiState = rewardUiState.updateData(tripsRepository)
    }

    val totalDuration: Float
        get() = rewardUiState.totalDuration

    val timeOption: String
        get() = rewardUiState.option.toTimeString()

    val options : List<String>
        get() = TimeType.entries.map { it.toTimeString() }

    val destinations: List<Pair<Int, String>>
        get() = rewardUiState.destinations

    val sheetState: Boolean
        get() = rewardUiState.isOpenSheet

    val barChartListSize : Int
        get() = rewardUiState.sheetData.barChartListSize

    val eachGroupBarSize: Int
        get() = rewardUiState.sheetData.eachGroupBarSize

    val yStepSize: Int
        get() = rewardUiState.sheetData.yStepSize

    val groupBarData: List<List<Float>>
        get() = rewardUiState.sheetData.groupBarData

    val itemBarColors: List<Color>
        get() = rewardUiState.sheetData.itemBarColors()

    val itemBarNames: List<String>
        get() = rewardUiState.sheetData.itemBarNames

    val groupBarNames: List<String>
        get() = rewardUiState.sheetData.groupBarNames

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

}

data class RewardScreenUiState(
    val option: TimeType = TimeType.Day,
    val totalDuration: Float = 0f,
    val destinations: List<Pair<Int, String>> = listOf(),
    val isOpenSheet: Boolean = false,
    val sheetData: RewardSheetData = RewardSheetData(),
)
{
    fun changeOption(option: TimeType): RewardScreenUiState {
        return this.copy(option = option, isOpenSheet = false)
    }

    suspend fun getDuration(tripsRepository: TripsRepository): Float {
        return tripsRepository.getTotalDurationInRange(L = queryDateStart(option), R = queryDateEnd(option))
    }

    suspend fun getDestinations(tripsRepository: TripsRepository): List<Trip> {
        return tripsRepository.getCompleteDestinationInRange(L = queryDateStart(option), R = queryDateEnd(option))
    }

    suspend fun getTrips(tripsRepository: TripsRepository): List<Trip> {
        return tripsRepository.getAllTripsInRange(L = queryDateStart(option), R = queryDateEnd(option))
    }

    suspend fun updateData(tripsRepository: TripsRepository): RewardScreenUiState {
        var state = this
        return withContext(Dispatchers.Default) {
            // Call the suspend functions and collect the results
            val duration = async { getDuration(tripsRepository) }
            val destinations = async { getDestinations(tripsRepository) }
            val trips = async { getTrips(tripsRepository) }

            Log.d("RewardViewModel", "update data")

            val updatedState = state.copy(
                totalDuration = duration.await().toHours(),
                destinations = destinations.await().map { Pair<Int, String>(it.desResId, it.destination) },
                sheetData = trips.await().toRewardSheetData(state.option)
            )

            // All async tasks have completed, return the updated state
            Log.d("RewardViewModel", "updateData: $updatedState")
            return@withContext updatedState
        }
    }
}

fun List<Trip>.toRewardSheetData(option: TimeType) : RewardSheetData {
    val pattern = "dd/MM"
    val dateFormatter = DateTimeFormatter.ofPattern(pattern)
    val currentDate = LocalDate.now().format(dateFormatter)

    return when(option) {
        TimeType.Day -> RewardSheetData().listTripToDay(this, groupName = "${currentDate}")
        TimeType.Week -> RewardSheetData().listTripToWeek(this)
        TimeType.Month -> RewardSheetData().listTripToMonth(this)
        TimeType.Year -> RewardSheetData().listTripToYear(this)
        else -> RewardSheetData().listTripToAll(this)
    }
}

data class RewardSheetData(
    val groupBarData: List<List<Float>> = listOf(),
    val itemBarNames:List<String> = listOf<String>(),
    val yStepSize: Int = 5,
    val groupBarNames: List<String> = listOf(),
){
    val barChartListSize: Int = groupBarData.size
    val eachGroupBarSize: Int = if (barChartListSize > 0) groupBarData[0].size else 0
    fun itemBarColors(): List<Color>{
        return itemBarNames.map { it.toActivityTag().color }.toList()
    }

    fun listTripToDay(trips: List<Trip>, groupName: String = "Day"): RewardSheetData{
//        count total duration for each tag
        val totalDuration = mutableMapOf<ActivityTag, Float>()
        for (trip in trips){
            val tag = trip.tag.toActivityTag()
            if (totalDuration.containsKey(tag)){
                totalDuration[tag] = totalDuration[tag]!! + trip.duration.toHours()
            } else {
                totalDuration[tag] = trip.duration.toHours()
            }
        }

//        convert to groupBarData
        val list:MutableList<Float> = mutableListOf()
        val itemBarNames:MutableList<String> = mutableListOf()

        for(tag in ActivityTag.entries){
            val duration = totalDuration[tag] ?: 0f
            list.add(duration)
            itemBarNames.add(tag.toActivityTagString())
        }


        val groupBarData = listOf(list)
        val groupBarNames = listOf(groupName)

        return RewardSheetData(groupBarData, itemBarNames, groupBarNames = groupBarNames)
    }

    fun listTripToWeek(trips: List<Trip>): RewardSheetData{
        //       count total duration for each tag in each day

        val dateStart = queryDateStart(TimeType.Week)
        val dist = 3600L * 24 * 1000

        var groupBarData: MutableList<List<Float>> = mutableListOf()
        var groupBarNames: MutableList<String> = mutableListOf()
        var itemBarNames: MutableList<String> = mutableListOf()

        for(i in 0..6){
            val date = dateStart + i * dist
            val dateEnd = date + dist

            var list : MutableList<Trip> = mutableListOf()
            for (trip in trips){
                if (trip.startTime >= date && trip.endTime < dateEnd){
                    list.add(trip)
                }
            }

            val sheetDataDay = listTripToDay(list, groupName = "${dayOfWeek[i]}")
            groupBarData.add(sheetDataDay.groupBarData[0])
            groupBarNames.add(dayOfWeek[i])
            itemBarNames = sheetDataDay.itemBarNames.toMutableList()
        }

        return RewardSheetData(groupBarData, itemBarNames, groupBarNames = groupBarNames)
    }

    fun listTripToMonth(trips: List<Trip>): RewardSheetData {
        //       count total duration for each tag in each day

        val dateStart = queryDateStart(TimeType.Month)
        val dist = 3600L * 24 * 1000 * 7

        var groupBarData: MutableList<List<Float>> = mutableListOf()
        var groupBarNames: MutableList<String> = mutableListOf()
        var itemBarNames: MutableList<String> = mutableListOf()

        for(i in 0..4){
            val date = dateStart + i * dist
            val dateEnd = date + dist

            var map : MutableMap<ActivityTag, Float> = mutableMapOf()
            for (trip in trips){
                if (trip.startTime >= date && trip.endTime < dateEnd){
                    val tag = trip.tag.toActivityTag()
                    if (map.containsKey(tag)){
                        map[tag] = map[tag]!! + trip.duration.toHours()
                    } else {
                        map[tag] = trip.duration.toHours()
                    }
                }
            }

            var itemNames : MutableList<String> = mutableListOf()
            var list : MutableList<Float> = mutableListOf()
            for(tag in ActivityTag.entries){
                val duration = map[tag] ?: 0f
                list.add(duration)
                itemNames.add(tag.toActivityTagString())
            }

            groupBarData.add(list)

            groupBarNames.add("Week ${i+1}")
            itemBarNames = itemNames
        }

        return RewardSheetData(groupBarData, itemBarNames, groupBarNames = groupBarNames)
    }

    fun listTripToYear(trips: List<Trip>): RewardSheetData {
        //       count total duration for each tag in each day

        var groupBarData: MutableList<List<Float>> = mutableListOf()
        var groupBarNames: MutableList<String> = mutableListOf()
        var itemBarNames: MutableList<String> = mutableListOf()

        val dateNote = Date()

        for(i in 0..10){
            val calendar = Calendar.getInstance();
            calendar.time = dateNote
            calendar.set(Calendar.MONTH, i)
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            val date = calendar.timeInMillis
            calendar.set(Calendar.MONTH, i+1)
            val dateEnd = calendar.timeInMillis

            var map : MutableMap<ActivityTag, Float> = mutableMapOf()
            for (trip in trips){
                if (trip.startTime >= date && trip.endTime < dateEnd){
                    val tag = trip.tag.toActivityTag()
                    if (map.containsKey(tag)){
                        map[tag] = map[tag]!! + trip.duration.toHours()
                    } else {
                        map[tag] = trip.duration.toHours()
                    }
                }
            }

            var itemNames : MutableList<String> = mutableListOf()
            var list : MutableList<Float> = mutableListOf()
            for(tag in ActivityTag.entries){
                val duration = map[tag] ?: 0f
                list.add(duration)
                itemNames.add(tag.toActivityTagString())
            }

            groupBarData.add(list)

            groupBarNames.add("Month ${i+1}")
            itemBarNames = itemNames
        }

        val calendar = Calendar.getInstance();
        calendar.time = dateNote
        calendar.set(Calendar.MONTH, 11)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val date = calendar.timeInMillis
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1)
        calendar.set(Calendar.MONTH, 0)
        val dateEnd = calendar.timeInMillis

        var map : MutableMap<ActivityTag, Float> = mutableMapOf()
        for (trip in trips){
            if (trip.startTime >= date && trip.endTime < dateEnd){
                val tag = trip.tag.toActivityTag()
                if (map.containsKey(tag)){
                    map[tag] = map[tag]!! + trip.duration.toHours()
                } else {
                    map[tag] = trip.duration.toHours()
                }
            }
        }

        var itemNames : MutableList<String> = mutableListOf()
        var list : MutableList<Float> = mutableListOf()
        for(tag in ActivityTag.entries){
            val duration = map[tag] ?: 0f
            list.add(duration)
            itemNames.add(tag.toActivityTagString())
        }

        groupBarData.add(list)

        groupBarNames.add("Month 12")
        itemBarNames = itemNames


        return RewardSheetData(groupBarData, itemBarNames, groupBarNames = groupBarNames)
    }

    fun listTripToAll(trips: List<Trip>) : RewardSheetData{
        //       count total duration for each tag in each day

        var groupBarData: MutableList<List<Float>> = mutableListOf()
        var groupBarNames: MutableList<String> = mutableListOf()
        var itemBarNames: MutableList<String> = mutableListOf()

        var map : MutableMap<ActivityTag, Float> = mutableMapOf()
        for (trip in trips){
            val tag = trip.tag.toActivityTag()
            if (map.containsKey(tag)){
                map[tag] = map[tag]!! + trip.duration.toHours()
            } else {
                map[tag] = trip.duration.toHours()
            }
        }

        var itemNames : MutableList<String> = mutableListOf()
        var list : MutableList<Float> = mutableListOf()
        for(tag in ActivityTag.entries){
            val duration = map[tag] ?: 0f
            list.add(duration)
            itemNames.add(tag.toActivityTagString())
        }

        groupBarData.add(list)

        groupBarNames.add("All data")
        itemBarNames = itemNames

        return RewardSheetData(groupBarData, itemBarNames, groupBarNames = groupBarNames)
    }
}

enum class TimeType{
    Day, Week,  Month, Year, All
}

fun String.toTimeType(): TimeType {
    return when (this) {
        "Day" -> TimeType.Day
        "Week" -> TimeType.Week
        "Month" -> TimeType.Month
        "Year" -> TimeType.Year
        "All Time" -> TimeType.All
        else -> TimeType.All
    }
}
fun TimeType.toTimeString(): String{
    return when(this){
        TimeType.Day -> "Day"
        TimeType.Week -> "Week"
        TimeType.Month -> "Month"
        TimeType.Year -> "Year"
        TimeType.All -> "All Time"
    }
}

private fun Float.toHours(): Float{
    return BigDecimal(this.toDouble() / (1000L * 3600))
        .setScale(2, RoundingMode.HALF_UP)
        .toFloat()
}

private fun queryDateStart(option: TimeType): Long {
    val calendar = Calendar.getInstance()
    calendar.time = Date()

    return when (option) {
        TimeType.Day -> {
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            calendar.timeInMillis
        }
        TimeType.Week -> {
            calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            calendar.timeInMillis
        }
        TimeType.Month -> {
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            calendar.timeInMillis
        }
        TimeType.Year -> {
            calendar.set(Calendar.DAY_OF_YEAR, 1)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            calendar.timeInMillis
        }
        TimeType.All -> 0
    }
}
private fun queryDateEnd(option: TimeType): Long{
    val calendar = Calendar.getInstance()
    calendar.time = Date()

    return when (option) {
        TimeType.Day -> {
            calendar.set(Calendar.HOUR_OF_DAY, 23)
            calendar.set(Calendar.MINUTE, 59)
            calendar.set(Calendar.SECOND, 59)
            calendar.set(Calendar.MILLISECOND, 999)
            calendar.timeInMillis
        }
        TimeType.Week -> {
            calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek + 6)
            calendar.set(Calendar.HOUR_OF_DAY, 23)
            calendar.set(Calendar.MINUTE, 59)
            calendar.set(Calendar.SECOND, 59)
            calendar.set(Calendar.MILLISECOND, 999)
            calendar.timeInMillis
        }
        TimeType.Month -> {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            calendar.set(Calendar.HOUR_OF_DAY, 23)
            calendar.set(Calendar.MINUTE, 59)
            calendar.set(Calendar.SECOND, 59)
            calendar.set(Calendar.MILLISECOND, 999)
            calendar.timeInMillis
        }
        TimeType.Year -> {
            calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR))
            calendar.set(Calendar.HOUR_OF_DAY, 23)
            calendar.set(Calendar.MINUTE, 59)
            calendar.set(Calendar.SECOND, 59)
            calendar.set(Calendar.MILLISECOND, 999)
            calendar.timeInMillis
        }
        TimeType.All -> Date().time
    }
}
private val dayOfWeek = listOf<String>("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
private val monthOfYear = listOf<String>(
    "Jan", "Feb", "Mar", "Apr", "May", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov","Dec"
)


// region Destinations
//enum class Destinations (val imageId: Int, val place: String)
//{
//    CanTho(R.drawable.img_cantho, "Cần Thơ"),
//    DaLat(R.drawable.img_dalat, "Đà Lạt"),
//    DaNang(R.drawable.img_danang, "Đà Nẵng"),
//    HaNoi(R.drawable.img_hanoi, "Hà Nội"),
//    HoChiMinh(R.drawable.img_hcm, "TP Hồ Chí Minh"),
//    Hue(R.drawable.img_hue, "Huế"),
//    NhaTrang(R.drawable.img_nhatrang, "Nha Trang"),
//    PhuQuoc(R.drawable.img_phuquoc, "Phú Quốc"),
//    //    QuangBinh(R.drawable.img_quangbinh),
////    QuangNam(R.drawable.img_quangnam),
////    QuangNinh(R.drawable.img_quangninh),
//    VungTau(R.drawable.img_vungtau, "Vũng Tàu"),
//    HoiAn(R.drawable.img_hoian, "Hội An"),
//    HaiPhong(R.drawable.img_haiphong, "Hải Phòng"),
//    HaLong(R.drawable.img_halong, "Hạ Long"),
//    NinhBinh(R.drawable.img_ninhbinh, "Ninh Bình"),
//    HaGiang(R.drawable.img_hagiang, "Hà Giang"),
//    SaPa(R.drawable.img_sapa, "Sa Pa"),
//    MocChau(R.drawable.img_mocchau, "Mộc Châu"),
//    PhanThiet(R.drawable.img_phanthiet, "Phan Thiết"),
//
//}
// endregion

// region ActivityTag
//enum class ActivityTag {
//    friend, study, family, sport, work, other
//}
//fun String.toActivityTag(): ActivityTag {
//    return when (this) {
//        "friend" -> ActivityTag.friend
//        "study" -> ActivityTag.study
//        "family" -> ActivityTag.family
//        "sport" -> ActivityTag.sport
//        "work" -> ActivityTag.work
//        else -> ActivityTag.other
//    }
//}
// endregion