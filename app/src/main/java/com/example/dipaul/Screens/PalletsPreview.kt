package com.example.dipaul.Screens

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Search
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dipaul.CustomControl.AddButton
import com.example.dipaul.CustomControl.DeleteButton
import com.example.dipaul.CustomControl.DialogAlert
import com.example.dipaul.data.AddProd
import com.example.dipaul.data.MaxNumber
import com.example.dipaul.data.Pallets
import com.example.dipaul.data.Product
import com.example.dipaul.data.Roll
import com.example.dipaul.data.ScreenProductPreview
import com.example.dipaul.ui.theme.DarkBlue
import com.example.dipaul.ui.theme.LightBlue
import com.example.dipaul.ui.theme.MiddleBlue
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PalletsScreen(fs: FirebaseFirestore, navController: NavController, roll: String = "") {
    var locTemp by remember {
        mutableStateOf("")
    }
    val listPallets = remember {
        mutableStateOf(emptyList<Pallets>())
    }
    var tempListPallets: List<Pallets> by remember {
        mutableStateOf(emptyList())
    }
    fs.collection("pallets").addSnapshotListener { snapshot, e ->
        if (snapshot != null) {
            listPallets.value = snapshot.toObjects(Pallets::class.java)
        }
    }
    val context = LocalContext.current
    var isAlert by remember{
        mutableStateOf(false)
    }
    var isDelete by remember {
        mutableStateOf(false)
    }
    var deletPallets: Pallets by remember {
        mutableStateOf(Pallets())
    }

    tempListPallets = listPallets.value
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MiddleBlue,
                    titleContentColor = LightBlue,
                ),
                title = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        BasicTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.07f)
                                .border(width = 2.dp, color = LightBlue, RoundedCornerShape(20.dp)),
                            value = locTemp,
                            onValueChange = {
                                locTemp = it
                                tempListPallets = SearchPallets(locTemp, listPallets.value)
                            },
                            textStyle = TextStyle(fontSize = 15.sp, color = LightBlue)
                        ) { innerTextField ->
                            TextFieldDefaults.DecorationBox(
                                value = locTemp,
                                innerTextField = innerTextField,
                                contentPadding = PaddingValues(30.dp, 0.dp),
                                visualTransformation = VisualTransformation.None,
                                enabled = true,
                                singleLine = true,
                                shape = RoundedCornerShape(40.dp),
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
                    fs.collection("number").document("number").get().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val tempNumber = task.result.toObject(MaxNumber::class.java)?.maxNumber!!
                            fs.collection("pallets").document((tempNumber + 1).toString())
                                .set(Pallets("Ground", tempNumber + 1))
                            fs.collection("number").document("number").set(MaxNumber(tempNumber + 1))
                            navController.navigate(AddProd(tempNumber + 1, roll = roll))
                        }
                    }
                }
                else{
                    Toast.makeText(context, "Недостаточно прав", Toast.LENGTH_LONG).show()
                }
            })
        }
    ) { innerPadding ->
        if (isAlert){
            DialogAlert(isDelete = {isDelete = it}, isAlert = {isAlert = it})
        }
        if (isDelete){
            DeletePallet(fs, deletPallets)
            isDelete = false
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
                userScrollEnabled = true,
                contentPadding = PaddingValues(0.dp,10.dp)
            ) {
                items(tempListPallets) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 4.dp),
                        onClick = {
                            navController.navigate(
                                ScreenProductPreview(
                                    it.number.toString(),
                                    it.location,
                                    roll
                                )
                            )
                        },
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        content = {
                            Column(
                                Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = it.number.toString(),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = DarkBlue
                                    )
                                    Text(text = it.location, fontSize = 15.sp, color = DarkBlue)
                                    DeleteButton(modifier = Modifier.height(30.dp).width(30.dp), onClick = {
                                        if (roll == Roll().kladovshik){
                                            isAlert = true
                                            deletPallets = it
                                        }
                                        else{
                                            Toast.makeText(context, "Недостаточно прав", Toast.LENGTH_LONG).show()
                                        }
                                    })
                                }
                            }
                        })
                }
            }
        }
    }
}

fun SearchPallets(text: String, list: List<Pallets>): List<Pallets> {
    return list.filter {
        it.number.toString().startsWith(text)
    }
}

fun DeletePallet(fs: FirebaseFirestore, it: Pallets){
    fs.collection("pallets").document(it.number.toString()).delete()
}