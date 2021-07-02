package com.example.tapandgo.api

import com.example.tapandgo.model.remote.rental.GetRentalResponse
import com.example.tapandgo.model.remote.rental.PostCreateRentalRequest
import com.example.tapandgo.model.remote.rental.PostCreateRentalResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RentalService {

    @GET("rentals")
    suspend fun getRentals(): GetRentalResponse

    @POST("rentals")
    suspend fun createRental(@Body body: PostCreateRentalRequest): PostCreateRentalResponse

}