package com.app.baseproject.ui.main.task

import com.app.baseproject.base.MvpView
import com.app.baseproject.data.model.local.User

interface TaskView: MvpView {
    fun onSuccessAddNewUser()
    fun onFailedAddNewUser()
    fun onSuccessGetData(data: List<User>)
    fun onFailGetData()
    fun onSuccessImportDb()
    fun onFailImportDb()
}