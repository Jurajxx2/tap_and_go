package com.example.tapandgo.repository

import com.example.tapandgo.model.Rental
import kotlinx.coroutines.flow.Flow

interface RentalRepository {

    fun createRental(id: String, from: String, to: String)
    fun getRentals(): Flow<List<Rental>>
}