package com.example.dipaul.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dipaul.CustomControl.DeleteButton
import com.example.dipaul.CustomControl.DialogAlert
import com.example.dipaul.data.Pallets
import com.example.dipaul.data.Product
import com.example.dipaul.data.ScreenEditProduct
import com.example.dipaul.ui.theme.DarkBlue
import com.example.dipaul.ui.theme.LightBlue
import com.example.dipaul.ui.theme.MiddleBlue
import com.google.firebase.firestore.FirebaseFirestore


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(fs: FirebaseFirestore, navController: NavController) {
    var searchText by remember {
        mutableStateOf("")
    }
    val listProducts: MutableState<List<Product>> = remember {
        mutableStateOf(emptyList())
    }
    val listPallets: MutableState<List<Pallets>> = remember {
        mutableStateOf(emptyList())
    }
    LaunchedEffect(key1 = false) {
        GetData(fs, listProducts, listPallets)
    }
    var tempListProduct: List<Product> by remember {
        mutableStateOf(emptyList())
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
    tempListProduct = listProducts.value
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MiddleBlue,
                    titleContentColor = LightBlue,
                ),
                title = {
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.07f)
                            .border(width = 2.dp, color = LightBlue, RoundedCornerShape(20.dp)),
                        value = searchText,
                        onValueChange = {
                            searchText = it
                            tempListProduct = Search(searchText, listProducts.value)
                        },
                        textStyle = TextStyle(fontSize = 15.sp, color = LightBlue)
                    ) { innerTextField ->
                        TextFieldDefaults.DecorationBox(
                            value = searchText,
                            innerTextField = innerTextField,
                            contentPadding = PaddingValues(35.dp, 0.dp),
                            visualTransformation = VisualTransformation.None,
                            enabled = true,
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedTextColor = LightBlue,
                                unfocusedTextColor = LightBlue,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent

                            ),
                            interactionSource = remember { MutableInteractionSource() }

                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(5.dp, 0.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = "",
                                tint = LightBlue
                            )
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
        }
    ) { innerPadding ->
        if (isAlert){
            DialogAlert(isDelete = {isDelete = it}, isAlert = {isAlert = it})
        }
        if (isDelete){
            DeleteProduct(fs, deletProduct)
            isDelete = false
        }
        Column(
            Modifier
                .fillMaxSize()
                .background(DarkBlue)
                .padding(0.dp, innerPadding.calculateTopPadding(), 0.dp, 0.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(0.dp, 10.dp)
            ) {
                items(tempListProduct) { it ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(125.dp)
                            .padding(10.dp, 4.dp),
                        onClick = {
                            navController.navigate(ScreenEditProduct(it.pallet, it.alias))
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
                                        text = listPallets.value.get(listPallets.value.indexOfFirst { p -> p.number.toString() == it.pallet }).location,
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
                                        color = DarkBlue
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
                                            isAlert = true
                                            deletProduct = it
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

fun Search(text: String, list: List<Product>): List<Product> {
    return list.filter {
        it.schet.startsWith(text)
    }
}

fun GetData(fs: FirebaseFirestore, listProducts: MutableState<List<Product>>? = null, listPallets: MutableState<List<Pallets>>){
    fs.collection("pallets").get().addOnCompleteListener { pallets ->
        if (pallets.isSuccessful) {
            listPallets.value = pallets.result.toObjects(Pallets::class.java)
            if (listProducts != null){
                for (pallet in pallets.result.toObjects(Pallets::class.java)) {
                    fs.collection("pallets").document(pallet.number.toString()).collection("products")
                        .get().addOnCompleteListener { products ->
                            if(products.isSuccessful){
                                listProducts.value += products.result.toObjects(Product::class.java)
                            }
                        }
                }
            }
        }
    }
}