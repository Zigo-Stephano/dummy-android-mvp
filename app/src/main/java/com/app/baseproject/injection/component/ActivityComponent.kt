package com.app.baseproject.injection.component

import com.app.baseproject.injection.PerActivity
import com.app.baseproject.injection.module.ActivityModule
import com.app.baseproject.ui.login.LoginActivity
import com.app.baseproject.ui.main.MainActivity
import com.app.baseproject.ui.main.home.editUser.EditUserActivity
import com.app.baseproject.ui.register.RegisterActivity
import com.app.baseproject.ui.splash.SplashActivity
import dagger.Subcomponent

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(registerActivity: RegisterActivity)
    fun inject(splashActivity: SplashActivity)
    fun inject(editUserActivity: EditUserActivity)

}
