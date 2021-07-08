package com.example.tapandgo.model.remote.car

import com.example.tapandgo.model.Car

class GetCarsResponse {
    data class Car(
        val id: Int,
        val brand: String,
        val model: String,
        val imageURL: String,
        val fuel: String,
        val pricePerDay: Double,
        val currency: String,
        val year: String,
        val type: String,
        val status: String,
        val groupId: String,
        val registrationPlate: String,
        val createdAt: String,
        val updatedAt: String,
        val subjectTypeId: Int,
        val location: Location
    ) {
        data class Location(
            val latitude: Double,
            val longitude: Double
        )

        fun toLocalCar() = Car(
            id,
            brand,
            model,
            imageURL,
            fuel,
            pricePerDay,
            currency,
            year,
            type,
            status,
            groupId,
            registrationPlate,
            createdAt,
            updatedAt,
            subjectTypeId,
            com.example.tapandgo.model.Car.Location(location.latitude, location.longitude)
        )
    }
}