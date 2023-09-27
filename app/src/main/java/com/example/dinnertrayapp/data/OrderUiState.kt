package com.example.dinnertrayapp.data

data class OrderUiState(
    val maindish:String = "",
    val sidedish:String = "",
    val dessert:String = "",
    val price:String = "",
    val pickupOptions: List<String> = listOf()

)
