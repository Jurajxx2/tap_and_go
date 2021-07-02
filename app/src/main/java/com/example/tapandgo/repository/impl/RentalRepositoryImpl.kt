package com.example.tapandgo.repository.impl

import com.example.tapandgo.api.RentalService
import com.example.tapandgo.database.RentalDao
import com.example.tapandgo.model.Rental
import com.example.tapandgo.repository.RentalRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect

class RentalRepositoryImpl(private val rentalService: RentalService, private val rentalDao: RentalDao): RentalRepository {

    override fun createRental(id: String, from: String, to: String) {

    }

    @ExperimentalCoroutinesApi
    override fun getRentals() = callbackFlow {
        rentalDao.getAll().collect { send(it) }
    }
}