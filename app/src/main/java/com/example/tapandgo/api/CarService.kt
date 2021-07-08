package com.example.tapandgo.api

import com.example.tapandgo.model.remote.car.GetCarsResponse
import retrofit2.http.GET

interface CarService {

    @GET("cars")
    suspend fun getCars(): List<GetCarsResponse.Car>
}