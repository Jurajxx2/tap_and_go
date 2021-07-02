package com.example.tapandgo.model.remote.user

import com.example.tapandgo.model.Profile

data class PostRegistrationResponse(
    val accessToken: String,
    val refreshToken: String,
    val profile: Profile
) {
    data class Profile(
        val id: String,
        val firstName: String,
        val lastName: String,
        val email: String,
        val createdAt: String,
        val updatedAt: String
    ) {
        fun toLocalProfile() = com.example.tapandgo.model.Profile(id, firstName, lastName, email, createdAt, updatedAt)
    }
}