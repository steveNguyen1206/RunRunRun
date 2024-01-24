package com.example.travlingfocus.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.util.Date

// Dao: Data Access Object
@Dao
interface TripDao {
    @Query("SELECT * from trips ORDER BY startTime ASC")
    fun getAllTrips(): Flow<List<Trip>>

    @Query("SELECT * from trips WHERE id = :id")
    fun getTrip(id: Int): Flow<Trip>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(trip: Trip)

    @Update
    suspend fun update(trip: Trip)

    @Delete
    suspend fun delete(trip: Trip)

////    Get Total Duration with startTime and endTime in Range L -> R
    @Query("SELECT * FROM trips WHERE userId == :userId AND startTime >= :L AND endTime <= :R ORDER BY endTime ASC")
    suspend fun getAllTripsInRange(userId: Int = 0, L: Long = 0, R: Long = Date().time): List<Trip>
//
//
    @Query("SELECT IFNULL(SUM(duration), 0) FROM trips WHERE userId == :userId AND startTime >= :L AND endTime <= :R")
    suspend fun getTotalDurationInRange(userId: Int = 0, L: Long = 0, R: Long = Date().time): Float
//
////    Get destination with startTime and endTime in Range L -> R
    @Query("SELECT * FROM trips WHERE userId == :userId AND completed == 1 AND startTime >= :L AND endTime <= :R ORDER BY endTime ASC")
    suspend fun getCompleteDestinationInRange(userId: Int = 0, L: Long = 0, R: Long = Date().time): List<Trip>
}