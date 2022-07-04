package com.app.baseproject.injection.component

import android.app.Application
import android.content.Context
import com.app.baseproject.data.DataManager
import com.app.baseproject.data.local.Preferences
import com.app.baseproject.injection.ApplicationContext
import com.app.baseproject.injection.module.AppModule
import com.app.baseproject.utils.rx.RxEventBus
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    //    fun inject(app: Application)
    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun dataManager(): DataManager

    fun eventBus(): RxEventBus

    fun preferences(): Preferences
}