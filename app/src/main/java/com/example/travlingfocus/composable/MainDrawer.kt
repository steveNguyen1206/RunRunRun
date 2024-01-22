package com.example.travlingfocus.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travlingfocus.R
import com.example.travlingfocus.home.Routes

private val screens = listOf (
    Routes.PassPort.route,
    Routes.TimeLine.route,
    Routes.Ranking.route,
    Routes.Friend.route,
    Routes.Setting.route,

)

private val screeenIcons = mapOf(
    Routes.PassPort.route to R.drawable.ic_passport,
    Routes.TimeLine.route to R.drawable.ic_time_line,
    Routes.Ranking.route to R.drawable.ic_ranking,
    Routes.Friend.route to R.drawable.ic_friends,
    Routes.Setting.route to R.drawable.ic_setting,

)

@Composable
fun MainDrawer(
    modifier: Modifier = Modifier,
    tabClick: () -> Unit,
) {
    Scaffold (
       containerColor = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            DrawerTopBar(
                modifier = Modifier.height(120.dp).fillMaxWidth()
            )
        }
    ) {
        DrawerContent(
            modifier = modifier
                .padding(it).fillMaxWidth(),
            tabClick = tabClick
        )
    }

}

@Composable
fun DrawerContent(
    modifier: Modifier = Modifier,
    tabClick: () -> Unit
)
{
    Column (
        modifier = modifier
            .padding(24.dp)
    )
    {
        screens.forEach { screen ->
            DrawerTab(
                text = screen.replaceFirstChar { it.uppercase() },
                tabClick = tabClick,
                icon = screeenIcons[screen] ?: error("No icon for $screen")
            )
        }
    }
}

@Composable
fun DrawerTab (
    text: String,
    icon: Int,
    tabClick: () -> Unit,
)
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { tabClick() },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .padding(8.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = text,
            modifier = Modifier
                .padding(16.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
fun DrawerTopBar (
    modifier: Modifier = Modifier,
){
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Text(
            text = "RAMBLE",
            letterSpacing = 4.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(16.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "version 1.0.0",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimary,

        )
    }

}