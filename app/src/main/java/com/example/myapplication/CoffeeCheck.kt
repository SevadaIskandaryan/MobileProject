package com.example.myapplication

class CoffeeCheck (
    val name: String = "",
    val priceUnit: Int = 0,
    val priceTotal: Int = 0,
    val quantity: Int = 0,
    val size: String = ""
)

val coffeCheckList = mutableListOf<CoffeeCheck>()

fun setCoffeeCheck(name: String, priceUnit: Int, quantity: Int, size: String ) {
    coffeCheckList.add(CoffeeCheck(name, priceUnit, priceUnit*quantity, quantity, size))
}