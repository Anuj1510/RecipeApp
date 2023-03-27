package com.example.recipeapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(

    val mealRoomDataBase: MealRoomDataBase

):ViewModel() {

    private var mealDetailListLiveData = MutableLiveData<Meal>()
    private var favoritesMealLiveData = MutableLiveData<List<Meal>>()

    fun getMealDetail(id:String){
        RetrofitInstance.api.getMealsDetails(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() != null){
                    mealDetailListLiveData.value = response.body()!!.meals[0]
                }
                else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MealActivity",t.message.toString())
            }
        })
    }

    fun observeMealDetaillivedata():LiveData<Meal>{
        return mealDetailListLiveData
    }

    fun insertData(meal: Meal){
        viewModelScope.launch {
            mealRoomDataBase.mealDao().upsertMeal(meal)
        }
    }



    fun observeFavoritesLiveData():LiveData<List<Meal>>{
        return favoritesMealLiveData
    }
}