package com.example.tapandgo.model.remote.rental

import com.example.tapandgo.model.Rental

class GetRentalResponse(val data: List<Rental>) {
    data class Rental(
        val from: String,
        val to: String,
        val subjectID: Int,
        val status: String
    ) {
        fun toLocalRental() = com.example.tapandgo.model.Rental(from,to,subjectID,status)
    }
}