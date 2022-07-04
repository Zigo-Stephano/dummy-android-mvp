package com.app.baseproject.ui.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.app.baseproject.R
import com.app.baseproject.base.BaseActivity
import com.app.baseproject.data.local.Preferences
import com.app.baseproject.data.model.request.PostData
import com.app.baseproject.data.model.response.ResponseLogin
import com.app.baseproject.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginView {

    @Inject
    lateinit var loginPresenter: LoginPresenter

    @Inject
    lateinit var preferences: Preferences

    @Suppress("DEPRECATION")
    private var mProgressDialog: ProgressDialog? = null

    private lateinit var user: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent()?.inject(this)
        setContentView(R.layout.activity_login)
        loginPresenter.attachView(this )
        mProgressDialog = ProgressDialog(this)

        initListener()
    }

    private fun initListener() {
        ivClose.setOnClickListener {
            onBackPressed()
        }
        btnLogin.setOnClickListener {
            user = etUser.text.toString()
            password = etPassword.text.toString()
            when {
                user.isEmpty() || user == "" -> {
                    Toast.makeText(this, "Silakan masukkan nama atau email anda", Toast.LENGTH_SHORT).show()
                }
                password.isEmpty() || password == "" -> {
                    Toast.makeText(this, "Silakan masukkan password anda", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val data = PostData()
                    data.user = user
                    data.password = password
                    loginPresenter.postLogin(data)
                }
            }
        }
    }

    override fun onSuccessLogin(data: ResponseLogin) {
        val getDate = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        getDate.add(Calendar.DATE, 1)
        val date = dateFormat.format(getDate.time)
        val dateExpired = dateFormat.parse(date)
        preferences.saveExpired(dateExpired)
        preferences.saveToken(data.token)
        Toast.makeText(this, "Success login ", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onFailedLogin() {
        Toast.makeText(this, "Fail login", Toast.LENGTH_SHORT).show()
    }

    override fun showPostData(data: PostData) {
        Toast.makeText(this, "Postdata = $data", Toast.LENGTH_SHORT).show()
    }

    override fun onDismissLoading() {
        mProgressDialog?.isShowing?.let {
            mProgressDialog?.dismiss()
        }
    }

    override fun onShowLoading() {
        @Suppress("DEPRECATION")
        if (!mProgressDialog?.isShowing!!) {
            mProgressDialog?.setMessage("Loading...")
            mProgressDialog?.setCancelable(true)
            mProgressDialog?.show()
        }
    }

    override fun onFailed(message: String?, rc: String?) {
        TODO("Not yet implemented")
    }
}