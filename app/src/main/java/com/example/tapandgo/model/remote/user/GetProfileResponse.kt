package com.example.tapandgo.model.remote.user

import com.example.tapandgo.model.Profile

class GetProfileResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val createdAt: String,
    val updatedAt: String
) {
    fun toLocalProfile() = Profile(id, firstName, lastName, email, createdAt, updatedAt)
}