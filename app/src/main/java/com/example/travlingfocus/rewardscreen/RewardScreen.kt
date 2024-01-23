package com.example.travlingfocus.rewardscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.travlingfocus.R
import com.example.travlingfocus.composable.DisplaySpinner
import com.example.travlingfocus.composable.MyTabBar
import com.example.travlingfocus.composable.MyTabReward

enum class TimeType{
    Day,  Month, Year, All, Week
}

@Preview(showBackground = true, backgroundColor = 0xFF81A684)
@Composable
fun RewardScreenPreview() {
    RewardScreen(
        widthSize = WindowWidthSizeClass.Medium,
        viewModel = RewardViewModel(),
    )
}

@Composable
fun RewardScreen(
    widthSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    viewModel: RewardViewModel
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.statusBarsPadding(),
        backgroundColor = MaterialTheme.colorScheme.primary,
        topBar = {
            MyTabBar(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                MyTabReward(modifier = it, hours = viewModel.getTotalHours())
            }
        },
    ){

        RewardContent(
            modifier = modifier.padding(it),
            widthSize = widthSize,
            viewModel = viewModel,
        )
    }
}

@Composable
fun RewardContent(
    modifier: Modifier = Modifier,
    widthSize: WindowWidthSizeClass,
    viewModel: RewardViewModel
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        RewardSpinner(
            modifier,
            widthSize,
            viewModel
        )

        RewardBoard()

        IconRewardBottomSheetButton(
            modifier = modifier.weight(1f),
            viewModel = viewModel)
//        do more here
    }
}

@Composable
fun RewardSpinner(
    modifier: Modifier = Modifier,
    widthSize: WindowWidthSizeClass,
    viewModel: RewardViewModel
) {
    val options = remember(key1 = viewModel.timeOptions) {
        viewModel.timeOptions
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp),
    ) {
        DisplaySpinner(
            modifier = modifier,
            options = options,
            onOptionSelected = { viewModel.chooseOption(it) },
            parent = { modifier, onClick ->
                RewardSpinnerButton(
                    modifier = modifier,
                    onClick = onClick,
                    viewModel = viewModel
                )
            }
        )
    }
}

@Composable
fun RewardSpinnerButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    viewModel: RewardViewModel
){
//        Day/Month/Year
    IconButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onPrimary,
                RoundedCornerShape(48.dp),
            )
            .clip(RoundedCornerShape(48.dp)) )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = viewModel.getTimeString(),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.width(10.dp))

            //                Draw down buttom
            Image(
                painter = painterResource(id = R.drawable.ic_toggle_down),
                contentDescription = null,
                contentScale = ContentScale.Fit,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF81A684)
@Composable
fun RewardBoard(
    modifier: Modifier = Modifier,
    viewModel: RewardViewModel = RewardViewModel()
){
    val places = viewModel.getPlaces()
    Box(
//        contentAlignment = Alignment.BottomEnd,
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {
            Image(
                painterResource(id = R.drawable.ic_board),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
            )

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                //            columns = GridCells.Adaptive(120.dp),
                columns = GridCells.Fixed(3),
                content = {
                    items(places.size) { index ->
                        val place = places[index]
                        RewardGridItem(
                            modifier = Modifier
                                .padding(8.dp)
                                .background(MaterialTheme.colorScheme.surface)
                                .wrapContentHeight(Alignment.CenterVertically),
                            //                            .clip(RoundedCornerShape(20.dp))
                            //                            .border(
                            //                                width =  2.dp,
                            //                                color = if (viewModel.selectedPlaceIndex == index) GreenGray else androidx.compose.ui.graphics.Color.Transparent,
                            //                                shape = RoundedCornerShape(20.dp)
                            //                            )
                            place = place,
                            pinColor = viewModel.getPinColor(index),
                        ) {
                            viewModel.setSelectedPlaceIndex(index)
                        }
                    }
                }
            )

        }

        Image(
            painter = painterResource(id = R.drawable.bottom_img),
            contentDescription = null,
            modifier = Modifier
                .width(100.dp)
                .align(Alignment.BottomEnd),
            contentScale = ContentScale.FillWidth,
        )
    }

}

@Composable
fun RewardGridItem(
    modifier: Modifier = Modifier,
    place: Place,
    pinColor: Color,
    onClick: () -> Unit)
{
    Box(
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier = modifier
                .clickable(onClick = onClick)
                .padding(8.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End,
        ) {
            Image(
                painter = painterResource(id = place.imageId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(),
                //                .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.FillWidth,
            )

            Text(
                //            text = if (place.name != "Next Place") place.name else "",
                text = place.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(vertical = 4.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                //            textAlign = Alignment.Center,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.TopCenter,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_push_pin),
                contentDescription = null,
                colorFilter = ColorFilter.tint(pinColor, blendMode = BlendMode.ColorBurn),
            )
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RewardBottomSheet(
    modifier: Modifier = Modifier,
    viewModel: RewardViewModel,
    onDismissRequest: () -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = {
//            Log.d("RewardBottomSheet", "RewardBottomSheet: Dismissed")
            onDismissRequest()
//            Log.d("RewardBottomSheet", "RewardBottomSheet: ${viewModel.getSheetState().value}")
        },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        RewardBottomSheetContent(
            modifier = Modifier
                .fillMaxSize()
//                .padding(16.dp)
                .background(Color(0xFFE6B9BD)),
            viewModel = viewModel,
        )
    }
}

@Composable
fun RewardBottomSheetContent(
    modifier: Modifier = Modifier,
    viewModel: RewardViewModel
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ){
        Text(
            text = "Statistics",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp),)

        Spacer(modifier = Modifier.height(16.dp))

//        Example
        MultipleBarChartPreview()
//        MultipleBarChart(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp),
//            viewModel = viewModel,
//        )
    }
}

@Composable
fun IconRewardBottomSheetButton(
    modifier: Modifier = Modifier,
    viewModel: RewardViewModel
){
    Column(
        modifier = modifier.padding(bottom =  32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconButton(
            onClick = {
                viewModel.openBottomSheet()
            },
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(48.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    RoundedCornerShape(48.dp),
                )
                .background(MaterialTheme.colorScheme.secondary)
        ) {
            Text(
                text = "Statistics",
                style = MaterialTheme.typography.bodyLarge,
                modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                color = MaterialTheme.colorScheme.onSecondary,
            )
        }

        if(viewModel.getSheetState().value) {
            RewardBottomSheet(
                viewModel = viewModel,
                onDismissRequest = {
                    viewModel.closeBottomSheet()
                }
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF81A684)
@Composable
fun RewardBottomSheetPreview(){
//    val viewModel = hiltViewModel<RewardViewModel>()
    val viewModel = RewardViewModel()
    IconRewardBottomSheetButton(viewModel = viewModel)
}