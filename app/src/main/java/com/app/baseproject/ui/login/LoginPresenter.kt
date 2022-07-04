package com.app.baseproject.ui.login

import android.util.Log
import com.app.baseproject.base.BasePresenter
import com.app.baseproject.data.DataManager
import com.app.baseproject.data.local.Preferences
import com.app.baseproject.data.model.request.PostData
import com.app.baseproject.utils.retrofit.RetrofitError
import com.app.baseproject.utils.rx.RxUtil
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class LoginPresenter @Inject
constructor(dataManager: DataManager, preferences: Preferences) : BasePresenter<LoginView>() {
    private var mDisposable: Disposable? = null

    private var mPreferences: Preferences? = null

    init {
        mDataManager = dataManager
        mPreferences = preferences
    }

    override fun detachView() {
        super.detachView()
        mDisposable?.dispose()
    }

    fun postLogin(user: PostData) {
        checkViewAttached()
        mvpView?.onShowLoading()
        RxUtil.dispose(mDisposable)
        mDisposable = mDataManager!!.login(user)
            .compose(RxUtil.applySchedulersIo())
            .doOnTerminate { mvpView?.onDismissLoading() }
            .subscribe({ dataResponse ->
                when {
                    dataResponse.code.equals("200") -> {
                        mvpView?.onSuccessLogin(dataResponse)
                    }
                    else -> {
                        mvpView?.onFailedLogin()
                    }
                }
            }, { throwable ->
                RetrofitError.handlerErrorPresenter(this.mvpView!!, throwable)
            })
    }

}