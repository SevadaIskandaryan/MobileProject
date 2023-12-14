package com.example.myapplication

data class Coffee (
    val id: Int = 0,
    val name: String = "",
    val price: Int = 0,
    val description: String = "",
    val photoName: String = ""
)


var TotalPrice = 0
var CurrentCoffeeID = 0
var coffeeList = mutableListOf<Coffee>()
fun getCoffeeByID(ID: Int): Coffee {
    val coffee: Coffee = coffeeList.find { it.id == ID }!!
    return coffee
}
