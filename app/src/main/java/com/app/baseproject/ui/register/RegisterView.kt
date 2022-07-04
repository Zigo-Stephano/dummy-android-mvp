package com.app.baseproject.ui.register

import com.app.baseproject.base.MvpView
import com.app.baseproject.data.model.request.PostData
import com.app.baseproject.data.model.response.ResponRegister

interface RegisterView: MvpView {
    fun onSuccessRegister(data: PostData)
    fun onFailedRegister()
}