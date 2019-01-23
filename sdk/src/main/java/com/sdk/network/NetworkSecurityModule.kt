package com.sdk.network

import android.annotation.SuppressLint
import android.text.TextUtils
import com.sdk.pref.SdkPreferences
import com.sotwtm.support.scope.LibScope
import com.sotwtm.util.Log
import dagger.Module
import dagger.Provides
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.internal.tls.OkHostnameVerifier
import okhttp3.logging.HttpLoggingInterceptor
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

/**
 * Module provide [X509TrustManager] and [HostnameVerifier]
 * @author Gosh
 */

@Module
class NetworkSecurityModule
/**
 * @Param providedTrustManager A trust manager for cert check. null will use OkHttp default.
 * @Param providedHostnameVerifier A [HostnameVerifier] for host name check. null will use OkHttp default.
 * */
constructor(private val trustedCert: Array<X509Certificate> = arrayOf(),
            private val providedHostnameVerifier: HostnameVerifier = OkHostnameVerifier.INSTANCE,
            private val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .connectionSpecs(listOf(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT))) {

    @Provides
    @LibScope
    fun getTrustManager(sdkPreferences: SdkPreferences): X509TrustManager = X509TrustCertManager(trustedCert, sdkPreferences)

    @Provides
    @LibScope
    fun getHostnameVerifier(sdkPreferences: SdkPreferences): HostnameVerifier = OptionalHostnameVerifier(providedHostnameVerifier, sdkPreferences)

    @Provides
    @LibScope
    fun createOkHttpClient(trustManager: X509TrustManager?,
                           hostnameVerifier: HostnameVerifier?): OkHttpClient {

        if (trustManager != null) {
            try {
                if (Log.isDebuggable) {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.level = HttpLoggingInterceptor.Level.BODY
                    okHttpClientBuilder.addInterceptor(interceptor)
                }

                // Create an TLS socket factory
                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(null, arrayOf<TrustManager>(trustManager), SecureRandom())
                val sslSocketFactory = sslContext.socketFactory
                okHttpClientBuilder.sslSocketFactory(sslSocketFactory, trustManager)
            } catch (e: NoSuchAlgorithmException) {
                Log.e("Cannot find the algorithm", e)
            } catch (e: KeyManagementException) {
                Log.e("Error on key management", e)
            }
        }
        if (hostnameVerifier != null) {
            okHttpClientBuilder.hostnameVerifier(hostnameVerifier)
        }

        return okHttpClientBuilder.build()
    }

    class OptionalHostnameVerifier(private val defaultVerifier: HostnameVerifier = OkHostnameVerifier.INSTANCE,
                                   private val sdkPreferences: SdkPreferences) : HostnameVerifier {

        override fun verify(hostname: String?, session: SSLSession?): Boolean =
                !sdkPreferences.verifyHttpsCert.get() ||
                        defaultVerifier.verify(hostname, session)
    }

    /**
     * Trust manager to trust given cert.
     * @author John
     */
    class X509TrustCertManager
    constructor(private val trustedCert: Array<X509Certificate> = arrayOf(),
                private val sdkPreferences: SdkPreferences) : X509TrustManager {

        @SuppressLint("TrustAllX509TrustManager")
        @Throws(CertificateException::class)
        override fun checkClientTrusted(chains: Array<X509Certificate>?, authType: String?) {
            // Trust all client cert
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(chains: Array<X509Certificate>?, authType: String?) {

            if (!sdkPreferences.verifyHttpsCert.get()) {
                // Trust all server cert
                return
            }

            if (TextUtils.isEmpty(authType)) {
                throw CertificateException("No auth type!")
            }

            if (chains?.isEmpty() != false) {
                throw CertificateException("No cert chains!")
            }
            chains.forEach {
                it.checkValidity()
            }
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> = trustedCert
    }
}
