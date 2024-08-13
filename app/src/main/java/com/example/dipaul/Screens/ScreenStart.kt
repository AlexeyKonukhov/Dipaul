package com.example.dipaul.Screens

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dipaul.data.Roll
import com.example.dipaul.data.ScreenKladovshik
import com.example.dipaul.data.ScreenLogin
import com.example.dipaul.data.ScreenPallets
import com.example.dipaul.data.ScreenStart
import com.example.dipaul.data.ScreenVoditell
import com.example.dipaul.data.User
import com.example.dipaul.ui.theme.DarkBlue
import com.example.dipaul.ui.theme.LightBlue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun VectorPreview(fs: FirebaseFirestore, navController: NavController, auth: FirebaseAuth) {

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        if (auth.currentUser != null) {
            auth.currentUser?.getIdToken(true)?.addOnCompleteListener { user ->
                if (user.isSuccessful) {
                    fs.collection("users").document(auth.currentUser?.email!!).get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                val tempUser = task.result.toObject(User::class.java)

                                if (tempUser?.roll == Roll().kladovshik) {
                                    navController.navigate(ScreenKladovshik) {
                                        this.popUpTo(0)
                                    }
                                    Toast.makeText(
                                        context,
                                        "Здравствуйте, ${tempUser.firstName}!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                                if (tempUser?.roll == Roll().voditel) {
                                    navController.navigate(ScreenVoditell) {
                                        this.popUpTo(0)
                                    }
                                    Toast.makeText(
                                        context,
                                        "Здравствуйте, ${tempUser.firstName}!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                } else {
                    navController.navigate(ScreenLogin) {
                        this.popUpTo(0)
                    }
                }
            }
        } else {
            navController.navigate(ScreenLogin) {
                this.popUpTo(0)
            }
        }
    }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(DipaulLogo, null, modifier = Modifier.fillMaxHeight(0.4f))
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Диполь", fontSize = 40.sp, color = LightBlue)
    }
}


private var _DipaulLogo: ImageVector? = null

val DipaulLogo: ImageVector
    get() {
        if (_DipaulLogo != null) {
            return _DipaulLogo!!
        }
        _DipaulLogo = ImageVector.Builder(
            name = "ЛоготипДиполь",
            defaultWidth = 288.dp,
            defaultHeight = 480.dp,
            viewportWidth = 24f,
            viewportHeight = 40f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF0EB0B2)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(23.9032f, 0f)
                horizontalLineTo(15.9336f)
                horizontalLineTo(7.9696f)
                horizontalLineTo(0f)
                verticalLineTo(7.9696f)
                verticalLineTo(15.9448f)
                horizontalLineTo(7.9696f)
                verticalLineTo(7.9696f)
                horizontalLineTo(15.9336f)
                verticalLineTo(15.9448f)
                horizontalLineTo(23.9032f)
                verticalLineTo(7.9696f)
                verticalLineTo(0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFDF1831)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(23.9032f, 31.8783f)
                verticalLineTo(23.9031f)
                horizontalLineTo(15.9336f)
                verticalLineTo(31.8783f)
                horizontalLineTo(7.9696f)
                verticalLineTo(23.9031f)
                horizontalLineTo(0f)
                verticalLineTo(31.8783f)
                verticalLineTo(39.8423f)
                horizontalLineTo(7.9696f)
                horizontalLineTo(15.9336f)
                horizontalLineTo(23.9032f)
                verticalLineTo(31.8783f)
                close()
            }
        }.build()
        return _DipaulLogo!!
    }
