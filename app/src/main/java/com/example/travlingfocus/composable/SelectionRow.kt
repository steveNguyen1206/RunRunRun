package com.example.travlingfocus.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TimeSelectRow(
    modifier: Modifier = Modifier,
    timeList: List<String>,
    onTimeChose: (String) -> Unit,
    selectedIndex: Int = 0
){
    ScrollableTabRow(selectedTabIndex = 0, modifier = modifier) {
        timeList.forEachIndexed { index, time ->
            Chip(
                time = time,
                selected = index == selectedIndex,
                onTimeChose = onTimeChose
            )
        }
    }
}

@Composable
fun Chip (
    time: String,
    selected: Boolean,
    onTimeChose: (String) -> Unit
){
    Surface(
        modifier = Modifier.padding(end = 8.dp),
        shape = RoundedCornerShape(20.dp),
        color = if(selected) Color.White else Color.Transparent
    ) {
        Row(modifier = Modifier
            .clickable(
                onClick = {
                    onTimeChose(time)
                }
            ),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = time,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}