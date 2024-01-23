package com.example.travlingfocus.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.travlingfocus.R

@Composable
fun MyTabBar(
    modifier: Modifier = Modifier,
    onMenuClicked: () -> Unit,
    onTimerClick : () -> Unit = {},
    onStopWatchClick : () -> Unit = {},
    children: @Composable (Modifier) -> Unit,
    canNavigateBack: Boolean = false,
    navigateUp: () -> Unit = {},
) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Separate Row as the children shouldn't have the padding
        Row(Modifier.padding(16.dp)) {
            if(canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
            Image(
                modifier = Modifier
                    .padding(4.dp)
                    .clickable(onClick = onMenuClicked),
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = null,
            )
        }

    }
    children(
        Modifier
            .align(Alignment.CenterVertically)
    )
}

@Composable
fun MyTab(
    modifier: Modifier = Modifier,
    onTimerClick : () -> Unit,
    onStopWatchClick : () -> Unit,
) {
    Box(
        modifier = modifier
            .padding(8.dp)
//            .clip(RoundedCornerShape(8.dp))
            .border(
                BorderStroke(2.dp, Color.Black),
                RoundedCornerShape(24.dp)
            )
    ) {
        Row(
            modifier.padding(8.dp).padding(horizontal = 16.dp),
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

    Image(
        modifier = Modifier
            .padding(16.dp)
            .padding(4.dp),
        painter = painterResource(id = R.drawable.ic_music),
        contentDescription = null
    )
}