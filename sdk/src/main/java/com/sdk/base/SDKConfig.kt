package com.sdk.base

import android.os.Parcel
import android.os.Parcelable

data class SDKConfig(val domain: String,
                     val port: String
                     ) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(domain)
        writeString(port)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SDKConfig> = object : Parcelable.Creator<SDKConfig> {
            override fun createFromParcel(source: Parcel): SDKConfig = SDKConfig(source)
            override fun newArray(size: Int): Array<SDKConfig?> = arrayOfNulls(size)
        }
    }
}
