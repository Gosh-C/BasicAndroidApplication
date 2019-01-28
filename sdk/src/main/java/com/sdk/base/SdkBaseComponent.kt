package com.sdk.base

import android.app.Application
import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sdk.SDK
import com.sdk.api.ApiComponent
import com.sdk.network.NetworkSecurityModule
import com.sdk.network.RetrofitBuilder
import com.sdk.pref.SdkPreferences
import com.sdk.pref.SdkPreferencesModule
import com.sotwtm.support.scope.LibScope
import com.squareup.picasso.Picasso
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector

@LibScope
@Component(
    modules = [
        // Preferences modules
        SdkPreferencesModule::class,
        // Gson, picasso, etc. modules
        SdkHelperModule::class,
        // Network setting modules
        NetworkSecurityModule::class]
)
interface SdkBaseComponent : AndroidInjector<SDK> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun gsonBuilder(gsonBuilder: GsonBuilder = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)): Builder

        fun networkSecurityModule(networkSecurityModule: NetworkSecurityModule): Builder

        fun build(): SdkBaseComponent
    }

    val application: Application
    val context: Context
    val gson: Gson
    val picasso: Picasso
    val sdkPreferences: SdkPreferences
    val retrofitBuilder: RetrofitBuilder

    //Declare SubComponent
    val apiComponentBuilder: ApiComponent.Builder
}
