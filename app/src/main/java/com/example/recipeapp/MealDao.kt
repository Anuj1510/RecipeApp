package com.example.recipeapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMeal(meal:Meal) // mai isse meal ko insert and update dono kr sakta hu

    @Delete
    suspend fun delete(meal:Meal)

    @Query("SELECT * FROM mealInformation")
    fun getAllMeals():LiveData<List<Meal>>

}