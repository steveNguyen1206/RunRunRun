package com.example.travlingfocus.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {
    @Query("SELECT * from trips ORDER BY startTime ASC")
    fun getAllTrips(): Flow<List<Trip>>

    @Query("SELECT * from trips WHERE id = :id")
    fun getTrip(id: Int): Flow<Trip>

    @Query("SELECT startTime from trips")
    fun getAllStartTime(): Flow<List<Long>>

    @Query("SELECT * from trips WHERE userId = :userId")
    fun getTripsByUserId(userId: Int): Flow<List<Trip>>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(trip: Trip)

    @Update
    suspend fun update(trip: Trip)
    @Delete
    suspend fun delete(trip: Trip)

    @Query("SELECT * from trips WHERE startTime >= :startTime AND endTime <= :endTime")
    fun getTripInRange(startTime: Long, endTime: Long): Flow<List<Trip>>
}