package com.example.travlingfocus.composable

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
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
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.toSize
import com.example.travlingfocus.R
import com.example.travlingfocus.home.ActivityTag
import com.example.travlingfocus.home.MainViewModel
import com.example.travlingfocus.home.TimerType
import com.example.travlingfocus.ui.theme.PinkGray
import com.example.travlingfocus.ui.theme.PinkLight
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

public const val totalTime = 7200000f

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Timer(
    handleColor: Color,
    activeBarColor: Color,
    inactiveBarColor: Color,
    modifier: Modifier,
    strokeWidth: Dp = 10.dp,
    timerType: TimerType = TimerType.Timer,
    viewModel: MainViewModel,
    openBottomSheet : () -> Unit,
    triggerTimerFromOutSize: Int = 0
) {
    val draggThreshold = (strokeWidth.value * 3 )/(125 * Math.PI) * 180

    var center by remember {
        mutableStateOf(Offset.Zero)
    }
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val chosenTime by viewModel.timerValue.observeAsState(60000f)

    Log.d("chosenTime", chosenTime.toString())

    var value by remember  {
        mutableStateOf(chosenTime / totalTime)
    }
    Log.d("value", value.toString())

    var currentTime by remember {
        mutableStateOf(chosenTime)
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

    var oldPositionTime by remember {
        mutableStateOf(chosenTime)
    }

    Log.d("oldPositionTime", oldPositionTime.toString())

    LaunchedEffect(key1 = triggerTimerFromOutSize, block = {
        Log.d("triggerTimerFromOutSize", triggerTimerFromOutSize.toString())
            if(!isTimerRunning && triggerTimerFromOutSize != 0) {
                isTimerRunning = !isTimerRunning
            }
    })
    var quote by remember {
        mutableStateOf(MotivatedVote.quote0)
    }

    LaunchedEffect(key1 = chosenTime, block = {
        currentTime = chosenTime
        value = currentTime / totalTime
        oldPositionTime = currentTime
    })

    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
        if (timerType == TimerType.Timer) {
            if (currentTime > 0f && isTimerRunning) {
                delay(100L)
                currentTime -= 100f
                value = currentTime / totalTime.toFloat()
                if(currentTime.mod(10000f) == 0f)
                {
                    quote = MotivatedVote.values()[(quote.ordinal + 1).mod(MotivatedVote.values().size)]
                }
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

            AnimatedContent(
                targetState = quote,
                transitionSpec = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(durationMillis = 500)
                    ) togetherWith ExitTransition.None
                }
            ) { targetState ->
                Text(
                    text = "${targetState.quote}",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
//            Text(
//                text = "Let's get ready to ramble!",
//
//            )
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
                                    Log.d("DragStartedAngle", dragStartedAngle.toString())
                                },
                                onDrag = { change, _ ->
                                    var touchAngle = -atan2(
                                        x = center.y - change.position.y,
                                        y = center.x - change.position.x
                                    ) * 180 / Math.PI.toFloat()
                                    touchAngle = (touchAngle).mod(360f)
                                    Log.d("touchAngle", touchAngle.toString())

                                    val currentAngle = oldPositionTime * 360f / totalTime

                                    if (touchAngle < 90f && value * 360f > 270f) {
                                        touchAngle += 360f
                                    }
//
                                    if (touchAngle > 270f && value * 360f < 90f) {
                                        touchAngle -= 360f
                                    }
                                    changeAngle = touchAngle - currentAngle
                                    Log.d("currentAngle", currentAngle.toString())

                                    val lowerThreshold = currentAngle - draggThreshold
                                    val higherThreshold = currentAngle + draggThreshold

                                    val newTime =
                                        (oldPositionTime + (changeAngle / (360f / totalTime)).roundToInt())

                                    Log.d("Old time", oldPositionTime.toString())
                                    Log.d("current time", currentTime.toString())
                                    Log.d("newTime", newTime.toString())


                                    if (dragStartedAngle in lowerThreshold..higherThreshold &&
                                        (currentTime < totalTime || newTime < currentTime)
                                        && (newTime > currentTime || currentTime > 0)
                                        && abs(newTime - currentTime) > 120000f && !isTimerRunning
                                    ) {
                                        if (newTime > currentTime + 120000f)
                                            currentTime += 120000f
                                        else currentTime -= 120000f
                                        value = currentTime / totalTime
//                                        oldPositionTime = currentTime
//                                        viewModel.updateTimerValue(currentTime)

                                    }

                                },
                                onDragEnd = {
                                    oldPositionTime = currentTime
                                    viewModel.updateTimerValue(currentTime)

//                           onPositionChange(positionValue)
                                }
                            )
                        }
                ) {
                    drawCircle(
                        color = PinkLight,
                    )
                    val radiousX = size.width * 0.8f
                    val radiousY = size.height * 0.35f
                    drawOval(
                        color = PinkGray,
                        topLeft = Offset((center.x - radiousX / 2), center.y),
                        size = Size(radiousX, radiousY),
//                        style = Stroke(width = 2.dp.toPx())
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

                val density = LocalDensity.current

                val infiniteTransition = rememberInfiniteTransition()
                val offset by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = -32f,
                    animationSpec = infiniteRepeatable(
                        tween(1000),
                        repeatMode = RepeatMode.Reverse
                    ),

                )

                AnimatedContent(
                    targetState = quote,
                    transitionSpec = {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(durationMillis = 500)
                        ) togetherWith ExitTransition.None
                    }
                ){
                    targetState ->
                    GifImage(data = iconOfquote[targetState]!!,
                        mayBeginGifAnimation = isTimerRunning,
                        onGifClick = {
                                      if(!isTimerRunning) {
                                          openBottomSheet()
                                      }
                        } ,
                        modifier = Modifier
                            .size(148.dp)
                            .offset {
                                if (isTimerRunning) Offset(
                                    0f,
                                    offset
                                ).toIntOffset() else Offset.Zero.toIntOffset()
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

//            tag text
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .wrapContentSize()
                    .border(
                        BorderStroke(2.dp, Color.White),
                        RoundedCornerShape(20.dp)
                    )
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            )
            {
                val selectedTag by viewModel.selectedTag.observeAsState(ActivityTag.frend)
                Text(
                    text = '#' + selectedTag.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.padding(8.dp)
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

            PrimButton(
                onButtonClick = {
                    if (currentTime > 0f) {
                        isTimerRunning = !isTimerRunning
                    }

                },
                colors = ButtonDefaults.run {
                    val buttonColors = buttonColors(
                        if (!isTimerRunning || currentTime <= 0L) MaterialTheme.colorScheme.secondary else  MaterialTheme.colorScheme.primary
                    )
                    buttonColors
                },
                borderStroke =  if (isTimerRunning) BorderStroke(2.dp, Color.White) else null  ,
                text = if (isTimerRunning) "Cancel" else "Travel",
            )
            Spacer(modifier = Modifier.height(32.dp))

    }
}

private fun Offset.toIntOffset() = IntOffset(x.roundToInt(), y.roundToInt())

fun timeFormatUtil (time: Float): String {
    val minutes = (time / 60000).toInt().toString().padStart(2, '0')
    val seconds =  ((time.toInt() % 60000) / 1000).toString().padStart(2, '0')
    return minutes + " : " + seconds
}

enum class MotivatedVote (val quote: String ) {
    quote0("Let's get ready to ramble"),
    quote1("Focus sharp, success closer."),
    quote2("Eyes on the goal, distractions fade."),
    qoute3("Concentrate, conquer, celebrate."),
    quote4("Block distractions, unlock achievements."),
    quote5("Time invested, focus manifested."),
    quote6("Distractions out, determination in."),
    quote7("Mind sharp, goals in sight."),
    quote8("Stay centered, success enters."),
    quote9("Focused steps lead to triumph."),
    quote10("In the zone, greatness grown.")


}

private val iconOfquote = mapOf(
    MotivatedVote.quote0 to R.drawable.ic_balo,
    MotivatedVote.quote1 to R.drawable.ic_map,
    MotivatedVote.quote2 to R.drawable.ic_guitar,
    MotivatedVote.qoute3 to R.drawable.ic_camera,
    MotivatedVote.quote4 to R.drawable.ic_health,
    MotivatedVote.quote5 to R.drawable.ic_ticket,
    MotivatedVote.quote6 to R.drawable.ic_toothpath,
    MotivatedVote.quote7 to R.drawable.ic_bento,
    MotivatedVote.quote8 to R.drawable.ic_clothe,
    MotivatedVote.quote9 to R.drawable.ic_card,
    MotivatedVote.quote10 to R.drawable.ic_car,

)