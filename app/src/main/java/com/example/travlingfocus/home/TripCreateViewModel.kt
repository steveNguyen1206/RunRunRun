package com.example.travlingfocus.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.travlingfocus.data.Trip
import com.example.travlingfocus.data.TripsRepository
import java.sql.Time
import java.time.LocalDate
import java.util.Date

class TripCreateViewModel(private val tripsRepository: TripsRepository) : ViewModel() {
    var tripUiState by mutableStateOf(TripUiState())
        private set

    fun updateUiState (tripDetails: TripDetails) {
        tripUiState = TripUiState(tripDetails, isEntryValid(tripDetails))
    }

    suspend fun saveTrip() {
        val trip = tripUiState.tripDetails.toTrip()
        if(isEntryValid(tripUiState.tripDetails))
            tripsRepository.insertTrip(trip)
        else Log.d("TripCreateViewModel", "saveTrip: invalid trip: ${trip.toString()}")
    }

    fun isEntryValid(tripDetails: TripDetails): Boolean {
        return tripDetails.destination.isNotBlank() && tripDetails.tag.isNotBlank()
                && tripDetails.duration > 0.0f
                && tripDetails.startTime.time < tripDetails.endTime.time
                && tripDetails.desResId > 0
    }
}

data class TripUiState(
    val tripDetails: TripDetails = TripDetails(),
    val isEntryValid: Boolean = false,
)

data class TripDetails (
    val id: Int = 0,
    val userId: Int = 0,
    val destination: String = Destinations.HaGiang.place,
    val desResId: Int = Destinations.HaGiang.imageId,
    val completed: Boolean = false,
    val startTime: Date = Date(),
    val endTime: Date = Date(),
    val duration: Float = 60000f,
    val tag: String = ActivityTag.friend.name,
)

fun TripDetails.toTrip(): Trip {
    return Trip(
        id = id,
        userId = userId,
        destination = destination,
        desResId = desResId,
        completed = completed,
        startTime = startTime.time,
        endTime = endTime.time,
        duration = duration,
        tag = tag,
    )
}

fun Trip.toTripDetails(): TripDetails {
    return TripDetails(
        id = id,
        userId = userId,
        destination = destination,
        desResId = desResId,
        completed = completed,
        startTime = Date(startTime),
        endTime = Date(endTime),
        duration = duration,
        tag = tag,
    )
}