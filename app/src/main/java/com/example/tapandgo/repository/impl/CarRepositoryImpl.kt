package com.example.tapandgo.repository.impl

import android.util.Log
import com.example.tapandgo.api.CarService
import com.example.tapandgo.database.CarDao
import com.example.tapandgo.helpers.FirebaseObject
import com.example.tapandgo.model.Car
import com.example.tapandgo.model.Cars
import com.example.tapandgo.repository.CarRepository
import com.example.tapandgo.utils.EventResponse
import com.example.tapandgo.utils.valueEventFlow
import com.google.firebase.database.GenericTypeIndicator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.Exception
import java.lang.NullPointerException

class CarRepositoryImpl(private val carService: CarService, private val carDao: CarDao): CarRepository {

    val database = FirebaseObject.getInstance().getReference("cars")

    @ExperimentalCoroutinesApi
    override fun getCars(): Flow<List<Cars>> {
        val result = database.valueEventFlow()
        val listType = object : GenericTypeIndicator<HashMap<String, List<Car>>>() {}
        val listType2 = object : GenericTypeIndicator<List<Car>>() {}

        return result.map {
            when(it) {
                is EventResponse.Changed -> {
                    val myCars = it.snapshot.children.map { it.children }.map {
                        val cars = it.mapNotNull {
                            val reservations = it.child("reservations").children.mapNotNull { it.getValue(Car.Reservation::class.java) }
                            val car = it.getValue(Car::class.java)
                            car?.copy(carReservations = reservations)
                        }
                        Cars(cars)
                    }
                    myCars
                }
                is EventResponse.Cancelled -> throw it.error.toException()
            }
        }
    }
}