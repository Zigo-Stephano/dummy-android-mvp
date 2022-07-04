package com.app.baseproject.ui.main.task

import android.content.Context
import com.app.baseproject.base.BasePresenter
import com.app.baseproject.data.DataManager
import com.app.baseproject.data.local.DataDatabase
import com.app.baseproject.data.local.Preferences
import com.app.baseproject.data.model.local.User
import com.app.baseproject.data.model.request.PostToken
import com.app.baseproject.utils.retrofit.RetrofitError
import com.app.baseproject.utils.rx.RxUtil
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class TaskPresenter @Inject
constructor(dataManager: DataManager, preferences: Preferences) : BasePresenter<TaskView>(){

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

    fun addNewUser(data: User) {
        checkViewAttached()
        mvpView?.onShowLoading()
        RxUtil.dispose(mDisposable)
        mDisposable = Flowable.fromCallable{ mDatabase?.dataDao()?.addUser(data) }
            .compose(RxUtil.applySchedulersIoFlowable())
            .doOnTerminate { mvpView?.onDismissLoading() }
            .subscribe({
                mvpView?.onSuccessAddNewUser()
            }, {
                mvpView?.onFailedAddNewUser()
            })
    }

    fun getSetupData(tokenHeader: String, token: PostToken){
        checkViewAttached()
        mvpView?.onShowLoading()
        RxUtil.dispose(mDisposable)
        mDisposable = mDataManager!!.importDb(tokenHeader, token)
            .compose(RxUtil.applySchedulersIo())
            .subscribe({
                mvpView?.onSuccessGetData(it.data!!)
                mvpView?.onDismissLoading()
            }, {
                mvpView?.onFailGetData()
                mvpView?.onDismissLoading()
            })
    }

    fun importDb(data: List<User>){
        checkViewAttached()
        mvpView?.onShowLoading()
        RxUtil.dispose(mDisposable)
        mDisposable = Flowable.fromCallable { mDatabase?.dataDao()?.deleteAllUser() }
            .compose(RxUtil.applySchedulersIoFlowable())
            .switchMap{
                Flowable.fromCallable { mDatabase?.dataDao()?.addAllUser(data) }
                    .compose(RxUtil.applySchedulersIoFlowable())
            }
            .subscribe({
                mvpView?.onSuccessImportDb()
                mvpView?.onDismissLoading()
            }, {
                mvpView?.onFailImportDb()
                mvpView?.onDismissLoading()
            })
    }
}