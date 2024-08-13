package com.example.dipaul.Cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.dipaul.ui.theme.DarkBlue
import com.example.dipaul.ui.theme.LightBlue
import com.example.dipaul.ui.theme.MiddleBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownExample(list: List<String>, name: String, onValueChange: (String) -> Unit) {
    val options = list
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    onValueChange(selectedOptionText)
    ExposedDropdownMenuBox(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .background(DarkBlue),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .padding(5.dp),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            label = { Text(name) },
            shape = RoundedCornerShape(10.dp),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
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
        )
        ExposedDropdownMenu(
            modifier = Modifier
                .fillMaxWidth()
                .background(LightBlue)
                .border(0.dp, LightBlue, RectangleShape),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    colors = MenuDefaults.itemColors(
                        textColor = DarkBlue,
                        trailingIconColor = LightBlue,
                        leadingIconColor = LightBlue
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(LightBlue),
                    text = { Text(selectionOption) },
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                        onValueChange(selectedOptionText)
                    },
                )
            }
        }
    }
}