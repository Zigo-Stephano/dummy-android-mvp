package com.app.baseproject.ui.splash

import android.content.Intent
import android.os.Bundle
import com.app.baseproject.BuildConfig.BASE_URL
import com.app.baseproject.R
import com.app.baseproject.base.BaseActivity
import com.app.baseproject.data.local.Preferences
import com.app.baseproject.ui.main.MainActivity
import com.app.baseproject.ui.register.RegisterActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity() {

    private val baseUrl = BASE_URL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent()?.inject(this)
        setContentView(R.layout.activity_splash)
        initView()
    }

    private fun initView() {
        Preferences().saveToken(baseUrl)
        GlobalScope.launch {
            delay(3_000)
            when {
                Preferences().getToken()!!.isNullOrEmpty() || Preferences().getToken() == "" -> {
                    val intent = Intent(this@SplashActivity, RegisterActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                else -> {
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
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
}