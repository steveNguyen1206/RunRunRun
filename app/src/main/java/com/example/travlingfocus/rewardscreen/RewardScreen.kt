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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.travlingfocus.R
import com.example.travlingfocus.composable.DisplaySpinner
import com.example.travlingfocus.composable.MyTabBar
import com.example.travlingfocus.composable.MyTabReward
import com.example.travlingfocus.home.MainViewModel
import com.example.travlingfocus.ui.theme.GreenGray

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
                MyTabReward(modifier = it, hours = 96.7)
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
        RewardSpinner(
            modifier,
            widthSize,
            viewModel
        )

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
