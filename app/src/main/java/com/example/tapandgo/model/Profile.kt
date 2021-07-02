package com.example.tapandgo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class Profile(
    @PrimaryKey val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val createdAt: String,
    val updatedAt: String
    )