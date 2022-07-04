package com.app.baseproject.base

interface MvpView {
    fun onDismissLoading()
    fun onShowLoading()
    fun onFailed(message: String?, rc: String?)
}