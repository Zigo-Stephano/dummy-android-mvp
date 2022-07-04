package com.app.baseproject.ui.main

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.app.baseproject.R
import com.app.baseproject.base.BaseActivity
import com.app.baseproject.data.local.Preferences
import com.app.baseproject.ui.main.account.AccountFragment
import com.app.baseproject.ui.main.home.HomeFragment
import com.app.baseproject.ui.main.task.TaskFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    companion object {
        const val MENU_HOME = 1
        const val MENU_TASK = 2
        const val MENU_ACCOUNT = 3

        var fragment: Fragment? = null
    }

    @Inject
    lateinit var preferences: Preferences

    @Suppress("DEPRECATION")
    private var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent()?.inject(this)
        setContentView(R.layout.activity_main)
        mProgressDialog = ProgressDialog(this)
        fragmentView(1)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> {
                    fragmentView(MENU_HOME)
                }
                R.id.task -> {
                    fragmentView(MENU_TASK)
                }
                R.id.account -> {
                    fragmentView(MENU_ACCOUNT)
                }
            }
            true
        }
    }

    override fun onDismissLoading() {
        TODO("Not yet implemented")
    }

    override fun onShowLoading() {
        TODO("Not yet implemented")
    }

    override fun onFailed(message: String?, rc: String?) {
        TODO("Not yet implemented")
    }

    fun pindahTask(){
        bottomNavigationView.selectedItemId = R.id.task
        fragmentView(MENU_TASK)
    }

    private fun fragmentView(position: Int) {
        when(position) {
            MENU_HOME -> {
                fragment = HomeFragment()
            }
            MENU_TASK -> {
                fragment = TaskFragment()
            }
            MENU_ACCOUNT -> {
                fragment = AccountFragment()
            }
        }

        if (fragment != null) {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
//            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            fragmentTransaction.replace(R.id.frameLayout, fragment!!)
            fragmentTransaction.commit()
        }
    }
}