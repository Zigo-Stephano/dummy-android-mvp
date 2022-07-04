package com.app.baseproject.ui.main.home

import com.app.baseproject.base.MvpView
import com.app.baseproject.data.model.local.User

interface HomeView: MvpView {
    fun onFailGetData()
    fun showAllData(data: List<User>)
    fun onSuccessDeleteData()
    fun onFailedDeleteData()
}