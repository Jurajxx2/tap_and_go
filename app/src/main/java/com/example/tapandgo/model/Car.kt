package com.example.tapandgo.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Car(
    val id: Int = 0,
    val brand: String = "",
    val model: String = "",
    val imageURL: String = "",
    val fuel: String = "",
    val pricePerDay: Int = 0,
    val currency: String = "",
    val year: String = "",
    val type: String = "",
    val status: String = "",
    val groupId: String = "",
    val registrationPlate: String = "",
    val subjectTypeId: Int = 0,
    val location: Location = Location(),
    val carReservations: List<Reservation> = emptyList()
): Serializable {
    data class Location(
        val latitude: Double = 0.0,
        val longitude: Double = 0.0
    ): Serializable

    data class Reservation(
        val from: String = "",
        val to: String = ""
    ): Serializable
}