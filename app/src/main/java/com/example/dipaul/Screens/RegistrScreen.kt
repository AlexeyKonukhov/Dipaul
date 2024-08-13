package com.example.dipaul.Screens

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.example.dipaul.Cards.DropDownExample
import com.example.dipaul.CustomControl.CustomButton
import com.example.dipaul.CustomControl.TextBox
import com.example.dipaul.data.Roll
import com.example.dipaul.data.ScreenStart
import com.example.dipaul.data.User
import com.example.dipaul.ui.theme.DarkBlue
import com.example.dipaul.ui.theme.LightBlue
import com.example.dipaul.ui.theme.MiddleBlue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrScreen(fs: FirebaseFirestore, navController: NavController, auth: FirebaseAuth) {
    val context = LocalContext.current
    var familyState by remember {
        mutableStateOf("")
    }
    var firstNameState by remember {
        mutableStateOf("")
    }
    var lastNameState by remember {
        mutableStateOf("")
    }
    var emailState by remember {
        mutableStateOf("")
    }
    var passwordState by remember {
        mutableStateOf("")
    }
    var rollState by remember {
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
                        Text(text = "Регистрация", fontSize = 20.sp, color = LightBlue)
                    }

                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description",
                        )
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
                .fillMaxSize(1f)
                .padding(innerPadding)
                .verticalScroll(ScrollState(2), true)
                .imePadding()
                .background(DarkBlue),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp),
                value = familyState,
                onValueChange = { familyState = it },
                label = "Фамилия"
            )

            TextBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp),
                value = firstNameState,
                onValueChange = { firstNameState = it },
                label = "Имя"
            )

            TextBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp),
                value = lastNameState,
                onValueChange = { lastNameState = it },
                label = "Отчество"
            )

            TextBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp),
                value = emailState,
                onValueChange = { emailState = it },
                label = "E-mail"
            )

            TextBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp),
                value = passwordState,
                onValueChange = { passwordState = it },
                label = "Пароль",
                visualTransformation = PasswordVisualTransformation(),
                keyboardOption = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            DropDownExample(
                list = listOf(Roll().kladovshik, Roll().voditel),
                name = "Роль",
                onValueChange = { rollState = it })
            Spacer(modifier = Modifier.height(20.dp))
            CustomButton(
                modifier = Modifier,
                onClick = {
                    if (familyState.isNotEmpty() && firstNameState.isNotEmpty() &&
                        lastNameState.isNotEmpty() && emailState.isNotEmpty() && passwordState.isNotEmpty()
                    ) {
                        SignUp(
                            navController,
                            auth,
                            familyState,
                            firstNameState,
                            lastNameState,
                            emailState,
                            passwordState,
                            rollState,
                            context,
                            fs
                        )
                    } else {
                        Toast.makeText(context, "Не все поля заполнены", Toast.LENGTH_SHORT).show()
                    }
                }, text = "Регистрация"
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

fun SignUp(
    navController: NavController,
    auth: FirebaseAuth,
    family: String,
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    roll: String,
    context: Context,
    fs: FirebaseFirestore
) {

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                fs.collection("users").document(email).set(
                    User(
                        family = family,
                        firstName = firstName,
                        lastName = lastName,
                        login = email,
                        password = password,
                        uid = auth.currentUser?.uid!!,
                        roll = roll
                    )
                )
                navController.navigate(ScreenStart)
            } else {
                Toast.makeText(context, "Ошибка: ${task.exception?.message}", Toast.LENGTH_LONG)
                    .show()
            }
        }
}