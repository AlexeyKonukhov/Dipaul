package com.example.dipaul.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ExitToApp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dipaul.CustomControl.CustomButton
import com.example.dipaul.data.AddProd
import com.example.dipaul.data.MaxNumber
import com.example.dipaul.data.Pallets
import com.example.dipaul.data.Roll
import com.example.dipaul.data.ScreenLogin
import com.example.dipaul.data.ScreenPallets
import com.example.dipaul.data.ScreenProductPreview
import com.example.dipaul.data.ScreenSearch
import com.example.dipaul.ui.theme.DarkBlue
import com.example.dipaul.ui.theme.LightBlue
import com.example.dipaul.ui.theme.MiddleBlue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KladovshicScreen(navController: NavController, auth: FirebaseAuth) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MiddleBlue,
                    titleContentColor = LightBlue,
                ),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = { auth.signOut()
                                      navController.navigate(ScreenLogin)},
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color.Transparent
                            )
                        ) {
                            Icon(Icons.AutoMirrored.Rounded.ExitToApp, "", tint = LightBlue)
                        }
                    }
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBlue)
                .padding(0.dp, innerPadding.calculateTopPadding(), 0.dp, 0.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CustomButton(
                onClick = {
                    navController.navigate(ScreenPallets(Roll().kladovshik))
                },
                modifier = Modifier
                    .width(180.dp)
                    .height(120.dp), text = "Паллеты",
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            CustomButton(
                onClick = {
                    navController.navigate(ScreenSearch)
                },
                modifier = Modifier
                    .width(180.dp)
                    .height(120.dp),
                text = "Продукция",
                fontSize = 30.sp
            )
        }
    }
}