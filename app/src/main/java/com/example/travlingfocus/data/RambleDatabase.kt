package com.example.travlingfocus.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Database(entities = [Trip::class, User::class], version = 2, exportSchema = false)
abstract class RambleDatabase : RoomDatabase(){

    abstract fun tripDao(): TripDao

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var Instance: RambleDatabase? = null


        fun getDatabase(context: Context): RambleDatabase {
            Log.d("get database", "get database")
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, RambleDatabase::class.java, "ramble_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}