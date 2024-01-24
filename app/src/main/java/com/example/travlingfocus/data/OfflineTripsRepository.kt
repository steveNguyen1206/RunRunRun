package com.example.travlingfocus.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineTripsRepository @Inject constructor
    (private val tripDao: TripDao) : TripsRepository {
    override fun getAllTripsStrem(): Flow<List<Trip>> = tripDao.getAllTrips()

    override fun getTripStream(id: Int): Flow<Trip?> = tripDao.getTrip(id)

    override suspend fun insertTrip(trip: Trip) = tripDao.insert(trip)

    override suspend fun deleteTrip(trip: Trip) = tripDao.delete(trip)

    override suspend fun updateTrip(trip: Trip) = tripDao.update(trip)

    override fun getAllStartTime(): Flow<List<Long>> = tripDao.getAllStartTime()

    override fun getTripInRange(startTime: Long, endTime: Long): Flow<List<Trip>> = tripDao.getTripInRange(startTime, endTime)
}