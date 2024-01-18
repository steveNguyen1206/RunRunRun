package com.example.travlingfocus.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                    .padding(top = 8.dp),
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
) {
    Box(
        
    ) {
        Row(
            modifier.padding(16.dp)
        ) {
            Image(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable(onClick = onTimerClick),
                painter = painterResource(id = R.drawable.ic_timmer),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(20.dp))
            Image(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable(onClick = onStopWatchClick),
                painter = painterResource(id = R.drawable.ic_stopwatch),
                contentDescription = null,
            )
        }
    }

    Image(
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 8.dp),
        painter = painterResource(id = R.drawable.ic_music),
        contentDescription = null
    )
}