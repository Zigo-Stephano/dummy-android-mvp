package com.app.baseproject.data.remote

import android.annotation.SuppressLint
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer

class Interceptor : Interceptor {
    @SuppressLint("SimpleDateFormat")
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val method = original.method()

        val requestBuilder = original.newBuilder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json; charset=utf-8")
                .header("X-Auth-Key", "apiid")
                .method(method, original.body())

//        val token = Hawk.get("token", "")
//
//        if (token != null && token != "") {
//            requestBuilder.addHeader("Authorization", "Bearer " + Hawk.get("token"))
//
//            val timeStamp = GeneralMethod.getCurrentDate()
//            val signature = "${original.method()}:" + "/index.php:" + "${token}:" + "${GeneralMethod.hash256(original.getBodyAsString())}:" + timeStamp
//            val signatureEncrypt = Encrypt.hmacSha("apikey", signature, "HmacSHA256")
//            Log.d("request_header", "body_request : ${original.getBodyAsString()}")
//            Log.d("request_header", "time_stamp : $timeStamp")
//            Log.d("request_header", "signature : $signature")
//            Log.d("request_header", "signature_encrypt : $signatureEncrypt")
//            requestBuilder.addHeader("X-Auth-Timestamp", timeStamp)
//            requestBuilder.addHeader("X-Auth-Signature", signatureEncrypt)
//        }

        val request = requestBuilder.build()

        return chain.proceed(request)
    }

    private fun Request.getBodyAsString(): String {
        val requestCopy = this.newBuilder().build()
        val buffer = Buffer()
        requestCopy.body()?.writeTo(buffer)
        return buffer.readUtf8()
    }

}