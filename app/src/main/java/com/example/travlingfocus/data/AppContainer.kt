package com.example.travlingfocus.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val tripsRepository: TripsRepository
    val userRepository: UserRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val tripsRepository: TripsRepository by lazy {
        OfflineTripsRepository(RambleDatabase.getDatabase(context).tripDao())
    }

    override val userRepository: UserRepository by lazy {
        OfflineUserRepository(RambleDatabase.getDatabase(context).userDao())
    }
}
