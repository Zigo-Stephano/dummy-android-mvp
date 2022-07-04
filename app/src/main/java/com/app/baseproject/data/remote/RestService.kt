package com.app.baseproject.data.remote

import android.content.Context
import com.app.baseproject.BuildConfig
import com.app.baseproject.BuildConfig.BASE_URL
import com.app.baseproject.data.model.request.PostData
import com.app.baseproject.data.model.request.PostToken
import com.app.baseproject.data.model.response.ResponRegister
import com.app.baseproject.data.model.response.ResponseLogin
import com.app.baseproject.data.model.response.ResponseSetupData
import com.app.baseproject.utils.rx.RxErrorHandlingCallAdapterFactory
import com.google.gson.GsonBuilder
import com.orhanobut.hawk.Hawk
import com.readystatesoftware.chuck.ChuckInterceptor
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*


interface RestService {
    companion object {
        val ENDPOINT = Hawk.get("baseUrl") ?: BASE_URL
    }

    /**
     * Request data for login
     */
    // Default
//    @POST
//    fun login(
//        @Url url: String?,
//        @Body data: PostData
//    ): Observable<ResponseLogin>

    @POST
    fun login(
        @Url url: String?,
        @Body user: PostData,
    ): Observable<ResponseLogin>

    @POST
    fun importDb(
        @Url url: String,
        @Header("Authorization") tokenHeader: String,
        @Body token: PostToken,
    ): Observable<ResponseSetupData>

    @POST
    fun register(
        @Url url: String?,
        @Body user: PostData,
    ): Observable<ResponRegister>

    object Creator {
        fun newRestService(context: Context): RestService {
            val gson =
                GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").setLenient().create()
            val httpClientBuilder = OkHttpClient().newBuilder()

            val logging = HttpLoggingInterceptor()
            logging.level =
                if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else
                    HttpLoggingInterceptor.Level.NONE
            httpClientBuilder.addInterceptor(logging).build()

            httpClientBuilder.addInterceptor(Interceptor())
            httpClientBuilder.addInterceptor(ChuckInterceptor(context))
            httpClientBuilder.connectTimeout(100, TimeUnit.SECONDS)
            httpClientBuilder.readTimeout(100L, TimeUnit.SECONDS)
            httpClientBuilder.writeTimeout(100L, TimeUnit.SECONDS)

            val trustAllCerts: Array<TrustManager> = arrayOf(
                object : X509TrustManager {
                    override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {

                    }

                    override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {

                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )

            // Install the all-trusting trust manager
            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory

            httpClientBuilder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            httpClientBuilder.hostnameVerifier { p0, p1 -> true }

            val retrofit = Retrofit.Builder().baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .callFactory(httpClientBuilder.build())
                .build()

            return retrofit.create(RestService::class.java)
        }
    }
}