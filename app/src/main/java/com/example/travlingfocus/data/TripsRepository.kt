package com.example.travlingfocus.data

import kotlinx.coroutines.flow.Flow

interface TripsRepository {

    fun getAllTripsStrem(): Flow<List<Trip>>

    fun getTripStream(id: Int): Flow<Trip?>

    suspend fun insertTrip(trip: Trip)

    suspend fun deleteTrip(trip: Trip)

    suspend fun updateTrip(trip: Trip)
}