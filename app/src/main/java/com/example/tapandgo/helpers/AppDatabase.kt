package com.example.tapandgo.helpers

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tapandgo.database.CarDao
import com.example.tapandgo.database.RentalDao
import com.example.tapandgo.database.UserDao
import com.example.tapandgo.model.Car
import com.example.tapandgo.model.Profile
import com.example.tapandgo.model.Rental

@Database(entities = [Profile::class, Rental::class, Car::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun rentalDao(): RentalDao
    abstract fun carDao(): CarDao

    companion object {

        private const val DATABASE_NAME = "tap_and_go"
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }
}