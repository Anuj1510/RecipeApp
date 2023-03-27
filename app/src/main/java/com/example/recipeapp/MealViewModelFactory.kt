package com.example.recipeapp


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MealViewModelFactory(
    private val mealRoomDataBase: MealRoomDataBase
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealViewModel(mealRoomDataBase) as T
    }

}