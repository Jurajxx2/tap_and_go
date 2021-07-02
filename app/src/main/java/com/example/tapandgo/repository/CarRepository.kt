package com.example.tapandgo.repository

import com.example.tapandgo.model.Car
import com.example.tapandgo.model.Cars
import kotlinx.coroutines.flow.Flow

interface CarRepository {

    fun getCars(): Flow<List<Cars>>
}