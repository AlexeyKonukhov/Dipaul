package com.example.dipaul.Screens

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dipaul.CustomControl.CustomButton
import com.example.dipaul.CustomControl.TextBox
import com.example.dipaul.data.Roll
import com.example.dipaul.data.ScreenKladovshik
import com.example.dipaul.data.ScreenRegistr
import com.example.dipaul.data.ScreenVoditell
import com.example.dipaul.data.User
import com.example.dipaul.ui.theme.DarkBlue
import com.example.dipaul.ui.theme.LightBlue
import com.example.dipaul.ui.theme.MiddleBlue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Test(fs: FirebaseFirestore, navController: NavController, auth: FirebaseAuth) {
    val context = LocalContext.current
    var emailState by remember {
        mutableStateOf("")
    }
    var passwordState by remember {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MiddleBlue,
                    titleContentColor = LightBlue,
                ),
                title = {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Диполь", fontSize = 40.sp, color = LightBlue)
                    }

                },
                windowInsets = TopAppBarDefaults.windowInsets
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(30.dp),
                containerColor = DarkBlue,
                contentColor = LightBlue,
                content = {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "prod by Alexey Konukhov", fontSize = 9.sp, color = LightBlue)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(DarkBlue),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            TextBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(10.dp, 0.dp),
                value = emailState,
                onValueChange = {
                    emailState = it
                },
                label = "E-mail"
            )
            TextBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(10.dp, 0.dp),
                value = passwordState,
                onValueChange = {
                    passwordState = it
                },
                label = "Пароль",
                visualTransformation = PasswordVisualTransformation(),
                keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Spacer(modifier = Modifier.height(40.dp))

            CustomButton(onClick = {
                SignIn(
                    fs,
                    navController,
                    auth = auth,
                    email = emailState,
                    password = passwordState,
                    context = context
                )
            }, text = "Вход")

            Spacer(modifier = Modifier.height(20.dp))

            TextButton(onClick = {
                navController.navigate(ScreenRegistr)
            }) {
                Text(text = "Регистрация")
            }

        }
    }
}

fun SignIn(
    fs: FirebaseFirestore,
    navController: NavController,
    auth: FirebaseAuth,
    email: String,
    password: String,
    context: Context
) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { user ->
            if (user.isSuccessful) {
                Toast.makeText(context, "Авторизация пройдена", Toast.LENGTH_SHORT).show()
                fs.collection("users").document(auth.currentUser?.email!!).get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val temp = task.result.toObject(User::class.java)
                            if (temp?.roll == Roll().kladovshik)
                                navController.navigate(ScreenKladovshik){
                                    this.popUpTo(0)
                                }
                            if (temp?.roll == Roll().voditel)
                                navController.navigate(ScreenVoditell){
                                    this.popUpTo(0)
                                }
                        }
                    }
            } else {
                Toast.makeText(context, "Ошибка: ${user.exception?.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
}