package com.example.tapandgo.repository

import com.example.tapandgo.model.Car
import com.example.tapandgo.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CarRepository {
    suspend fun refreshToken()
    fun getCars(): Flow<Resource<List<Car>>>
    suspend fun removeAll()
}