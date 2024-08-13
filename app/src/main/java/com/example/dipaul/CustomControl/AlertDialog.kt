package com.example.dipaul.CustomControl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.dipaul.ui.theme.DarkBlue
import com.example.dipaul.ui.theme.LightBlue
import com.example.dipaul.ui.theme.MiddleBlue


@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog

        Card(
            modifier = Modifier
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.background(MiddleBlue),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(5.dp))

                Icon(icon, "", tint = Color.Red, modifier = Modifier
                    .width(40.dp)
                    .height(40.dp))
                Text(text = dialogTitle, color = LightBlue, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = dialogText,
                    modifier = Modifier.padding(16.dp),
                    color = LightBlue,
                    fontSize = 15.sp
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(0.5f),
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = DarkBlue
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Отмена", color = Color.Green)
                    }
                    TextButton(
                        onClick = { onConfirmation() },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(1f),
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = DarkBlue
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Да", color = Color.Red)
                    }
                }
            }
        }
    }
}


@Composable
fun DialogAlert(isDelete: (Boolean) -> Unit, isAlert: (Boolean) -> Unit){
    AlertDialogExample(
        onDismissRequest = { isDelete(false)
            isAlert(false)},
        onConfirmation = {
            isDelete(true)
            isAlert(false)
        },
        dialogTitle = "Осторожно!",
        dialogText = "Вы точно хотите удалить?",
        icon = Icons.Rounded.Warning
    )
}