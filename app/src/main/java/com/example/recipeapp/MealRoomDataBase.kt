package com.example.recipeapp

import android.content.Context
import androidx.room.*

@Database(entities = [Meal::class], version = 1, exportSchema = false)

@TypeConverters(MealTypeConverter::class)

abstract class MealRoomDataBase:RoomDatabase() {

    abstract fun mealDao():MealDao

    companion object{
        @Volatile
        var INSTANCE:MealRoomDataBase? = null

        fun getInstances(context: Context):MealRoomDataBase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealRoomDataBase::class.java,
                    "meal.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MealRoomDataBase
        }
    }

}