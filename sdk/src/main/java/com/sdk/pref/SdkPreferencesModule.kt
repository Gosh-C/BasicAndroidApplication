package com.sdk.pref

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.sdk.base.SdkBaseModule
import com.sotwtm.support.scope.LibScope
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

/**
 * Provides [SharedPreferences]
 * @author John
 */

@Module(includes = [SdkBaseModule::class])
class SdkPreferencesModule {

    @Provides
    @LibScope
    @SdkPref
    fun getSharedPreferences(context: Context): SharedPreferences =
            context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    @SuppressLint("CommitPrefEdits")
    @Provides
    @LibScope
    @SdkPrefEditor
    fun getSharedPreferencesEditor(@SdkPref sharedPreferences: SharedPreferences): SharedPreferences.Editor = sharedPreferences.edit()

    @Qualifier
    @Retention(AnnotationRetention.SOURCE)
    annotation class SdkPref

    @Qualifier
    @Retention(AnnotationRetention.SOURCE)
    annotation class SdkPrefEditor

    companion object {
        const val SHARED_PREF_NAME = "BASE_SDK"
    }
}
