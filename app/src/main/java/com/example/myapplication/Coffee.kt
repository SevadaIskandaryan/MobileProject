package com.example.myapplication

data class Coffee (
    val id: Int = 0,
    val name: String = "",
    val price: Int = 0,
    val description: String = "",
    val photoPath: String = "",
    var quantity: Int = 0,
    var size: String = ""
)