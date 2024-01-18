package com.example.travlingfocus

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.example.travlingfocus.util.UnsplashSizingInterceptor
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TravlingFocusApplication : Application(), ImageLoaderFactory {
    /**
     * Create the singleton [ImageLoader].
     * This is used by [AsyncImage] to load images in the app.
     */
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .components {
                add(UnsplashSizingInterceptor)
            }
            .build()
    }
}