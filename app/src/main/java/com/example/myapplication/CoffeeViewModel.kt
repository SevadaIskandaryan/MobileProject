package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson

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