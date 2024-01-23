package com.example.travlingfocus

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.travlingfocus.home.TripCreateViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
//        initializer {
//            ItemEditViewModel(
//                this.createSavedStateHandle(),
//                inventoryApplication().container.itemsRepository
//            )
//        }
//        // Initializer for ItemEntryViewModel
//        initializer {
//            ItemEntryViewModel(inventoryApplication().container.itemsRepository)
//        }
//
//        // Initializer for ItemDetailsViewModel
//        initializer {
//            ItemDetailsViewModel(
//                this.createSavedStateHandle(),
//                inventoryApplication().container.itemsRepository
//            )
//        }
//
//        // Initializer for HomeViewModel
//        initializer {
//            HomeViewModel(inventoryApplication().container.itemsRepository)
//        }

        initializer {
            TripCreateViewModel(
                travlingfocusApplication().container.tripsRepository
            )

        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.travlingfocusApplication(): TravlingFocusApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TravlingFocusApplication)