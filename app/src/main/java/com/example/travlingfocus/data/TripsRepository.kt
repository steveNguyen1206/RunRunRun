package com.example.travlingfocus.data

import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

// Interface to solve with trip
interface TripsRepository{

    fun getAllTripsStrem(): Flow<List<Trip>>

    fun getTripStream(id: Int): Flow<Trip?>

    suspend fun insertTrip(trip: Trip)

    suspend fun deleteTrip(trip: Trip)

    suspend fun updateTrip(trip: Trip)

    suspend fun getAllTripsInRange(userId:Int = 0, L: Long = 0, R: Long = Date().time): List<Trip>

    suspend fun getTotalDurationInRange(userId:Int = 0, L: Long = 0, R: Long = Date().time): Float

    suspend fun getCompleteDestinationInRange(userId:Int = 0, L: Long = 0,  R: Long = Date().time): List<Trip>

}