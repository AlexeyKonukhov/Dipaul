package com.example.dipaul.Screens

import android.annotation.SuppressLint
import android.util.Log
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
import com.example.dipaul.CustomControl.CustomButton
import com.example.dipaul.CustomControl.TextBox
import com.example.dipaul.data.Pallets
import com.example.dipaul.data.Product
import com.example.dipaul.data.Roll
import com.example.dipaul.ui.theme.DarkBlue
import com.example.dipaul.ui.theme.LightBlue
import com.example.dipaul.ui.theme.MiddleBlue
import com.google.firebase.firestore.FirebaseFirestore


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductScreen(
    fs: FirebaseFirestore,
    navController: NavController,
    pallet: String,
    nameProd: String,
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
        var isUpdate by remember {
            mutableStateOf(false)
        }
        if (!isUpdate) {
            fs.collection("pallets")
                .document(pallet)
                .collection("products")
                .document(nameProd).get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        try {
                            val temp = task.result.toObject(Product::class.java)
                            nameProduct = temp?.name!!
                            schetProduct = temp.schet
                            countProduct = temp.count
                        } catch (ex: Exception) {
                            Log.v("fs", ex.message.toString())
                        }

                    }
                }
        }
        Column(
            Modifier
                .fillMaxSize()
                .padding(0.dp, innerPadding.calculateTopPadding(), 0.dp, 0.dp)
                .background(DarkBlue),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Редактирование товара", fontSize = 20.sp, color = LightBlue)
            Spacer(modifier = Modifier.height(30.dp))

            TextBox(value = nameProduct, onValueChange = {
                isUpdate = true
                nameProduct = it.uppercase()
            }, label = "Наименование")

            TextBox(value = schetProduct, onValueChange = {
                isUpdate = true
                schetProduct = it
            }, label = "Счёт")

            TextBox(value = countProduct, onValueChange = {
                isUpdate = true
                countProduct = it
            }, label = "Количество")



            Spacer(modifier = Modifier.height(20.dp))

            CustomButton(onClick = {
                if (roll == Roll().kladovshik){
                    fs.collection("pallets").document(pallet).collection("products").document(nameProd)
                        .delete()
                    fs.collection("pallets").document(pallet).collection("products")
                        .document(nameProduct.replace('/', '#')).set(
                            Product(
                                name = nameProduct,
                                alias = nameProduct.replace('/', '#'),
                                schet = schetProduct,
                                count = countProduct,
                                location = "",
                                pallet = pallet
                            )
                        )
                    navController.popBackStack()
                }
                else{
                    Toast.makeText(context, "Недостаточно прав", Toast.LENGTH_LONG).show()
                }

            }, text = "Сохранить")
        }
    }
}