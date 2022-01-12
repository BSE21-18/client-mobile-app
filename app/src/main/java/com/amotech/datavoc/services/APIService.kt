package com.amotech.datavoc.services

import com.amotech.datavoc.modals.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.*

interface APIService {
    @POST("/subscribe")
    fun postUser(@Body req: HashMap<String, Any>): Call<User>
}