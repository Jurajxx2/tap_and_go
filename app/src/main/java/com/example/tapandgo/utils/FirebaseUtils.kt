package com.example.tapandgo.utils

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.callbackFlow

sealed class EventResponse {
    data class Changed(val snapshot: DataSnapshot): EventResponse()
    data class Cancelled(val error: DatabaseError): EventResponse()
}

@ExperimentalCoroutinesApi
fun DatabaseReference.valueEventFlow() = callbackFlow<EventResponse> {
    val valueEventListener = object: ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot): Unit = sendBlocking(EventResponse.Changed(snapshot))
        override fun onCancelled(error: DatabaseError): Unit = sendBlocking(EventResponse.Cancelled(error))
    }
    addValueEventListener(valueEventListener)
    awaitClose {
        removeEventListener(valueEventListener)
    }
}