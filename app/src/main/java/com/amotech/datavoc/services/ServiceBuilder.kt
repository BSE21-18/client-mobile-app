package com.amotech.datavoc.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

object ServiceBuilder {

    private const val URL = "http://137.184.24.216:7000/"

    // Create OkHttp Client
    private val okHttp = OkHttpClient.Builder()

    var gson = GsonBuilder()
        .setLenient()
        .create()!!

    // Create Retrofit Builder
    private val builder = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttp.build())

    // Create Retrofit Instance
    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
}