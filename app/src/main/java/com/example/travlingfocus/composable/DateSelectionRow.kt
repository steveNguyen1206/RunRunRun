package com.example.travlingfocus.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
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
import com.example.travlingfocus.ui.theme.GrayDivider
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun DateSelectionRow (
    modifier: Modifier = Modifier,
    list: List<Date>,
    onChose: (Date) -> Unit,
    selectedIndex: Int,
    fontSize: Int = 20,
    unSelectedBackground: Color = Color.Transparent,
) {
    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        modifier = modifier,
        containerColor = Color.Transparent,
        indicator = {},
        divider = {
            Divider(color = GrayDivider, thickness = 2.dp)
        },
        edgePadding = 0.dp,
    )
    {
        list.forEachIndexed { index, time ->
            val selected = selectedIndex == index
            Tab(
                selected = selected,
                onClick = {
                    onChose(time)
                },
            ) {
                Surface(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    color = if (selected) MaterialTheme.colorScheme.secondary else unSelectedBackground
                ) {
                    Column(
                        modifier = Modifier
                            .padding(12.dp, 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = SimpleDateFormat("EEE").format(time).first().toString(),
                            fontSize = (fontSize - 2).sp,
                            style = MaterialTheme.typography.bodySmall,
                            color = if (selected) MaterialTheme.colorScheme.onPrimary else Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp),
                            fontWeight = FontWeight.Normal,
                        )

                        Text(
                            text = SimpleDateFormat("dd").format(time),
                            fontSize = fontSize.sp,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (selected) MaterialTheme.colorScheme.onPrimary else Color.Black,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }
        }
    }

}