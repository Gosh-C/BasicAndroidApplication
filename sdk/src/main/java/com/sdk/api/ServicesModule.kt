package com.sdk.api

import com.sdk.api.services.ApiService
import com.sdk.base.SDKConfig
import com.sdk.network.RetrofitBuilder
import dagger.Module
import dagger.Provides

@Module
class ServicesModule {
    @Provides
    @PerConfig
    fun apiService(retrofitBuilder: RetrofitBuilder,
                                config: SDKConfig
    ): ApiService {
        var url = config.domain
        if("" != config.port){
            url = url + ":" + config.port
        }

        return retrofitBuilder
            .createRetrofit(url)
            .create(ApiService::class.java)
    }
}
