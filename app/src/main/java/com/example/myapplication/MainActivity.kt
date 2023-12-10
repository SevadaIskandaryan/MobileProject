package com.example.myapplication

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
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
                    AppPreview()
                }
            }
        }
    }
}
var TotalPrice = 0
var CurrentCoffeeID = 0
var coffeeList = mutableListOf<Coffee>()
val coffeCheckList = mutableListOf<CoffeeCheck>()
class CoffeeViewModel(application: Application) : AndroidViewModel(application) {
    private var coffeeList: List<Coffee> = emptyList()

    fun getCoffeeList(): List<Coffee> {
        if (coffeeList.isEmpty()) {
            loadCoffeeList()
        }
        return coffeeList
    }

    private fun loadCoffeeList() {
        val file_name = "coffees.json"
        val json_string = getApplication<Application>().assets.open(file_name).bufferedReader().use {
            it.readText()
        }
        val gson = Gson()
        coffeeList = gson.fromJson(json_string, Array<Coffee>::class.java).toList()
    }
}

fun getCoffeeByID(ID: Int): Coffee {
    val coffee: Coffee = coffeeList.find { it.id == ID }!!
    return coffee
}


fun setCoffeeCheck(name: String, priceUnit: Int, quantity: Int, size: String ) {
    coffeCheckList.add(CoffeeCheck(name, priceUnit, priceUnit*quantity, quantity, size))
}

@Composable
fun CustomNavigationView() {
    val navController = rememberNavController()
    val viewModel: CoffeeViewModel = viewModel()

    LaunchedEffect(viewModel) {
        coffeeList = viewModel.getCoffeeList() as MutableList<Coffee>
    }

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
        composable(
            route = "Check",
        ) {
            Check(navController)
        }
    }
}
@Preview(showBackground = true)
@Composable
fun AppPreview() {
    MyApplicationTheme {
        CustomNavigationView()
    }
}