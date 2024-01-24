package com.example.travlingfocus.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// Query Trip Offline and using tripDao - Room
class OfflineTripsRepository @Inject constructor
    (private val tripDao: TripDao) : TripsRepository {
    override fun getAllTripsStrem(): Flow<List<Trip>> = tripDao.getAllTrips()

    override fun getTripStream(id: Int): Flow<Trip?> = tripDao.getTrip(id)

    override suspend fun insertTrip(trip: Trip) = tripDao.insert(trip)

    override suspend fun deleteTrip(trip: Trip) = tripDao.delete(trip)

    override suspend fun updateTrip(trip: Trip) = tripDao.update(trip)

    override suspend fun getAllTripsInRange(userId:Int, L: Long, R: Long): List<Trip> = tripDao.getAllTripsInRange(userId, L = L, R = R)

    override suspend fun getTotalDurationInRange(userId:Int, L: Long, R: Long): Float = tripDao.getTotalDurationInRange(userId, L = L, R = R)

    override suspend fun getCompleteDestinationInRange(userId:Int, L: Long, R: Long): List<Trip> = tripDao.getCompleteDestinationInRange(userId, L = L, R = R)
}