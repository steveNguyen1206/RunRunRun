package com.example.travlingfocus.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val tripsRepository: TripsRepository
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
}
