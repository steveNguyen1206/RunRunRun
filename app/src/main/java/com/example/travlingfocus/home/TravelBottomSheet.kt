package com.example.travlingfocus.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travlingfocus.R
import com.example.travlingfocus.composable.GifImage
import com.example.travlingfocus.composable.PrimButton
import com.example.travlingfocus.composable.SelectRow
import com.example.travlingfocus.ui.theme.GrayContainer
import com.example.travlingfocus.ui.theme.GreenLight
import kotlin.math.roundToInt


@Composable
fun TravelBottomSheet (
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    travelClick: () -> Unit ,
)
{
    val scaffoldState = rememberScaffoldState()

    Scaffold (
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        bottomBar = {
            TravelBottomBar(
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth(),
                viewModel = viewModel,
                travelClick = travelClick
            )
        }
    ){
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
        )
        {

            val selectedTag by viewModel.selectedTag.observeAsState(ActivityTag.frend)

            var selectedTimeIndex by remember {
                mutableStateOf(0)
            }

            var selectedSouvenir by remember {
                mutableStateOf(Souvenir.Sourvenir1)
            }

            Text(
                text = "Focus Time",
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )

            SelectRow(
                modifier = Modifier
                    .fillMaxWidth(),
                list = viewModel.timeOptions,
                onChose = {
                    viewModel.updateTimerValue(it.toFloat() * 60000)
                    selectedTimeIndex = viewModel.timeOptions.indexOf(it)
                },
                selectedIndex = selectedTimeIndex,
                fontSize = 18,
                unSelectedBackground = GrayContainer,
            )

            Text(
                text = "Tag",
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )

            SelectRow(
                modifier = Modifier
                    .fillMaxWidth(),
                list = ActivityTag.values().map { '#' + it.name },
                onChose = {
                    viewModel.updateTag(ActivityTag.valueOf(it.replace("#", "")))
                },
                selectedIndex = selectedTag.ordinal,
                fontSize = 16,
                unSelectedBackground = GrayContainer,
            )

            Text(
                text = "Souvenir",
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )

            LazyHorizontalGrid(
                rows = GridCells.Adaptive(140.dp),
                content = {
                    items(Souvenir.values().size) { index ->
                        val souvenir = Souvenir.values()[index]
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(120.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .border(
                                    width = 2.dp,
                                    color = if (selectedSouvenir.ordinal == souvenir.ordinal) GreenLight else Color.Transparent,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .background(MaterialTheme.colorScheme.tertiary),
                            contentAlignment = Alignment.Center
                        ) {

                            GifImage(
                                data = souvenir.imageId,
                                mayBeginGifAnimation = false,
                                onGifClick = {
                                    selectedSouvenir = souvenir
                                    viewModel.updateSelectedSouvenir(souvenir)
                                },
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun TravelBottomBar (
    modifier: Modifier,
    viewModel: MainViewModel,
    travelClick: () -> Unit ,
){
    val selectedSouvenir by viewModel.selectedSouvenir.observeAsState(Souvenir.Sourvenir1)
    val selectedTime by viewModel.timerValue.observeAsState(60000f)
    val selectedTag by viewModel.selectedTag.observeAsState(ActivityTag.frend)
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.primary,
    ){
        Row (
            modifier = modifier.padding(horizontal = 36.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {

            GifImage(
                data = selectedSouvenir.imageId,
                mayBeginGifAnimation = false,
                onGifClick = {
                },
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.width(24.dp))
            Column(
                modifier = Modifier
                    .height(80.dp)
                    .weight(1f),
            )
            {
                Row(
                    modifier = Modifier.height(50.dp),
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_time),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = (selectedTime / 60000f).roundToInt().toString(),
                        fontSize = 22.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Text(
                    text = '#' + selectedTag.name,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            PrimButton(
                onButtonClick = {travelClick()},
                colors = ButtonDefaults.buttonColors(
                    MaterialTheme.colorScheme.secondary),
                text = "Travel"
            )
        }
    }

}