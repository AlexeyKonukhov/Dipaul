package com.example.dipaul.data

import kotlinx.serialization.Serializable

@Serializable
object ScreenLogin

@Serializable
object ScreenKladovshik

@Serializable
data class ScreenProductPreview(
    val palletNumber: String,
    val locationPallet: String,
    val roll: String = ""
)

@Serializable
object ScreenVoditell

@Serializable
data class ScreenPallets(
    val roll: String = ""
)

@Serializable
data class AddProd(
    val pallet: Int,
    val roll: String = ""
)

@Serializable
data class ScreenEditProduct(
    val pallet: String,
    val product: String,
    val roll: String = ""
)

@Serializable
object ScreenSearch

@Serializable
object ScreenRegistr

@Serializable
object ScreenStart