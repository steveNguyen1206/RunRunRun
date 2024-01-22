package com.example.travlingfocus.composable

import android.graphics.drawable.Animatable
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.travlingfocus.home.MainViewModel

@Composable
fun GifImage(
    modifier: Modifier = Modifier,
    data: Int,
//    mysize: Float,
    mayBeginGifAnimation: Boolean,
    onGifClick: () -> Unit,

) {
    var animatable by remember { mutableStateOf<Animatable?>(null) }

    animatable?.apply {
        if (!mayBeginGifAnimation) {
            stop()
        } else {
            if (!isRunning) start()
        }
    }


    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(data = data).apply(block = {
            }).build(), imageLoader = imageLoader,   onSuccess = { state ->
                (state.result.drawable as? Animatable)?.let {
                    animatable = it
                }
            },
        ),
        contentDescription = null,
        modifier = modifier.clickable { onGifClick() },

    )
}