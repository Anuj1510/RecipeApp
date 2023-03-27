package com.example.recipeapp


import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConverter {

    @TypeConverter
    fun fromStringToAny(attribute:Any?) : String{
        if(attribute == null){
            return ""
        }
        return attribute as String
    }

    @TypeConverter
    fun fromAnyToString(attribute:String?) : Any{
        if(attribute == null){
            return ""
        }
        return attribute
    }

}