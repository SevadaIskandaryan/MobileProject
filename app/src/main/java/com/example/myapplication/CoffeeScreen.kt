package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext


@Composable
fun CoffeeScreen(
    navController: NavController
){
    currentPrice = 0
    selectedQuantity = 1
    CoffeScreenTopMenu(navController)
}

var selectedSize = "Small"
var selectedQuantity = 1
var currentPrice = 0

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeScreenTopMenu(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val currentCoffee = getCoffeeByID(CurrentCoffeeID)
    currentPrice = currentCoffee.price
    selectedSize = "Small"
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Description",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(route = "CoffeeList") }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        CoffeeScreenScrollContent(innerPadding, navController)
    }
}

@Composable
fun CoffeeScreenScrollContent(innerPadding: PaddingValues, navController: NavController) {
    val currentCoffee = getCoffeeByID(CurrentCoffeeID)
    if (currentPrice == 0) { currentPrice = addToPrice(currentCoffee.price, selectedSize)}
    val resources = LocalContext.current.resources
    val resourceId = resources.getIdentifier(currentCoffee.photoName, "drawable", LocalContext.current.packageName)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(resourceId),
            contentDescription=null,
            modifier = Modifier.size(200.dp),
        )
        Row(){
            Text(
                text =  currentCoffee.name + ": " + currentCoffee.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .height(50.dp)
                    .verticalScroll(rememberScrollState())
            )
        }
        MultipleRadioButtonsSize(currentCoffee)

        CounterButton()
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Button(onClick = {
//                setCoffeeSizeByID(currentCoffee.id, selectedSize)
//                setCoffeeQuantityByID(currentCoffee.id, selectedQuantity)
                navController.navigate(route = "CoffeeList")
                TotalPrice += addToPrice(currentCoffee.price, selectedSize) * selectedQuantity
                setCoffeeCheck(currentCoffee.name, currentPrice, selectedQuantity, selectedSize)
                currentPrice = 0
            },
            ) {
                Text(text = "Add To Cart")
            }
        }

    }
}
fun addToPrice(price: Int,size: String): Int{
    if (size == "Small"){
        return price
    } else if (size == "Medium"){
        return (price + 200)
    } else {
        return (price + 400)
    }
}
@Composable
fun MultipleRadioButtonsSize(currentCoffee: Coffee) {
    val selectedValue = remember { mutableStateOf("") }
    val isSelectedItem: (String) -> Boolean = { selectedValue.value == it }
    val onChangeState: (String) -> Unit = { selectedValue.value = it }

    val items = listOf("Small", "Medium", "Large")
    Column(Modifier.padding(8.dp)) {
        Row() {
            Text(
                text = "Please select size: ${selectedValue.value.ifEmpty { selectedSize }}"
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = "${(currentPrice)}"
            )
        }
        selectedSize = selectedValue.value.ifEmpty { "Small" }
        items.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.selectable(
                    selected = isSelectedItem(item),
                    onClick = {
                        onChangeState(item)
                        currentPrice = addToPrice(currentCoffee.price, item)
                    },
                    role = Role.RadioButton
                ).padding(8.dp)
            ) {
                RadioButton(
                    selected = isSelectedItem(item),
                    onClick = null
                )
                Text(
                    text = item,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


@Composable
fun CounterButton() {
    var count: Int by remember { mutableStateOf(1) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            onClick = {
                // Decrement the counter on button click if count is greater than 1
                if (count > 1) {
                    count--
//                    currentPrice = addToPrice(currentCoffee.price, selectedSize)
                }
            },
            modifier = Modifier
                .padding(8.dp)
                .height(48.dp)
        ) {
            Text("-")
        }

        //Spacer(modifier = Modifier.height(16.dp))

        Text("$count")

        Button(
            onClick = {
                // Increment the counter on button click
                count += 1
//                currentPrice = addToPrice(currentCoffee.price, selectedSize)
            },
            modifier = Modifier
                .padding(8.dp)
                .height(48.dp)
        ) {
            Text("+")
        }
        //Spacer(modifier = Modifier.height(16.dp))
    }
    selectedQuantity = count
}
