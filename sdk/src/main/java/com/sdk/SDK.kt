package com.sdk

import android.app.Application
import com.google.gson.GsonBuilder
import com.sdk.network.NetworkSecurityModule
import com.sotwtm.support.util.singleton.SingletonHolder3

class SDK(application: Application,
          gsonBuilder: GsonBuilder,
          networkSecurityModule: NetworkSecurityModule) {

//    private val configAPIsMap = ConcurrentHashMap<String, M88ApiComponent>()

//    private val sdkBaseComponent: SdkBaseComponent = DaggerSdkBaseComponent.builder()
//        .application(application)
//        .gsonBuilder(gsonBuilder)
//        .networkSecurityModule(networkSecurityModule)
//        .build()

//    fun setCurrentAPIsConfig(config: M88SDKConfig): M88ApiComponent =
//        configAPIsMap[config.toString()] ?: {
//            val apiComponent = sdkBaseComponent.m88ApiComponentBuilder
//                .config(config)
//                .build()
//            configAPIsMap[config.toString()] = apiComponent
//            apiComponent
//        }.invoke()

    companion object : SingletonHolder3<SDK, Application, GsonBuilder, NetworkSecurityModule>(::SDK)
}
