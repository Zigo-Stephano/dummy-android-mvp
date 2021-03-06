package com.app.baseproject.injection.component

import com.app.baseproject.injection.ConfigPersistent
import com.app.baseproject.injection.module.ActivityModule
import com.app.baseproject.injection.module.FragmentModule
import dagger.Component

/**
 * A dagger component that will live during the lifecycle of an Activity but it won't
 * be destroy during configuration changes. Check [] to see how this components
 * survives configuration changes.
 * Use the [ConfigPersistent] scope to annotate dependencies that need to survive
 * configuration changes (for example Presenters).
 */
@ConfigPersistent
@Component(dependencies = [AppComponent::class])
interface ConfigPersistentComponent {
    fun activityComponent(activityModule: ActivityModule): ActivityComponent
    fun fragmentComponent(fragmentModule: FragmentModule): FragmentComponent
}
