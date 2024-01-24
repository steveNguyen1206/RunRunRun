package com.example.travlingfocus.rewardscreen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.GroupBarChart
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.barchart.models.BarPlotData
import co.yml.charts.ui.barchart.models.BarStyle
import co.yml.charts.ui.barchart.models.GroupBar
import co.yml.charts.ui.barchart.models.GroupBarChartData
import kotlin.math.max

@Preview(showBackground = true)
@Composable
fun MultipleBarChartPreview(){
    val groupBarData = listOf(
        listOf(10f, 20f, 30f, 40f, 50f),
        listOf(60f, 50f, 40f, 30f, 20f),
        listOf(30f, 40f, 50f, 60f, 70f),
        listOf(40f, 50f, 60f, 70f, 80f),
        listOf(50f, 60f, 70f, 80f, 90f),
//        listOf(60f, 70f, 80f, 90f, 100f),
//        listOf(70f, 80f, 90f, 100f, 110f),
//        listOf(80f, 90f, 100f, 110f, 120f),
//        listOf(90f, 100f, 110f, 120f, 130f),
//        listOf(100f, 110f, 120f, 130f, 140f),
//        listOf(110f, 120f, 130f, 140f, 150f),
//        listOf(120f, 130f, 140f, 150f, 160f),
//        listOf(130f, 140f, 150f, 160f, 170f),
//        listOf(140f, 150f, 160f, 170f, 180f),
//        listOf(150f, 160f, 170f, 180f, 190f),
//        listOf(160f, 170f, 180f, 190f, 200f),
//        listOf(170f, 180f, 190f, 200f, 210f),
//        listOf(180f, 190f, 200f, 210f, 220f),
//        listOf(190f, 200f, 210f, 220f, 230f),
//        listOf(200f, 210f, 220f, 230f, 240f),
//        listOf(210f, 220f, 230f, 240f, 250f),
//        listOf(220f, 230f, 240f, 250f, 260f),
    )

    val barChartListSize = groupBarData.size
    val eachGroupBarSize = groupBarData[0].size
    val yStepSize = 5
    val itemBarColors = listOf<Color>(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Magenta)
    val itemBarNames = listOf<String>("Study", "Work", "Relax", "Sport", "Sleep")

    MultipleBarChart(
        barChartListSize = barChartListSize,
        eachGroupBarSize = eachGroupBarSize,
        yStepSize = yStepSize,
        groupBarData = groupBarData,
        itemBarColors = itemBarColors,
        itemBarNames = itemBarNames,
        groupBarNames = listOf("Day 1", "Day 2", "Day 3", "Day 4", "Day 5"),
    )
}

@Composable
fun MultipleBarChart(
    modifier: Modifier = Modifier,
    barChartListSize: Int,
    eachGroupBarSize: Int,
    yStepSize: Int = 5,
    groupBarData: List<List<Float>>,
    itemBarColors: List<Color>,
    itemBarNames: List<String>,
    groupBarNames: List<String>, // Name for each Group Bar Chart
    ){

    var max_yValue = groupBarData.flatten().maxOrNull() ?: 0f
    max_yValue = max(max_yValue, 10f)
    max_yValue = (max_yValue / 10).toInt() * 10f + (if (max_yValue % 10 > 0) 10f else 0f)  // round up to the nearest 10

    val groupBarPlotData = BarPlotData(
        groupBarList =  getGroupBarChartData(groupBarData, groupBarNames),  // Generate Data for each Group
        barColorPaletteList = itemBarColors, // get Color for each Group
        groupingSize = eachGroupBarSize,
         barStyle= BarStyle(
            barWidth = 12.dp,
            paddingBetweenBars = 8.dp,
            cornerRadius = 12.dp,
//            barShadow = null,
         ),
    )

    val groupBarData = groupBarPlotData.groupBarList

    val xAxisData = AxisData.Builder()
        .axisStepSize(40.dp)
        .steps(groupBarData.size - 1)
        .bottomPadding(10.dp)
        .labelAndAxisLinePadding(20.dp)
        .startDrawPadding(12.dp )
        .labelData { index -> groupBarData[index].label }
        .axisLabelColor(MaterialTheme.colorScheme.onPrimary)
        .axisLineColor(MaterialTheme.colorScheme.onPrimary)
        .backgroundColor(Color(0xFFE6B9BD))
        .build()

    val yAxisData = AxisData.Builder()
        .steps(yStepSize)
        .labelAndAxisLinePadding(20.dp)
        .axisOffset(12.dp)
        .labelData { index -> (index * (max_yValue / yStepSize)).toString() }
        .axisLineColor(MaterialTheme.colorScheme.onPrimary)
//        .axisLabelColor(MaterialTheme.colorScheme.onPrimary)
        .axisLabelColor(MaterialTheme.colorScheme.onPrimary)
        .backgroundColor(Color(0xFFE6B9BD))
        .build()

    val groupBarChartData = GroupBarChartData(
        barPlotData = groupBarPlotData,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        backgroundColor = Color(0xFFE6B9BD),
        paddingEnd = 0.dp,
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color(0xFFE6B9BD)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        // Draw top border using Canvas
        Canvas(modifier = Modifier.fillMaxWidth()) {
            drawLine(
                color = Color.Black,
                strokeWidth = 4.dp.toPx(),
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f)
            )
        }

        GroupBarChart(
            modifier = modifier
                .height(300.dp)
                .wrapContentSize()
                .background(Color(0xFFE6B9BD)),
            groupBarChartData = groupBarChartData,

        )

        val scrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .wrapContentSize()
                .horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.SpaceAround,
        ){
            for (i in 0 until barChartListSize){
                ItemDesciptor(
                    modifier = Modifier
                        .wrapContentSize(),
                    itemBarName = itemBarNames[i],
                    itemBarColor = itemBarColors[i],
                )
            }
        }
    }
}

@Composable
fun ItemDesciptor(
    modifier: Modifier = Modifier,
    itemBarName: String,
    itemBarColor: Color,
) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Center,
    ){
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(percent = 50))
                .background(itemBarColor)
        ) {

        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = itemBarName,
            modifier = Modifier
                .wrapContentSize(),
            color = MaterialTheme.colorScheme.onPrimary,
        )
}

}

fun getGroupBarChartData(groupBarData: List<List<Float>>, groupBarName: List<String>): List<GroupBar> {
    val groupBarPlotData = mutableListOf<GroupBar>()
    for (i in groupBarData.indices){
        val barList = mutableListOf<BarData>()

        for (j in groupBarData[i].indices){
            barList.add(
                BarData(
                    point = Point(i.toFloat(), groupBarData[i][j] ),
                    label = "Bar $j",
                    description = "Bar at $i with label Bar $j hash value ${groupBarData[i][j]}"
                )
            )
        }
        groupBarPlotData.add(GroupBar(groupBarName[i] ?: i.toString(), barList))
    }

    return groupBarPlotData
}