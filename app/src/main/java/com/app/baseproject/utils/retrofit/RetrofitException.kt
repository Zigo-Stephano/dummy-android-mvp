package com.app.baseproject.utils.retrofit

import java.io.IOException

import retrofit2.Response
import retrofit2.Retrofit

/**
 * Created by test(test@gmail.com) on 12/4/16.
 */

class RetrofitException internal constructor(message: String,
                                             /**
                                              * The request URL which produced the error.
                                              */
                                             val url: String?,
                                             /**
                                              * Response object containing status code, headers, body, etc.
                                              */
                                             val response: Response<*>?,
                                             /**
                                              * The event kind which triggered this error.
                                              */
                                             val kind: Kind, exception: Throwable?,
                                             /**
                                              * The Retrofit this request was executed on
                                              */
                                             val retrofit: Retrofit?) : RuntimeException(message, exception) {

    /**
     * HTTP response body converted to specified `type`. `null` if there is no
     * response.
     *
     * @throws IOException if unable to convert the body to the specified `type`.
     */
//    @Throws(IOException::class)
    fun <T> getErrorBodyAs(type: Class<T>): T? {
        if (response?.errorBody() == null) {
            return null
        }
        val converter = retrofit?.responseBodyConverter<T>(type, arrayOfNulls(0))
        return converter?.convert(response.errorBody()!!)
    }

    /**
     * Identifies the event kind which triggered a [RetrofitException].
     */
    enum class Kind {
        /**
         * An [IOException] occurred while communicating to the server.
         */
        NETWORK,
        /**
         * A non-200 HTTP status code was received from the server.
         */
        HTTP,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }

    companion object {

        fun httpError(url: String, response: Response<*>, retrofit: Retrofit): RetrofitException {
            val message = response.code().toString() + " " + response.message()
            return RetrofitException(
                message,
                url,
                response,
                Kind.HTTP,
                null,
                retrofit
            )
        }

        fun networkError(exception: IOException): RetrofitException {
            return RetrofitException(
                exception.message.toString(),
                null,
                null,
                Kind.NETWORK,
                exception,
                null
            )
        }

        fun unexpectedError(exception: Throwable): RetrofitException {
            return RetrofitException(
                exception.message.toString(),
                null,
                null,
                Kind.UNEXPECTED,
                exception,
                null
            )
        }
    }
}
