package com.app.baseproject.ui.main.home.editUser

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

class EditUserPresenter @Inject
constructor(dataManager: DataManager, preferences: Preferences) : BasePresenter<EditUserView>(){
    private var mDisposable: Disposable? = null
    private var mDisposable1: Disposable? = null

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
        mDisposable1!!.dispose()
    }

    /** Initialize Room DB */
    fun attachRoom(context: Context) {
        this.context = context
        mDatabase = DataDatabase.getInstance(context)
    }

    fun getData(userId: Int){
        checkViewAttached()
        mvpView?.onShowLoading()
        RxUtil.dispose(mDisposable)
        mDisposable = mDatabase?.dataDao()?.getUser(userId)
            ?.compose(RxUtil.applySchedulersIoFlowable())
            ?.subscribe({
                mvpView?.onSuccessGetData(it)
                mvpView?.onDismissLoading()
            },{
                mvpView?.onFailedGetData()
                mvpView?.onDismissLoading()
            })
    }

    fun saveUpdatedData(data: User){
        checkViewAttached()
        mvpView?.onShowLoading()
        RxUtil.dispose(mDisposable1)
        mDisposable1 = Flowable.fromCallable { mDatabase?.dataDao()?.updateUser(data) }
            .compose(RxUtil.applySchedulersIoFlowable())
            ?.subscribe({
                mvpView?.onSuccessUpdateData()
                mvpView?.onDismissLoading()
            },{
                mvpView?.onFailedUpdateData()
                mvpView?.onDismissLoading()
            })
    }
}