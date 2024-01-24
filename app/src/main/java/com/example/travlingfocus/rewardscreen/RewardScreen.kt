package com.example.travlingfocus.rewardscreen

import android.util.Log
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
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travlingfocus.AppViewModelProvider
import com.example.travlingfocus.R
import com.example.travlingfocus.composable.DisplaySpinner
import com.example.travlingfocus.composable.MyTabBar
import com.example.travlingfocus.composable.MyTabReward
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch


//@Preview(showBackground = true, backgroundColor = 0xFF81A684)
//@Composable
//fun RewardScreenPreview() {
//    RewardScreen(
//        viewModel = RewardViewModel(),
//    )
//}

@Composable
fun RewardScreen(
    modifier: Modifier = Modifier,
    viewModel: RewardViewModel =  viewModel(factory = AppViewModelProvider.Factory),
    navigateUp: () -> Unit = {},
    canNavigateBack: Boolean = true,
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.statusBarsPadding(),
        backgroundColor = MaterialTheme.colorScheme.primary,
        topBar = {
            MyTabBar(
                modifier = modifier
                    .fillMaxWidth(),
                navigateUp = navigateUp,
                canNavigateBack = canNavigateBack,
                children = { MyTabReward(modifier = it, hours = viewModel.totalDuration)}
            )
        },
    ){
        RewardContent(
            modifier = modifier.padding(it),
            viewModel = viewModel,
        )
    }
}

@Composable
fun RewardContent(
    modifier: Modifier = Modifier,
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
    viewModel: RewardViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp),
    ) {
        DisplaySpinner(
            modifier = modifier,
            options = viewModel.options,
            onOptionSelected =  {
                coroutineScope.launch {
                    viewModel.updateOption(it)
                } },
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
                text = viewModel.timeOption,
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
    viewModel: RewardViewModel = viewModel(factory = AppViewModelProvider.Factory),
){
    val places = viewModel.destinations
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
//                            viewModel.setSelectedPlaceIndex(index)
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
    place: Pair<Int, String>,
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
            Log.d("RewardScreen", "RewardGridItem: ${place}")
            Log.d("RewardScreen", "RewardGridItem: ${painterResource(id =place.first)}")

            Image(
                painter = painterResource(id = place.first),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
            )

            Text(
                //            text = if (place.name != "Next Place") place.name else "",
                text = place.second,
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
                colorFilter = ColorFilter.tint(pinColor),
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
//        MultipleBarChartPreview()
        MultipleBarChart(
            modifier = Modifier
                .fillMaxWidth(),
            barChartListSize = viewModel.barChartListSize,
            eachGroupBarSize = viewModel.eachGroupBarSize,
            yStepSize = viewModel.yStepSize,
            groupBarData = viewModel.groupBarData,
            itemBarColors = viewModel.itemBarColors,
            itemBarNames = viewModel.itemBarNames,
            groupBarNames = viewModel.groupBarNames,
        )
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
                viewModel.openSheet()
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

        if(viewModel.sheetState) {
            RewardBottomSheet(
                viewModel = viewModel,
                onDismissRequest = {
                    viewModel.closeSheet()
                }
            )
        }
    }
}

//@Preview(showBackground = true, backgroundColor = 0xFF81A684)
//@Composable
//fun RewardBottomSheetPreview(){
////    val viewModel = hiltViewModel<RewardViewModel>()
//    val viewModel = RewardViewModel()
//    IconRewardBottomSheetButton(viewModel = viewModel)
//}