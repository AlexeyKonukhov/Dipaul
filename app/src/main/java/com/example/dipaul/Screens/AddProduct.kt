package com.example.dipaul.Screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dipaul.CustomControl.AddButton
import com.example.dipaul.CustomControl.TextBox
import com.example.dipaul.data.Product
import com.example.dipaul.data.Roll
import com.example.dipaul.ui.theme.DarkBlue
import com.example.dipaul.ui.theme.LightBlue
import com.example.dipaul.ui.theme.MiddleBlue
import com.google.api.Context
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    fs: FirebaseFirestore,
    navController: NavController,
    pallets: Int,
    roll: String = ""
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MiddleBlue,
                    titleContentColor = LightBlue,
                ),
                title = {

                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description",
                            tint = LightBlue
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        var nameProduct by remember {
            mutableStateOf("")
        }
        var schetProduct by remember {
            mutableStateOf("")
        }
        var countProduct by remember {
            mutableStateOf("")
        }
        Column(
            Modifier
                .fillMaxSize()
                .background(DarkBlue)
                .padding(0.dp, innerPadding.calculateTopPadding(), 0.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Добавление в паллет №${pallets}", fontSize = 20.sp, color = LightBlue)
            Spacer(modifier = Modifier.height(30.dp))

            TextBox(
                value = nameProduct,
                onValueChange = { nameProduct = it.uppercase() },
                label = "Наименование"
            )

            TextBox(value = schetProduct, onValueChange = { schetProduct = it }, label = "Счёт")

            TextBox(
                value = countProduct,
                onValueChange = { countProduct = it },
                label = "Количество"
            )

            Spacer(modifier = Modifier.height(20.dp))

            AddButton(onClick = {
                if (roll == Roll().kladovshik){
                    if (nameProduct.length > 0) {
                        fs.collection("pallets").document(pallets.toString()).collection("products")
                            .document(nameProduct.replace('/', '#')).set(
                                Product(
                                    count = countProduct,
                                    schet = schetProduct,
                                    location = "",
                                    pallet = pallets.toString(),
                                    name = nameProduct,
                                    alias = nameProduct.replace('/', '#')
                                )
                            )
                        countProduct = ""
                        schetProduct = ""
                        nameProduct = ""
                    } else {
                        Toast.makeText(context, "Введите наименование", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(context, "Недостаточно прав", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}