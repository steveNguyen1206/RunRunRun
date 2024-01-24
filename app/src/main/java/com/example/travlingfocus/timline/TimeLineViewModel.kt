package com.example.travlingfocus.timline

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travlingfocus.data.Trip
import com.example.travlingfocus.data.TripsRepository
import com.example.travlingfocus.home.TripDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.Date

class TimeLineViewModel(tripsRepository: TripsRepository) : ViewModel() {
    private val _timelineUiState = MutableStateFlow(TimelineUiState())
    val timelineUiState: StateFlow<TimelineUiState> = _timelineUiState

    init {
        viewModelScope.launch {
                launch {
                    tripsRepository.getAllTripsStrem().collect { trips ->
                        _timelineUiState.value = _timelineUiState.value.copy(
                            tripLists = trips,
                            tripByDay = getTripByDay(_timelineUiState.value.currentDay, trips)
                        )
                    }
                }
                launch {
                    tripsRepository.getAllStartTime().collect { startTimes ->
                        _timelineUiState.value = _timelineUiState.value.copy(allStartDays = startTimes)
                    }
                }

        }
    }

    fun updateCurrentDay (day: Date) {
        _timelineUiState.value = _timelineUiState.value.copy(
            currentDay = day, tripByDay = getTripByDay(day, _timelineUiState.value.tripLists)
        )
    }


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class TimelineUiState(
    val tripLists: List<Trip> = listOf(),
    val currentDay: Date = Date(),
    val allStartDays: List<Long> = listOf(),
    val tripByDay: List<Trip> = getTripByDay(currentDay, tripLists),
)

fun getTripByDay(currentDay : Date, tripLists: List<Trip>): List<Trip> {
    val startOfDay = getStartOfDayMillis(currentDay)
    val endOfDay = getEndOfDayMillis(currentDay)
    return tripLists.filter { trip ->
        trip.startTime in startOfDay..endOfDay
    }
}
fun getEndOfDayMillis(inputDate: Date): Long {
    val instant = inputDate.toInstant()
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    val endOfDay = localDateTime.toLocalDate().atTime(LocalTime.MAX)
    return endOfDay.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun getStartOfDayMillis(inputDate: Date): Long {
    val instant = inputDate.toInstant()
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    val startOfDay = localDateTime.toLocalDate().atTime(LocalTime.MIN)
    return startOfDay.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}