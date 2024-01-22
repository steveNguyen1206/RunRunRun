package com.example.travlingfocus.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrimButton(
    onButtonClick: () -> Unit,
    colors: ButtonColors,
    borderStroke: BorderStroke ? = null,
    text: String,
) {
    Button(
        onClick = {
            onButtonClick()
        },
        colors = colors,
        shape = RoundedCornerShape(20.dp),
        border = borderStroke,
        modifier = Modifier
            .padding(8.dp, 32.dp)
            .shadow(elevation = 9.dp, spotColor = Color.Black, ambientColor = Color.Black, shape = RoundedCornerShape(20.dp))
    )
    {
        Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}