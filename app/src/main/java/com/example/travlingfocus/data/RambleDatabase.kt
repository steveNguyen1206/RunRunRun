package com.example.travlingfocus.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Trip::class], version = 1, exportSchema = false)
abstract class RambleDatabase : RoomDatabase(){

    abstract fun tripDao(): TripDao

    companion object {
        @Volatile
        private var Instance: RambleDatabase? = null

        fun getDatabase(context: Context): RambleDatabase {
            Log.d("get database", "get database")
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, RambleDatabase::class.java, "trip_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}