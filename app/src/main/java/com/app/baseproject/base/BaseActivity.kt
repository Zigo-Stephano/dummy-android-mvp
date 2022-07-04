package com.app.baseproject.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.collection.LongSparseArray
import androidx.appcompat.app.AppCompatActivity
import com.app.baseproject.BaseApplication
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import com.app.baseproject.injection.component.ActivityComponent
import com.app.baseproject.injection.component.ConfigPersistentComponent
import com.app.baseproject.injection.component.DaggerConfigPersistentComponent
import com.app.baseproject.injection.module.ActivityModule
import com.orhanobut.hawk.Hawk
import java.util.*

import java.util.concurrent.atomic.AtomicLong

/**
 * Abstract activity that every other Activity in this application must implement. It handles
 * creation of Dagger components and makes sure that instances of ConfigPersistentComponent survive
 * across configuration changes.
 */
abstract class BaseActivity : AppCompatActivity(), MvpView {

    private var mActivityComponent: ActivityComponent? = null
    private var mActivityId: Long = 0

    override fun attachBaseContext(newBase: Context?) {
        try {
            newBase?.let {
                super.attachBaseContext(ViewPumpContextWrapper.wrap(it))
            }
        }catch (e: Exception) {
            Log.d("view_pump", e.message.toString())
        }
    }

    fun setupLanguage() {

        val language: String? = Hawk.get("language")

        try {
            val config = resources.configuration
            val lang = if (language == "1") "en" else "in" // your language code
            val locale = Locale(lang)
            Locale.setDefault(locale)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                config.setLocale(locale)
            else
                config.locale = locale

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                createConfigurationContext(config)
            resources.updateConfiguration(config, resources.displayMetrics)
        } catch (e: Exception) {
            Log.d("error_language", e.message.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create the ActivityComponent and reuses cached ConfigPersistentComponent if this is
        // being called after a configuration change.
        mActivityId = savedInstanceState?.getLong(KEY_ACTIVITY_ID) ?: NEXT_ID.getAndIncrement()

        var configPersistentComponent: ConfigPersistentComponent? = sComponentsMap.get(mActivityId, null)

        if (configPersistentComponent == null) {
            Log.e("$mActivityId","Clearing ConfigPersistentComponent id=%d")
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .appComponent(BaseApplication.get(this).getComponent())
                    .build()
            sComponentsMap.put(mActivityId, configPersistentComponent)
        }
        mActivityComponent = configPersistentComponent?.activityComponent(ActivityModule(this))
        setupLanguage()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_ACTIVITY_ID, mActivityId)
    }

    override fun onDestroy() {
        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                !isChangingConfigurations
            } else {
                isChangingConfigurations
            }
        ) {
            Log.e("$mActivityId","Clearing ConfigPersistentComponent id=%d")
            sComponentsMap.remove(mActivityId)
        }
        super.onDestroy()
    }

    fun activityComponent(): ActivityComponent? {
        return mActivityComponent
    }

    companion object {

        private val KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID"
        private val NEXT_ID = AtomicLong(0)
        private val sComponentsMap =
            LongSparseArray<ConfigPersistentComponent>()
    }
}
