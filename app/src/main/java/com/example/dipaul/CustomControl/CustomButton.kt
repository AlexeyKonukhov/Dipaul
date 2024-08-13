package com.example.dipaul.CustomControl

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.dipaul.ui.theme.DarkBlue
import com.example.dipaul.ui.theme.LightBlue

@Composable
fun CustomButton(modifier: Modifier = Modifier, onClick: () -> Unit, text: String = "", fontSize: TextUnit = TextUnit.Unspecified) {
    Button(
        modifier = modifier.border(
            border = BorderStroke(2.dp, LightBlue),
            RoundedCornerShape(80.dp)
        ),
        contentPadding = PaddingValues(10.dp),
        shape = RoundedCornerShape(80.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Color.Transparent
        ),
        onClick = onClick) {
        Text(text = text, color = LightBlue, fontSize = fontSize)
    }
}