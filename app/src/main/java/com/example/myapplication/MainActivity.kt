package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingPreview()
                }
            }
        }
    }
}
var TotalPrice = 0
var CurrentCoffeeID = 0
var coffeeList = mutableListOf<Coffee>()

@Composable
fun getCoffeeList() : List<Coffee>{
    val file_name = "coffees.json"
    val json_string = LocalContext.current.assets.open(file_name).bufferedReader().use {
        it.readText()
    }
    val gson = Gson()
    coffeeList = gson.fromJson(json_string, Array<Coffee>::class.java).toMutableList()
    return coffeeList
}

fun getCoffeeByID(ID: Int): Coffee {
    val coffee: Coffee = coffeeList.find { it.id == ID }!!
    return coffee
}

fun setCoffeeSizeByID(ID: Int, size: String) {
    coffeeList?.find { it.id == ID }?.size = size
}

fun setCoffeeQuantityByID(ID: Int, quantity: Int) {
    coffeeList?.find { it.id == ID }?.quantity = quantity
}

@Composable
fun CustomNavigationView() {
    val navController = rememberNavController()
    getCoffeeList()
    NavHost(navController = navController, startDestination = "CoffeeList") {
        composable(
            route = "CoffeeList",
        ) {
            CoffeeList(navController)
        }
        composable(
            route = "CoffeeScreen",
        ) {
            CoffeeScreen(navController)
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        CustomNavigationView()
    }
}