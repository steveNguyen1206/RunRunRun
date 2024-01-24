package com.example.travlingfocus.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travlingfocus.R

@Composable
fun SelectRow(
    modifier: Modifier = Modifier,
    list: List<String>,
    onChose: (String) -> Unit,
    selectedIndex: Int,
    fontSize: Int = 20,
    unSelectedBackground: Color = Color.Transparent,
){
    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        modifier = modifier,
        containerColor = Color.Transparent,
        indicator = { tabpostions ->
            Image(
                painterResource(id = R.drawable.ic_indicator_tab),
                contentDescription = null,
                modifier.tabIndicatorOffset(tabpostions[selectedIndex])
                    .size(20.dp)
                    .offset(y = -40.dp)
            )
        },
        divider = {}
    ) {
        list.forEachIndexed { index, time ->
            val selected = selectedIndex == index

            Tab(
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .height(60.dp)
                    .clip(RoundedCornerShape(16.dp)),
                selected = selected,
                onClick = {
                    onChose(time)
                },
            ){
                Surface(
                    modifier = Modifier
                        .wrapContentSize(Alignment.Center)
                        .height(48.dp)
                        .padding(horizontal = 8.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    color = if(selected) Color.White else unSelectedBackground
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center),
                    ){
                        Text(
                            text = time,
                            fontSize = fontSize.sp,
                            style =  MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(8.dp),
                            fontWeight = if(selected) FontWeight.Bold else FontWeight.Normal,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SelectTagRow(
    modifier: Modifier = Modifier,
    list: List<String>,
    onChose: (String) -> Unit,
    selectedIndex: Int,
    fontSize: Int = 20,
){
    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        modifier = modifier,
        containerColor = Color.Transparent,
        indicator = {},
        divider = {}
    ) {
        list.forEachIndexed { index, time ->
            val selected = selectedIndex == index

            Tab(
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .clip(RoundedCornerShape(16.dp)),
                selected = selected,
                onClick = {
                    onChose(time)
                },
            ){
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            width = 2.dp,
                            color = if(selected) Color.White else Color.Transparent,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    color = Color.Transparent
                ) {
                        Text(
                            text = time,
                            fontSize = fontSize.sp,
                            style =  MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.Bold
                        )
                }
            }
        }
    }
}

