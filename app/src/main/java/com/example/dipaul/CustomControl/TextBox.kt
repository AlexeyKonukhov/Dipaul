package com.example.dipaul.CustomControl

import android.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.HistoricalChange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.dipaul.ui.theme.DarkBlue
import com.example.dipaul.ui.theme.LightBlue

@Composable
fun TextBox(
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOption: KeyboardOptions = KeyboardOptions.Default,
    label: String = "",
    value: String = "",
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = LocalTextStyle.current
) {
    OutlinedTextField(
        textStyle = textStyle,
        modifier = modifier,
        maxLines = 1,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOption,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = DarkBlue,
            unfocusedContainerColor = DarkBlue,
            focusedTextColor = LightBlue,
            unfocusedTextColor = LightBlue,
            focusedLabelColor = LightBlue,
            unfocusedLabelColor = LightBlue,
            focusedBorderColor = LightBlue,
            unfocusedBorderColor = LightBlue
        ),
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label)
        })
}