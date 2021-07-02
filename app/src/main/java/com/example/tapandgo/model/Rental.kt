package com.example.tapandgo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rentals")
class Rental(
    @PrimaryKey val id: String
)