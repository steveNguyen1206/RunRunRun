package com.example.travlingfocus.rewardscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.travlingfocus.composable.MyTabBar
import com.example.travlingfocus.composable.MyTabReward
import com.example.travlingfocus.home.MainViewModel

enum class TimeType{
    Day,  Month, Year
}

//@Preview(showBackground = true, backgroundColor = , showSystemUi =
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                MyTabReward(modifier = it, hours = 96.7, )
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
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    RoundedCornerShape(48.dp),
                )
                .clip(RoundedCornerShape(48.dp))
            ,
            contentAlignment = Alignment.Center
        ) {
            Row {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    text = viewModel.getTimeString(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier =  Modifier.width(40.dp))

//                Draw down buttom
//                Image(painter = , contentDescription = )
            }
        }
    }
}