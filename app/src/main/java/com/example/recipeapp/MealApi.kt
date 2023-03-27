package com.example.recipeapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// every api has two parts...the first part is base part which common in every api and here we needed the second part

interface MealApi {

    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php?")
    fun getMealsDetails(@Query("i")id:String) : Call<MealList>

    @GET("filter.php?")
    fun getPopularItems(@Query("c")id: String) : Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategoryItems() : Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName:String) : Call<MealsByCategoryList>

    @GET("search.php")
    fun searchMeal(@Query("s") searchQuery: String) : Call<MealList>

}