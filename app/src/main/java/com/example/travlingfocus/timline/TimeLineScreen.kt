package com.example.travlingfocus.timline

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material.Surface
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travlingfocus.composable.MyTabBar
import com.example.travlingfocus.composable.TitleTab
import com.example.travlingfocus.home.HomeTabBar
import com.example.travlingfocus.home.TimerType
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travlingfocus.AppViewModelProvider
import com.example.travlingfocus.R
import com.example.travlingfocus.composable.DateSelectionRow
import com.example.travlingfocus.composable.totalTime
import com.example.travlingfocus.data.Trip
import com.example.travlingfocus.home.TripDetails
import com.example.travlingfocus.ui.theme.GrayTripContainer
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.time.Duration.Companion.hours

@Composable
fun TimeLineScreen(
    navigateUp: () -> Unit,
    canNavigateBack: Boolean,
    viewModel: TimeLineViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val timelineUiState by viewModel.timelineUiState.collectAsState()
    Surface (
        modifier = Modifier.windowInsetsPadding(
            WindowInsets.navigationBars.only(WindowInsetsSides.Start + WindowInsetsSides.End)
        ),
        color = MaterialTheme.colorScheme.primary
    ){
        Scaffold(
            modifier = Modifier.statusBarsPadding(),
            topBar = {
                MyTabBar(
                    navigateUp = navigateUp,
                    canNavigateBack = canNavigateBack,
                    children = {
                        TitleTab(title = "Timeline")
                    }
                )
            },
            containerColor = Color.Transparent,
            content = {
                TimlineContent(
                    timelineUiState = timelineUiState,
                    modifier = Modifier.padding(it)
                )
            }
        )
    }

}

@Composable
fun TimlineContent(
    timelineUiState: TimelineUiState,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(108.dp)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row(
                modifier = Modifier.wrapContentSize()
            ){
                Text(
                    text = "24",
                    fontSize = 48.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(end = 8.dp)
                )
                Column (
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Wed",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Jan 2024",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier
                    )
                }
            }

            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor =  MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.1f),
                    contentColor = MaterialTheme.colorScheme.onTertiary
                ),
            ) {
                Text(
                    text= "Today",
                    color=MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }

        }

        val mockDate = listOf(
            Pair("Mon", "24"),
            Pair("Tue", "25"),
            Pair("Wed", "26"),
            Pair("Thu", "27"),
            Pair("Fri", "28"),
            Pair("Sat", "29"),
            Pair("Sun", "30")
        )

        var selectedDateIndex by remember {
            mutableStateOf(mockDate.indexOf(Pair("Wed", "26")))
        }

        DateSelectionRow(
            modifier = Modifier
                .fillMaxWidth(),
            onChose = { selectedDateIndex = mockDate.indexOf(it) },
            selectedIndex = selectedDateIndex,
            list = mockDate
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Row (
                modifier = Modifier
                    .width(80.dp),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Time",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onPrimary,
                    )
            }
            Text(
                text = "Trip",
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(start = 16.dp)
            )
            
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    modifier = Modifier
                        .clickable {  },
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_sort_down),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }


        LazyColumn(content = {
            items(timelineUiState.tripLists.size) { index ->
                Trip(
                    trip = timelineUiState.tripLists[index],
                    onClick = { /*TODO*/ }
                )
            }
        })
    }
}

@Composable
fun Trip(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    trip: Trip
){
    Row(
        modifier = modifier
            .height(IntrinsicSize.Max)
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(start = 16.dp, end = 16.dp),
    ){
        Column(
            modifier = modifier
                .width(80.dp),
            verticalArrangement = Arrangement.Top
        ) {
            val startTime = SimpleDateFormat("HH:mm").format(Date(trip.startTime))
            val endTime = SimpleDateFormat("HH:mm").format(Date(trip.endTime))
            Text(
                text = startTime,
                fontSize = 22.sp,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )
            Text(
                text = endTime,
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Normal,
            )
        }

        Divider(
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = modifier
                .fillMaxHeight()
                .width(2.dp)
                .border(2.dp, MaterialTheme.colorScheme.onPrimary)
                .padding(end = 16.dp)
        )
        Row(
            modifier = Modifier.padding(start= 16.dp, bottom = 16.dp)
        )
        {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (trip.completed) MaterialTheme.colorScheme.onTertiary else GrayTripContainer),
                verticalAlignment = Alignment.Top,
            ){
                val textColor = if (trip.completed) Color.White else Color.Black.copy(alpha = 0.2f)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                )
                {
                    Text(text = trip.destination,
                        fontSize = 20.sp,
                        color = textColor,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                    Row(
                        modifier = Modifier
                            .padding(bottom = 8.dp),
                    )
                    {
                        Icon(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(24.dp, 24.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_time),
                            contentDescription = null,
                            tint = textColor
                        )
                        Text(
                            text = (trip.duration / 60000f).toInt().toString() + " m",
                            fontSize = 18.sp,
                            color = textColor,
                            fontWeight = FontWeight.Normal,
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(bottom = 8.dp),
                    )
                    {
                        Icon(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(24.dp, 24.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_tag),
                            contentDescription = null,
                            tint = textColor
                        )
                        Text(
                            text = '#' + trip.tag,
                            fontSize = 18.sp,
                            color = textColor,
                            fontWeight = FontWeight.Normal,
                        )
                    }

                }
                IconButton(onClick = {  }) {
                    Icon(
                        modifier = Modifier
                            .clickable {  },
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_more),
                        contentDescription = null,
                        tint = textColor
                    )
                }
            }
        }

    }
}
