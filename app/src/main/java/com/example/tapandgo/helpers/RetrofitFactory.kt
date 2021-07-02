package com.example.tapandgo.helpers

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    private val retrofit: Retrofit

    init {

        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    val authToken = EncryptedPrefs.getToken()
                    if (authToken != null) builder.header("Authorization", authToken)
                    return@Interceptor chain.proceed(builder.build())
                }
            )
        }.build()

        retrofit = Retrofit.Builder()
            .baseUrl("http://digilab.herokuapp.com/")
            .callFactory(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> getApi(service: Class<T>): T {
        return retrofit.create(service)
    }
}