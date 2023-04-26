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
import retrofit2.http.Query

class HomeViewModel(

    private val mealRoomDataBase: MealRoomDataBase

):ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var PopularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    private var CategoryMealLiveData = MutableLiveData<List<Category>>()
    private var favoriteMealsLiveData = mealRoomDataBase.mealDao().getAllMeals()
    private var bottomSheetMealliveData = MutableLiveData<Meal>()
    private var searchedMealLiveData = MutableLiveData<List<Meal>>()

    init{
        getRandomMeal()
    }
    fun getRandomMeal(){

        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) { // iska matlab retrofit connected hai
                if(response.body() != null){
                    val randomMeal:Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal

                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {// isa matlab retrofit connected nahi hai
                Log.d("HomeFragment",t.message.toString())
            }
        })
    }

    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object : Callback<MealsByCategoryList>{
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                if(response.body() != null){
                    PopularItemsLiveData.value = response.body()!!.meals

                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("HomeFragment",t.message.toString())
            }
        })
    }

    fun getCategories(){
        RetrofitInstance.api.getCategoryItems().enqueue(object  : Callback<CategoryList>{

            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {

                if(response.body() != null){
                    CategoryMealLiveData.value = response.body()!!.categories

                }

            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("HomeViewModel",t.message.toString())
            }
        })
    }

    fun getMealById(id:String){
        RetrofitInstance.api.getMealsDetails(id).enqueue(object : Callback<MealList>{

            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal = response.body()?.meals?.first()
                meal?.let { meal ->

                    bottomSheetMealliveData.postValue(meal)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("HomeViewModel",t.message.toString())
            }
        })
    }

    fun deleteData(meal: Meal){
        viewModelScope.launch {
            mealRoomDataBase.mealDao().delete(meal)
        }
    }

    fun insertData(meal: Meal){
        viewModelScope.launch {
            mealRoomDataBase.mealDao().upsertMeal(meal)
        }
    }

    fun searchMeal(searchQuery: String) = RetrofitInstance.api.searchMeal(searchQuery).enqueue(
        object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val mealsList = response.body()?.meals
                mealsList?.let {
                    searchedMealLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("HomeViewModel",t.message.toString())
            }
        }
    )

    fun observeSearchedMealLiveData():LiveData<List<Meal>>{
        return searchedMealLiveData
    }

    // in mutable live data we can read and change its data but in live data we can only read its data

    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }

    fun observePopularMealLiveData() : LiveData<List<MealsByCategory>>{
        return PopularItemsLiveData
    }

    fun observeCategoriesLiveData():LiveData<List<Category>>{

        return CategoryMealLiveData

    }

    fun observeFavoriteslistLiveDate():LiveData<List<Meal>>{
        return favoriteMealsLiveData
    }

    fun observeBottomSheetMeal():LiveData<Meal>{
        return bottomSheetMealliveData
    }

}