package com.app.baseproject.injection.component

import com.app.baseproject.injection.PerFragment
import com.app.baseproject.injection.module.FragmentModule
import com.app.baseproject.ui.main.account.AccountFragment
import com.app.baseproject.ui.main.home.HomeFragment
import com.app.baseproject.ui.main.task.TaskFragment
import dagger.Subcomponent

/**
 * This component inject dependencies to all Activities across the application
 */
@PerFragment
@Subcomponent(modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(homeFragment: HomeFragment)
    fun inject(taskFragment: TaskFragment)
    fun inject(accountFragment: AccountFragment)
}
