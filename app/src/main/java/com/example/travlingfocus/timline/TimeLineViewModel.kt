package com.example.travlingfocus.timline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travlingfocus.data.Trip
import com.example.travlingfocus.data.TripsRepository
import com.example.travlingfocus.home.TripDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TimeLineViewModel(tripsRepository: TripsRepository) : ViewModel() {
    val timelineUiState: StateFlow<TimelineUiState> =
        tripsRepository.getAllTripsStrem().map { trip ->
            TimelineUiState(trip)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = TimelineUiState()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class TimelineUiState(
    val tripLists: List<Trip> = listOf(),
)