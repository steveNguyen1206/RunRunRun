package com.example.travlingfocus.composable

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.livedata.observeAsState
import com.example.travlingfocus.R
import com.example.travlingfocus.home.MainViewModel
import com.example.travlingfocus.home.TimerType
import com.example.travlingfocus.ui.theme.PinkLight
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

public const val totalTime = 7200000f

@Composable
fun Timer(
    handleColor: Color,
    activeBarColor: Color,
    inactiveBarColor: Color,
    modifier: Modifier,
    strokeWidth: Dp = 10.dp,
    timerType: TimerType = TimerType.Timer,
    viewModel: MainViewModel,
    openBottomSheet: () -> Unit,
) {
    val draggThreshold = (strokeWidth.value * 3 )/(125 * Math.PI) * 180

    var center by remember {
        mutableStateOf(Offset.Zero)
    }
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val chosenValue by viewModel.timerValue.observeAsState(60000f)

    var value by remember {
        mutableStateOf(chosenValue / totalTime)
    }

    var currentTime by remember {
        mutableStateOf(chosenValue)
    }
    var isTimerRunning by remember {
        mutableStateOf(false)
    }

    var changeAngle by remember {
        mutableStateOf(0f)
    }

    var dragStartedAngle by remember {
        mutableStateOf(0f)
    }

    var oldPositionValue by remember {
        mutableStateOf(chosenValue)
    }


    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
        if (timerType == TimerType.Timer) {
            if (currentTime > 0f && isTimerRunning) {
                delay(100L)
                currentTime -= 100f
                value = currentTime / totalTime.toFloat()
            } else if (currentTime <= 0f) {
                isTimerRunning = false
            }
        } else {
            if (isTimerRunning && currentTime < totalTime) {
                delay(100L)
                currentTime += 100f
                value = currentTime / totalTime.toFloat()
            }
            else if (currentTime >= totalTime) {
                isTimerRunning = false
            }
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Let's get ready to ramble!",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(32.dp))

            Box (
                modifier = Modifier
                    .size(250.dp),
                contentAlignment = Alignment.Center
            )
            {
                Canvas(
                    modifier = Modifier
                        .size(250.dp)
                        .onSizeChanged {
                            size = it
                        }
                        .pointerInput(true) {
                            detectDragGestures(
                                onDragStart = {
                                    dragStartedAngle = -atan2(
                                        x = center.y - it.y,
                                        y = center.x - it.x
                                    ) * 180 / Math.PI.toFloat()
                                    dragStartedAngle = (dragStartedAngle).mod(360f)
//                           Log.d("DragStartedAngle", dragStartedAngle.toString())
                                },
                                onDrag = { change, _ ->
                                    var touchAngle = -atan2(
                                        x = center.y - change.position.y,
                                        y = center.x - change.position.x
                                    ) * 180 / Math.PI.toFloat()
                                    touchAngle = (touchAngle).mod(360f)
//                           Log.d("touchAngle", touchAngle.toString())

                                    val currentAngle = oldPositionValue * 360f / totalTime

                                    if (touchAngle < 90f && value * 360f > 270f) {
                                        touchAngle += 360f
                                    }
//
                                    if (touchAngle > 270f && value * 360f < 90f) {
                                        touchAngle -= 360f
                                    }
                                    changeAngle = touchAngle - currentAngle
//                           Log.d("currentAngle", currentAngle.toString())

                                    val lowerThreshold = currentAngle - draggThreshold
                                    val higherThreshold = currentAngle + draggThreshold

                                    val newTime =
                                        (oldPositionValue + (changeAngle / (360f / totalTime)).roundToInt())

                                    if (dragStartedAngle in lowerThreshold..higherThreshold &&
                                        (currentTime < totalTime || newTime < currentTime)
                                        && (newTime > currentTime || currentTime > 0)
                                        && abs(newTime - currentTime) > 120000f && !isTimerRunning
                                    ) {
                                        if (newTime > currentTime + 120000f)
                                            currentTime += 120000f
                                        else currentTime -= 120000f
                                        value = currentTime / totalTime
                                        viewModel.updateTimerValue(currentTime)
                                    }

                                },
                                onDragEnd = {
                                    oldPositionValue = currentTime
//                           onPositionChange(positionValue)
                                }
                            )
                        }
                ) {

                    drawCircle(
                        color = PinkLight,
                    )
                    drawArc(
                        color = inactiveBarColor,
                        startAngle = -90f,
                        sweepAngle = 360f,
                        useCenter = false,
                        size = Size(size.width.toFloat(), size.height.toFloat()),
                        style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
                    )
                    drawArc(
                        color = activeBarColor,
                        startAngle = -90f,
                        sweepAngle = 360f * value,
                        useCenter = false,
                        size = Size(size.width.toFloat(), size.height.toFloat()),
                        style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
                    )
                    center = Offset(size.width.toFloat() / 2, size.height.toFloat() / 2)
                    val beta = (360f * value - 90f) * (Math.PI / 180f).toFloat()
                    val r = size.width.toFloat() / 2
                    val a = cos(beta) * r
                    val b = sin(beta) * r
                    drawPoints(
                        listOf(Offset(center.x + a, center.y + b)),
                        pointMode = PointMode.Points,
                        color = handleColor,
                        strokeWidth = (strokeWidth * 3f).toPx(),
                        cap = StrokeCap.Round
                    )
                }

                GifImage(data = R.drawable.cute_travel_1 ,
                    mysize = coil.size.Size(250, 250),
                    mayBeginGifAnimation = isTimerRunning,
                    openButtonSheet = openBottomSheet
                )
            }
        }

            Text(
                text = timeFormatUtil(currentTime),
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(32.dp)
            )
            Button(
                onClick = {
                    if (currentTime >  0f) {
                        isTimerRunning = !isTimerRunning
                    }
                },

                colors = ButtonDefaults.run {
                    val buttonColors = buttonColors(
                        if (!isTimerRunning || currentTime <= 0L) MaterialTheme.colorScheme.secondary else Color.Transparent
                    )
                    buttonColors
                },
                shape = RoundedCornerShape(20.dp),
                border = if (isTimerRunning) BorderStroke(2.dp, Color.White) else null,
                modifier = Modifier
                    .padding(8.dp, 32.dp)
            )
            {
                Text(
                    text = if (isTimerRunning) "Cancel"
                    else "Travel",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(modifier = Modifier.height(32.dp))

    }
}

fun timeFormatUtil (time: Float): String {
    val minutes = (time / 60000).roundToInt().toString().padStart(2, '0')
    val seconds =  ((time.toInt() % 60000) / 1000).toString().padStart(2, '0')
    return minutes + " : " + seconds
}