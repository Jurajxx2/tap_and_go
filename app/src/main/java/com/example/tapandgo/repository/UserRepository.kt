package com.example.tapandgo.repository

import com.example.tapandgo.model.Profile
import com.example.tapandgo.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun login(email: String, password: String)
    suspend fun logout()
    suspend fun register(firstName: String, lastName: String, email: String, password: String)
    suspend fun getUser(): Flow<Resource<Profile>>
    suspend fun refreshToken()
    suspend fun removeAll()
}