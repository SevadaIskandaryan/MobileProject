package com.example.myapplication

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController


@Composable
fun CoffeeScreen(
    navController: NavController
){
    Button(onClick = {
        navController.navigate(route = "CoffeeList")
    }) {
        Text(text = CurrentCoffee.name)
    }
}