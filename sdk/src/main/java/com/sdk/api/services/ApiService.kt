package com.sdk.api.services

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("connectionConfig.json")
    fun getServerConfig(): Call<JsonObject>
}
