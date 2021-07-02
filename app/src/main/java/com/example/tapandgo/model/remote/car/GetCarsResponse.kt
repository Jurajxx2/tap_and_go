package com.example.tapandgo.model.remote.car

class GetCarsResponse(val data: List<Car>) {
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
    }
}