package com.app.baseproject.ui.main.home.editUser

import com.app.baseproject.base.MvpView
import com.app.baseproject.data.model.local.User

interface EditUserView: MvpView {
    fun onSuccessGetData(user: User)
    fun onFailedGetData()
    fun onSuccessUpdateData()
    fun onFailedUpdateData()
}