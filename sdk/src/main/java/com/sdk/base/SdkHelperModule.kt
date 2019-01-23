package com.sdk.base

import android.content.Context
import android.util.Base64
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.sotwtm.support.scope.LibScope
import com.sotwtm.util.Log
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.ResponseBody

@Module
class SdkHelperModule {

    @Provides
    @LibScope
    fun gson(gsonBuilder: GsonBuilder): Gson = gsonBuilder
            .setLenient()
            .create()

    @Provides
    @LibScope
    fun picasso(context: Context): Picasso =
            Picasso.Builder(context).build()

    // Check if it is still needed
    fun jsonPicasso(context: Context, gson: Gson): Picasso =
            Picasso.Builder(context)
                    .downloader(OkHttp3Downloader(OkHttpClient.Builder().addInterceptor { chain ->
                        try {
                            val response = chain.proceed(chain.request().newBuilder().build())
                            val jsonObject = gson.fromJson(response.body()?.string(), JsonObject::class.java)
                            val imgStr = jsonObject.get("imageString").asString
                            val body = ResponseBody.create(response.body()?.contentType(), Base64.decode(imgStr, Base64.DEFAULT))
                            response.newBuilder().body(body).build()
                        } catch (th: Throwable) {
                            Log.e("Error on intercept http response", th)
                            throw th
                        }
                    }.build()))
                    .build()
}
