package com.app.baseproject.base

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.baseproject.BaseApplication
import com.app.baseproject.injection.component.ConfigPersistentComponent
import com.app.baseproject.injection.component.DaggerConfigPersistentComponent
import com.app.baseproject.injection.component.FragmentComponent
import com.app.baseproject.injection.module.FragmentModule

import java.util.HashMap
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by test(test@gmail.com) on 10/16/16.
 */

abstract class BaseFragment : Fragment() {

    protected var mActivity: Activity? = null
    var mFragmentComponent: FragmentComponent? = null
    private var mFragmentId: Long = 0

    @get:LayoutRes
    protected abstract val layout: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Create the ActivityComponent and reuses cached ConfigPersistentComponent if this is
        // being called after a configuration change.
        mFragmentId = savedInstanceState?.getLong(KEY_FRAGMENT_ID) ?: NEXT_ID.getAndIncrement()

        var configPersistentComponent = sComponentsMap[mFragmentId]


        if (configPersistentComponent == null) {
            Log.e("$mFragmentId","Clearing Fragment ConfigPersistentComponent id=%d")
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .appComponent(BaseApplication.get(requireContext()).getComponent())
                    .build()
            sComponentsMap[mFragmentId] = configPersistentComponent!!
        }
        mFragmentComponent = configPersistentComponent.fragmentComponent(FragmentModule(requireActivity()))

        mActivity = activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(layout, container, false)
    }

    fun fragmentComponent(): FragmentComponent? {
        return mFragmentComponent
    }

    override fun onDestroy() {
        Log.e("$mFragmentId","Clearing Fragment ConfigPersistentComponent id=%d")
        sComponentsMap.remove(mFragmentId)
        super.onDestroy()
    }

    companion object {

        private val KEY_FRAGMENT_ID = "KEY_FRAGMENT_ID"
        private val NEXT_ID = AtomicLong(0)
        private val sComponentsMap = HashMap<Long, ConfigPersistentComponent>()
    }
}
