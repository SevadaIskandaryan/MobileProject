package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


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