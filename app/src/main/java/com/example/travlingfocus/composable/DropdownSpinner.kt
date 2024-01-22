package com.example.travlingfocus.composable

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun DisplaySpinner(
    modifier: Modifier = Modifier,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    parent: @Composable (Modifier, () -> Unit) -> Unit,
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options.first()) } // default value

    val SpinnerTabModifier = Modifier
        .wrapContentSize(Alignment.Center)
//        .padding(4.dp)
//        .border(1.dp, MaterialTheme.colorScheme.onSurface, shape = RoundedCornerShape(4.dp))
        .background(MaterialTheme.colorScheme.surface)

//    Need button to show the dropdown menu

    Box(
        modifier = modifier
            .background(Color.Transparent)
            .wrapContentSize(Alignment.CenterEnd)
            .clip(RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center,
    ) {
        parent(
            modifier, // Modifier for the button
            { expanded = !expanded }
        )

        DropdownMenu(
            modifier = modifier
                .wrapContentSize(Alignment.CenterStart)
                .background(MaterialTheme.colorScheme.surface),
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(100.dp, 8.dp)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    modifier = SpinnerTabModifier,
                    onClick = {
                        selectedOption = option
                        expanded = false
                        Toast.makeText(context, "You selected $option", Toast.LENGTH_SHORT).show()
                        onOptionSelected(option)
                    },
                    text = {
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .padding(horizontal = 40.dp, vertical = 8.dp),
                            textAlign = TextAlign.Center,
                            fontWeight = if(selectedOption == option) FontWeight.Bold else FontWeight.Normal,
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DisplaySpinnerPreview() {
    DisplaySpinner(
        options = listOf("Day", "Month", "Year"),
        onOptionSelected = {},
    ){
        modifier, onClick ->
        IconButton(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
//                .fillMaxHeight(),
            onClick = onClick
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Click Here", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                )

            }
        }
    }
}