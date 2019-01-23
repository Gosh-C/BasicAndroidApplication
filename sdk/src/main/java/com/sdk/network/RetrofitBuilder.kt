package com.sdk.network

import com.google.gson.Gson
import com.sotwtm.support.scope.LibScope

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

/**
 * Module provides a method to build [Retrofit]

 * @author Gosh
 */

@LibScope
class RetrofitBuilder
@Inject
constructor(gson: Gson,
            internal val okHttpClient: OkHttpClient) {

    internal val gsonConverterFactory: GsonConverterFactory = GsonConverterFactory.create(gson)

    fun createRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
}
