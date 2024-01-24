package com.example.travlingfocus.timline

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.travlingfocus.composable.PrimButton
import com.example.travlingfocus.composable.SelectRow
import com.example.travlingfocus.composable.SelectTagRow
import com.example.travlingfocus.data.Trip
import com.example.travlingfocus.home.ActivityTag
import com.example.travlingfocus.home.TripDetails
import com.example.travlingfocus.ui.theme.GrayContainer

@Composable
fun EditDialog (
    onDismissRequest: () -> Unit,
    onEidtRequest: () -> Unit,
    onShareRequest: () -> Unit,
)
{
    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextButton(
                    onClick = { onEidtRequest() },
                )
                {
                    Text(
                        text = "Edit",
                        modifier = Modifier.padding(8.dp),
                        color = Color.Black
                    )
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                        .border(2.dp, color = Color.Black),
                )
                TextButton(
                    onClick = { onShareRequest() },
                )
                {
                    Text(
                        text = "Share",
                        modifier = Modifier.padding(8.dp),
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun EditMessageDialog(
    onDismissRequest: () -> Unit,
    onSaveRequest: () -> Unit,
    tripDetails: TripDetails,
    updateTrip: (TripDetails) -> Unit,
)
{
    var selectedTag by remember {
        mutableStateOf(ActivityTag.valueOf(tripDetails.tag))
    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, color = Color.White, shape = RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            backgroundColor = MaterialTheme.colorScheme.primary,
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = tripDetails.desResId),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(160.dp)
                        .fillMaxWidth()
                )

                SelectTagRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    list = ActivityTag.values().map { '#' + it.name },
                    onChose = {
                        selectedTag = ActivityTag.valueOf(it.substring(1))
                        updateTrip(
                            tripDetails.copy(
                                tag = it.substring(1)
                            )
                        )
                    },
                    selectedIndex = selectedTag.ordinal,
                )
                    OutlinedTextField(
                        value = "",
                        onValueChange = {  },
                        label = { Text("Trip note") },
                        minLines = 3,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedLabelColor = Color.Transparent,
                        ),
                    )

                PrimButton(
                    onButtonClick = onDismissRequest,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = Color.White
                    ),
                    text = "Save",
                )
            }
        }
    }
}