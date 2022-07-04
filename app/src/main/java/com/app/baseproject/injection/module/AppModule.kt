package com.app.baseproject.injection.module

import android.app.Application
import android.content.Context
import com.app.baseproject.data.remote.RestService
import com.app.baseproject.injection.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {

    @Provides
    fun provideApplication() = app

    @Provides
    @ApplicationContext
    fun provideContext(): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideAppService(): RestService {
        return RestService.Creator.newRestService(provideContext())
    }
}