package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson

@Composable
fun CoffeeList(
    navController: NavController
) {

    val file_name = "coffees.json"
    val json_string = LocalContext.current.assets.open(file_name).bufferedReader().use{
        it.readText()
    }
    val gson = Gson()
    val coffeeList: List<Coffee> = gson.fromJson(json_string, Array<Coffee>::class.java).toList()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp))
    {
        LazyColumn(Modifier.weight(1f)) {
            items(coffeeList) { coffee ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                navController.navigate(route = "CoffeeScreen")
                                CurrentCoffee = coffee
                            }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp)
                        ) {
                            Row() {
                                Text(
                                    text = coffee.name,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                                Spacer(Modifier.weight(1f))
                                Text(
                                    text = coffee.price.toString()+"AMD",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        Text(
            text = "Total: ${TotalPrice} AMD",
            fontSize = 20.sp,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(
                    color = Color(0xFF2196F3),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(8.dp)),
        )
    }
}