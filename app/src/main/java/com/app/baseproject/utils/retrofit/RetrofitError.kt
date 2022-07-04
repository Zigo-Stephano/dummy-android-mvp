package com.app.baseproject.utils.retrofit

import com.app.baseproject.base.MvpView
import com.app.baseproject.data.model.ApiResponse

/**
 * Created by test(test@gmail.com) on 12/4/16.
 */

object RetrofitError {

    fun handlerErrorPresenter(mvpView: MvpView, throwable: Throwable?) {
        val message = handleError(throwable)
        println("value_throwable_response : $throwable")
        message?.let {
            mvpView.onFailed(it, null)
        }
    }

    fun checkedError(throwable: Throwable?): Boolean{
        return !throwable?.message?.contains("Connection reset", ignoreCase = true)!!
    }

    private fun handleError(e: Throwable?): String? {
        return try {
            val error = e as RetrofitException
            val response = error.getErrorBodyAs(ApiResponse::class.java)

            if (response != null) {
                if (response.response_code == 401) {
                    "RC_SESSION_OUT"
                } else {
                    response.message
                }
            } else {
                println("error_message : ${e.message}")
                "Network Error, Please Try Again"
            }
        } catch (ex: Exception) {
            e?.message
        }

    }

//    private fun isOnline(): Boolean {
//        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val networkInfo = connectivityManager.activeNetworkInfo
//        return networkInfo != null && networkInfo.isConnected
//    }
}

