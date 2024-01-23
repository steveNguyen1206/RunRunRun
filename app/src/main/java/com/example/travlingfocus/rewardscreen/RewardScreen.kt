package com.example.travlingfocus.rewardscreen

import android.graphics.drawable.PaintDrawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.minimumInteractiveComponentSize
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.travlingfocus.R
import com.example.travlingfocus.composable.DisplaySpinner
import com.example.travlingfocus.composable.MyTabBar
import com.example.travlingfocus.composable.MyTabReward
import com.example.travlingfocus.home.MainViewModel
import com.example.travlingfocus.ui.theme.GreenGray
import dagger.hilt.android.lifecycle.HiltViewModel

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

@Composable
fun RewardBoard(){
    Image(
        painterResource(id = R.drawable.ic_board),
        contentDescription = null,
        Modifier
            .padding(horizontal = 32.dp, vertical = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        contentScale = ContentScale.FillWidth,
    )
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