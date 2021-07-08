package com.example.tapandgo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rentals")
class Rental(
    val from: String,
    val to: String,
    val subjectID: Int,
    val status: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)