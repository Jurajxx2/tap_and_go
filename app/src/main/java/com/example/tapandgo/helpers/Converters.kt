package com.example.tapandgo.helpers

import androidx.room.TypeConverter
import com.example.tapandgo.model.Car
import com.google.gson.Gson

class Converters {
     @TypeConverter
     fun fromString(value: String): Car.Location {
         return Gson().fromJson(value, Car.Location::class.java)
     }

    @TypeConverter
    fun localFileToString(value: Car.Location): String {
        return Gson().toJson(value)
    }
}