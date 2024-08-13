package com.example.dipaul.Screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dipaul.CustomControl.AddButton
import com.example.dipaul.CustomControl.DeleteButton
import com.example.dipaul.CustomControl.DialogAlert
import com.example.dipaul.data.AddProd
import com.example.dipaul.data.Pallets
import com.example.dipaul.data.Product
import com.example.dipaul.data.Roll
import com.example.dipaul.data.ScreenEditProduct
import com.example.dipaul.ui.theme.DarkBlue
import com.example.dipaul.ui.theme.LightBlue
import com.example.dipaul.ui.theme.MiddleBlue
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    fs: FirebaseFirestore,
    navController: NavController,
    pallets: String,
    location: String,
    roll: String = ""
) {
    val context = LocalContext.current
    var locTemp by remember {
        mutableStateOf(location)
    }
    var isAlert by remember{
        mutableStateOf(false)
    }
    var isDelete by remember {
        mutableStateOf(false)
    }
    var deletProduct: Product by remember {
        mutableStateOf(Product())
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MiddleBlue,
                    titleContentColor = LightBlue,
                ),
                title = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        var isDone by remember {
                            mutableStateOf(false)
                        }
                        var MAX_WIDTH_LOCATE by remember {
                            mutableStateOf(1f)
                        }
                        if (isDone) {
                            MAX_WIDTH_LOCATE = 0.85f
                        } else {
                            MAX_WIDTH_LOCATE = 1f;
                        }
                        BasicTextField(
                            modifier = Modifier
                                .fillMaxWidth(MAX_WIDTH_LOCATE)
                                .fillMaxHeight(0.07f)
                                .border(
                                    width = 2.dp,
                                    shape = RoundedCornerShape(40.dp),
                                    color = LightBlue
                                ),
                            value = locTemp,
                            onValueChange = {
                                locTemp = it
                                isDone = true
                            },
                            textStyle = TextStyle(
                                color = LightBlue,
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp
                            ),

                            ) { innerTextField ->
                            BorderStroke(width = 1.dp, color = LightBlue)
                            TextFieldDefaults.DecorationBox(
                                value = locTemp,
                                innerTextField = innerTextField,
                                contentPadding = PaddingValues(10.dp, 5.dp),
                                visualTransformation = VisualTransformation.None,
                                enabled = true,
                                singleLine = true,
                                shape = RoundedCornerShape(40.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    cursorColor = LightBlue,
                                    selectionColors = TextSelectionColors(LightBlue, DarkBlue),
                                    focusedTextColor = LightBlue,
                                    unfocusedTextColor = LightBlue,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent

                                ),
                                interactionSource = remember { MutableInteractionSource() },
                                label = {
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text(text = "Локация", color = LightBlue)
                                }

                            )
                        }



                        IconButton(
                            modifier = Modifier
                                .height(40.dp)
                                .width(40.dp), onClick = {
                                isDone = false
                                fs.collection("pallets").document(pallets).set(
                                    Pallets(
                                        locTemp,
                                        pallets.toInt()
                                    )
                                )
                            }
                        ) {
                            Icon(Icons.Rounded.Done, "", tint = LightBlue)
                        }
                    }
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
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            AddButton(onClick = {
                if (roll == Roll().kladovshik){
                    navController.navigate(AddProd(pallets.toInt(), roll = roll))
                }
                else{
                    Toast.makeText(context, "Недостаточно прав", Toast.LENGTH_LONG).show()
                }
            })
        },

        ) { innerPadding ->
        innerPadding.calculateBottomPadding()
        val listProductsTemp = remember {
            mutableStateOf(emptyList<Product>())
        }
        if (isAlert){
            DialogAlert(isDelete = {isDelete = it}, isAlert = {isAlert = it})
        }
        if (isDelete){
            DeleteProduct(fs, deletProduct)
            isDelete = false
        }
        fs.collection("pallets").document(pallets).collection("products")
            .addSnapshotListener { snapShot, exeption ->
                listProductsTemp.value = snapShot?.toObjects(Product::class.java)!!
            }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, innerPadding.calculateTopPadding(), 0.dp, 0.dp)
                .background(DarkBlue),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(0.dp, 10.dp)
            ) {
                items(listProductsTemp.value) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(125.dp)
                            .padding(10.dp, 4.dp),
                        onClick = {
                            navController.navigate(ScreenEditProduct(pallets, it.alias, roll))
                        },
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                        shape = RoundedCornerShape(10.dp),
                        content = {
                            Column(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {

                                //Top
                                Row(
                                    Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(0.5f),
                                        text = "Паллет №${it.pallet}",
                                        color = DarkBlue
                                    )
                                    Text(
                                        modifier = Modifier.fillMaxWidth(1f),
                                        text = location,
                                        fontSize = 20.sp,
                                        color = DarkBlue
                                    )
                                }

                                //Center
                                Row(
                                    Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        maxLines = 2,
                                        modifier = Modifier
                                            .fillMaxWidth(0.7f),
                                        text = it.name,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = DarkBlue,
                                    )
                                    Text(
                                        text = "${it.count} шт",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = DarkBlue
                                    )
                                    DeleteButton(
                                        modifier = Modifier
                                            .height(30.dp)
                                            .width(30.dp),
                                        onClick = {
                                            if (roll == Roll().kladovshik){
                                                isAlert = true
                                                deletProduct = it
                                            }
                                            else{
                                                Toast.makeText(context, "Недостаточно прав", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    )
                                }

                                //Bottom
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        maxLines = 1,
                                        modifier = Modifier.fillMaxWidth(0.8f),
                                        text = "Счёт: ${it.schet}",
                                        color = DarkBlue
                                    )
                                }

                            }
                        })
                }
            }
        }
    }
}

fun DeleteProduct(fs: FirebaseFirestore, it: Product){
    fs.collection("pallets").document(it.pallet)
        .collection("products").document(it.alias).delete()
}