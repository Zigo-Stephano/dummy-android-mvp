package com.app.baseproject.ui.main.account

import com.app.baseproject.base.BasePresenter
import com.app.baseproject.data.DataManager
import com.app.baseproject.data.local.Preferences
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class AccountPresenter @Inject
constructor(dataManager: DataManager, preferences: Preferences) : BasePresenter<AccountView>(){

    private val mDisposable: Disposable? = null

    private var mPreferences: Preferences? = null

    init {
        mDataManager = dataManager
        mPreferences = preferences
    }

    override fun detachView() {
        super.detachView()
        if (mDisposable != null) mDisposable!!.dispose()
    }
}