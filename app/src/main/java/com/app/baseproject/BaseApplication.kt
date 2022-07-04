package com.app.baseproject

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.app.baseproject.injection.component.AppComponent
import com.app.baseproject.injection.component.DaggerAppComponent
import com.app.baseproject.injection.module.AppModule
import com.orhanobut.hawk.Hawk


/**
 * Created by Arif Hanafiah on 5/9/22.
 * arifhanafiah666@gmail.com
 */
class BaseApplication: Application() {

    private var mApplicationComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        Hawk.init(this).build()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    fun getComponent(): AppComponent? {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        }
        return mApplicationComponent
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        lateinit var instance: BaseApplication
        operator fun get(context: Context): BaseApplication {
            return context.applicationContext as BaseApplication
        }
    }
}