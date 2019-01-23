package com.sdk.base

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

/**
 * All base stuff for SDK
 * @author Gosh
 */
@Module
abstract class SdkBaseModule() {

    @Binds
    abstract fun context(application: Application): Context
}
