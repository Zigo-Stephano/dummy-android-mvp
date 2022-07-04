package com.app.baseproject.ui.main.home

import android.content.Context
import com.app.baseproject.base.BasePresenter
import com.app.baseproject.data.DataManager
import com.app.baseproject.data.local.DataDatabase
import com.app.baseproject.data.local.Preferences
import com.app.baseproject.data.model.local.User
import com.app.baseproject.utils.rx.RxUtil
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class HomePresenter @Inject
constructor(dataManager: DataManager, preferences: Preferences): BasePresenter<HomeView>(){

    private var mDisposable: Disposable? = null

    private var mPreferences: Preferences? = null
    private var mDatabase: DataDatabase? = null
    private lateinit var context: Context

    init {
        mDataManager = dataManager
        mPreferences = preferences
    }

    override fun detachView() {
        super.detachView()
        mDisposable!!.dispose()
    }

    /** Initialize Room DB */
    fun attachRoom(context: Context) {
        this.context = context
        mDatabase = DataDatabase.getInstance(context)
    }

    fun getAllUser() {
        checkViewAttached()
        mvpView?.onShowLoading()
        RxUtil.dispose(mDisposable)
        mDisposable = mDatabase?.dataDao()?.getAllUser()
            ?.compose(RxUtil.applySchedulersIoFlowable())
            ?.subscribe({
                if (it != null) {
                    mvpView?.showAllData(it)
                    mvpView?.onDismissLoading()
                } else {
                    mvpView?.onFailGetData()
                    mvpView?.onDismissLoading()
                }
            }, {
                mvpView?.onDismissLoading()
            })
    }

    fun deleteData(data: User){
        checkViewAttached()
        mvpView?.onShowLoading()
        RxUtil.dispose(mDisposable)
        mDisposable = Flowable.fromCallable { mDatabase?.dataDao()?.deleteUser(data) }
            .compose(RxUtil.applySchedulersIoFlowable())
            .subscribe({
                mvpView?.onSuccessDeleteData()
                mvpView?.onDismissLoading()
            }, {
                mvpView?.onFailedDeleteData()
                mvpView?.onDismissLoading()})

    }
}