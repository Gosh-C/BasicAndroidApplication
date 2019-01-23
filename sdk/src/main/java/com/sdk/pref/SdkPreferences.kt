package com.sdk.pref

import android.content.SharedPreferences
import android.databinding.ObservableBoolean
import com.google.gson.Gson
import com.sotwtm.support.scope.LibScope
import javax.inject.Inject

/**
 */

@LibScope
class SdkPreferences
@Inject
constructor(@param:SdkPreferencesModule.SdkPref private val sharedPreferences: SharedPreferences,
            @param:SdkPreferencesModule.SdkPrefEditor private val editor: SharedPreferences.Editor,
            val gson: Gson) {

    val verifyHttpsCert: ObservableBoolean = object : ObservableBoolean() {
        override fun get(): Boolean = sharedPreferences.getBoolean(KEY_VERIFY_HTTPS_CERT, true)

        override fun set(value: Boolean) {
            when (value) {
                get() -> return
                else -> editor.putBoolean(KEY_VERIFY_HTTPS_CERT, value)
            }
            editor.apply()
            notifyChange()
        }
    }

    var isVideoOn: ObservableBoolean = object : ObservableBoolean() {
        override fun get(): Boolean = sharedPreferences.getBoolean(KEY_VIDEO_ON, true)

        override fun set(value: Boolean) {
            when (value) {
                get() -> return
                else -> editor.putBoolean(KEY_VIDEO_ON, value)
            }
            editor.apply()
            notifyChange()
        }
    }

    var isAudioOn: ObservableBoolean = object : ObservableBoolean() {
        override fun get(): Boolean = sharedPreferences.getBoolean(KEY_AUDIO_ON, true)

        override fun set(value: Boolean) {
            when (value) {
                get() -> return
                else -> editor.putBoolean(KEY_AUDIO_ON, value)
            }
            editor.apply()
            notifyChange()
        }
    }

    companion object {
        const val KEY_VERIFY_HTTPS_CERT = "VerifyHttpsCert"
        const val KEY_AUDIO_ON = "AudioOn"
        const val KEY_VIDEO_ON = "VideoOn"
    }
}
