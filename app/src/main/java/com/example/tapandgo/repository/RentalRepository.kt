package com.example.tapandgo.repository

import com.example.tapandgo.model.Car
import com.example.tapandgo.model.Rental
import com.example.tapandgo.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RentalRepository {

    suspend fun refreshToken()
    suspend fun createRental(id: Int, from: String, to: String)
    fun getRentals(): Flow<Resource<List<Rental>>>
    suspend fun removeAll()
}