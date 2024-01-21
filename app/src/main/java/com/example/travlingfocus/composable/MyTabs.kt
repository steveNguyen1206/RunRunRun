package com.example.travlingfocus.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.travlingfocus.R

@Composable
fun MyTabBar(
    modifier: Modifier = Modifier,
//    onMenuClicked: () -> Unit,
    onTimerClick : () -> Unit = {},
    onStopWatchClick : () -> Unit = {},
    children: @Composable (Modifier) -> Unit
) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Separate Row as the children shouldn't have the padding
        Row(Modifier.padding(16.dp)) {
            Image(
                modifier = Modifier
                    .padding(4.dp),
//                    .clickable(onClick = onMenuClicked),
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = null,
            )
//            Spacer(Modifier.width(8.dp))
//            Image(
//                painter = painterResource(id = R.drawable.ic_crane_logo),
//                contentDescription = null
//            )
        }
        children(
            Modifier
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun MyTab(
    modifier: Modifier = Modifier,
    onTimerClick : () -> Unit,
    onStopWatchClick : () -> Unit,
    onMusicClick : () -> Unit,
) {
    var isMusicPlaying by remember {  mutableStateOf(false) }

    Box(
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(24.dp))
            .border(
                BorderStroke(1.dp, Color(0xFFA0A0A0)),
                RoundedCornerShape(24.dp)
            )
//           Background is gradient color
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFD8E2DC),
                        Color(0x00D9D9D9),
                    )
                )
            )
    ) {
        Row(
            modifier
                .padding(8.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .padding(4.dp)
                    .clickable(onClick = onTimerClick),
                painter = painterResource(id = R.drawable.ic_timmer),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(40.dp))
            Image(
                modifier = Modifier
                    .padding(4.dp)
                    .clickable(onClick = onStopWatchClick),
                painter = painterResource(id = R.drawable.ic_stopwatch),
                contentDescription = null,
            )
        }
    }

//   I want to fill with only one width for multiple images
    Image(
        modifier = Modifier
            .padding(16.dp)
            .padding(4.dp)
            .clickable(onClick = {
                isMusicPlaying = !isMusicPlaying
                onMusicClick()
            }),
        painter = painterResource(id = if(isMusicPlaying == true) R.drawable.ic_music else R.drawable.ic_timmer),
        contentDescription = null,
        contentScale = ContentScale.Fit,
    )
}

@Composable
fun MyTabReward() {

}