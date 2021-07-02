package com.example.tapandgo.helpers

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.app
import org.koin.android.ext.koin.androidContext

object FirebaseObject {

    private lateinit var instance: FirebaseDatabase

    fun initialize(context: Context) {
        val options = FirebaseOptions.Builder()
            .setApiKey("AIzaSyBq7QeObuorvIJhtioTBMWipfpSekLWv4w")
            .setDatabaseUrl("https://digilab-30302.firebaseio.com")
            .setProjectId("digilab-30302")
            .setApplicationId("com.example.tapandgo")
            .setStorageBucket("digilab-30302.appspot.com")
            .setGcmSenderId("170028295851")
            .build()
        val myApp = FirebaseApp.initializeApp(context, options, "car_database")
        instance = FirebaseDatabase.getInstance(myApp)
    }

    fun getInstance() = instance
}