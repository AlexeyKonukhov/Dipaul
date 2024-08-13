package com.example.dipaul.data

data class Pallets(
    val location: String = "",
    val number: Int = 0,
    val products: List<Product> = emptyList()
)
