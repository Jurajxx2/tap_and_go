package com.example.tapandgo.model.remote.rental

class GetRentalResponse(val data: List<Rental>) {
    data class Rental(
        val from: String,
        val to: String,
        val subjectID: Int,
        val status: String
    )
}