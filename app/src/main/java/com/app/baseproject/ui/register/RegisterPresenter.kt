package com.app.baseproject.ui.register

import com.app.baseproject.base.BasePresenter
import com.app.baseproject.data.DataManager
import com.app.baseproject.data.local.Preferences
import com.app.baseproject.data.model.request.PostData
import com.app.baseproject.utils.retrofit.RetrofitError
import com.app.baseproject.utils.rx.RxUtil
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class RegisterPresenter @Inject
constructor(dataManager: DataManager, preferences: Preferences) : BasePresenter<RegisterView>(){
    private var mDisposable: Disposable? = null

    private var mPreferences: Preferences? = null

    init {
        mDataManager = dataManager
        mPreferences = preferences
    }

    override fun detachView() {
        super.detachView()
        mDisposable!!.dispose()
    }

    fun postRegis(data: PostData){
        checkViewAttached()
        mvpView?.onShowLoading()
        RxUtil.dispose(mDisposable)
        mDisposable = mDataManager!!.register(data)
            .compose(RxUtil.applySchedulersIo())
            .doOnTerminate {
                mvpView?.onDismissLoading()
            }
            .subscribe({ dataResponse ->
                when {
                    dataResponse.code == "201" -> {
                        mvpView?.onSuccessRegister(data)
                    }
                    else -> {
                        mvpView?.onFailedRegister()
                    }
                }

            }, { throwable ->
                RetrofitError.handlerErrorPresenter(this.mvpView!!, throwable)
            })
    }

    fun stopLoading() {
        mvpView?.onDismissLoading()
    }
}