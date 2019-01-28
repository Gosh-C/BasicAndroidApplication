package com.sdk.api

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.sdk.api.services.ApiService
import com.sdk.base.SDKConfig
import com.sdk.pref.SdkPreferences
import com.squareup.picasso.Picasso
import dagger.BindsInstance
import dagger.Subcomponent

@PerConfig
@Subcomponent(
        modules = [
//            ViewModelModule::class,
//            BrandPreferencesModule::class,
            ServicesModule::class
        ]
)
interface ApiComponent {

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun config(config: SDKConfig): Builder

        fun build(): ApiComponent
    }

    //Parent components
    val application: Application
    val context: Context
    val gson: Gson
    val picasso: Picasso
    val sdkPreferences: SdkPreferences

    // Config values
    val sdkConfig: SDKConfig

    // API services
    val apiService: ApiService
}
