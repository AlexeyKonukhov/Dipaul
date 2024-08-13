package com.example.dipaul.CustomControl

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dipaul.ui.theme.DarkBlue
import com.example.dipaul.ui.theme.LightBlue

@Composable
fun AddButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    FloatingActionButton(shape = CircleShape, modifier = modifier, onClick = onClick, containerColor = LightBlue) {
        Icon(Icons.Rounded.Add, "", tint = DarkBlue, modifier = Modifier.height(40.dp).width(40.dp))
    }
}