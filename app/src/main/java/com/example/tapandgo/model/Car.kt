package com.example.tapandgo.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tapandgo.model.remote.car.GetCarsResponse
import java.io.Serializable

@Entity(tableName = "cars")
data class Car(
    @PrimaryKey val id: Int,
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
): Serializable {
    data class Location(
        val latitude: Double = 0.0,
        val longitude: Double = 0.0
    ): Serializable
}