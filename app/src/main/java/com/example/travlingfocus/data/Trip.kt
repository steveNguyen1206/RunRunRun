package com.example.travlingfocus.data


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time

/**
 * Entity data class represents a single row in the database.
 */
@Entity(tableName = "trips")
data class Trip(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int = 0,
    val destination: String,  // name
    val desResId: Int,        // id to draw Image
    val completed: Boolean,
    val startTime: Long,
    val endTime: Long,
    val duration: Float,
    val tag: String,
)
