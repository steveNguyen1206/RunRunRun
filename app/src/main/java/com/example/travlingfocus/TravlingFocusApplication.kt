package com.example.travlingfocus

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.example.travlingfocus.data.AppContainer
import com.example.travlingfocus.data.AppDataContainer
import com.example.travlingfocus.util.UnsplashSizingInterceptor
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TravlingFocusApplication : Application(), ImageLoaderFactory {
    /**
     * Create the singleton [ImageLoader].
     * This is used by [AsyncImage] to load images in the app.
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .components {
                add(UnsplashSizingInterceptor)
            }
            .build()
    }

    // Path: app/src/main/java/com/example/travlingfocus/TravlingFocusApplication.kt
}