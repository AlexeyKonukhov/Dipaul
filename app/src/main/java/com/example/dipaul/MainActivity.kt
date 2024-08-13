package com.example.dipaul

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.dipaul.Screens.AddProductScreen
import com.example.dipaul.Screens.EditProductScreen
import com.example.dipaul.Screens.KladovshicScreen
import com.example.dipaul.Screens.PalletsScreen
import com.example.dipaul.Screens.ProductScreen
import com.example.dipaul.Screens.RegistrScreen
import com.example.dipaul.Screens.SearchScreen
import com.example.dipaul.Screens.Test
import com.example.dipaul.Screens.VectorPreview
import com.example.dipaul.Screens.VoditellScreen
import com.example.dipaul.data.AddProd
import com.example.dipaul.data.DataStoreManager
import com.example.dipaul.data.ScreenEditProduct
import com.example.dipaul.data.ScreenKladovshik
import com.example.dipaul.data.ScreenLogin
import com.example.dipaul.data.ScreenPallets
import com.example.dipaul.data.ScreenProductPreview
import com.example.dipaul.data.ScreenRegistr
import com.example.dipaul.data.ScreenSearch
import com.example.dipaul.data.ScreenStart
import com.example.dipaul.data.ScreenVoditell
import com.example.dipaul.data.User
import com.example.dipaul.ui.theme.DarkBlue
import com.example.dipaul.ui.theme.DipaulTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val auth = Firebase.auth
            val navController = rememberNavController()
            val fs = Firebase.firestore
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkBlue)
            ) {
                Scr(fs, navController, auth)
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Scr(
    fs: FirebaseFirestore,
    navController: NavHostController,
    auth: FirebaseAuth
) {
    Column {
        NavHost(navController = navController, startDestination = ScreenStart) {
            composable<ScreenLogin> {
                Test(fs, navController, auth)
            }
            composable<ScreenKladovshik> {
                KladovshicScreen(navController, auth)
            }
            composable<AddProd> {
                val args = it.toRoute<AddProd>()
                AddProductScreen(fs, navController, pallets = args.pallet, args.roll)
            }
            composable<ScreenVoditell> {
                VoditellScreen(fs, navController)
            }
            composable<ScreenPallets> {
                val args = it.toRoute<ScreenPallets>()
                PalletsScreen(fs = fs, navController = navController, args.roll)
            }
            composable<ScreenProductPreview> {
                val args = it.toRoute<ScreenProductPreview>()
                ProductScreen(
                    fs = fs,
                    navController = navController,
                    args.palletNumber,
                    args.locationPallet,
                    args.roll
                )
            }
            composable<ScreenEditProduct> {
                val args = it.toRoute<ScreenEditProduct>()
                EditProductScreen(fs = fs, navController = navController, args.pallet, args.product, args.roll)
            }
            composable<ScreenSearch> {
                SearchScreen(fs = fs, navController = navController)
            }
            composable<ScreenRegistr> {
                RegistrScreen(fs = fs, navController = navController, auth)
            }
            composable<ScreenStart> {
                VectorPreview(fs, navController, auth)
            }
        }
    }

}


