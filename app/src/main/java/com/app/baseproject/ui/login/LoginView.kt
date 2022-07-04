package com.app.baseproject.ui.login

import com.app.baseproject.base.MvpView
import com.app.baseproject.data.model.request.PostData
import com.app.baseproject.data.model.response.ResponseLogin

interface LoginView: MvpView {
    fun onSuccessLogin(data: ResponseLogin)
    fun onFailedLogin()
    fun showPostData(data: PostData)
}