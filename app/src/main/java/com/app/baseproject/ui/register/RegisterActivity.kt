package com.app.baseproject.ui.register

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.app.baseproject.R
import com.app.baseproject.base.BaseActivity
import com.app.baseproject.data.local.Preferences
import com.app.baseproject.data.model.request.PostData
import com.app.baseproject.data.model.response.ResponseLogin
import com.app.baseproject.ui.login.LoginActivity
import com.app.baseproject.ui.login.LoginPresenter
import com.app.baseproject.ui.login.LoginView
import com.app.baseproject.ui.main.MainActivity
import com.example.awesomedialog.*
import kotlinx.android.synthetic.main.activity_register.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class RegisterActivity : BaseActivity(), RegisterView, LoginView {

    @Inject
    lateinit var registerPresenter: RegisterPresenter

    @Inject
    lateinit var preferences: Preferences

    @Inject
    lateinit var loginPresenter: LoginPresenter

    @Suppress("DEPRECATION")
    private var mProgressDialog: ProgressDialog? = null

    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent()?.inject(this)
        setContentView(R.layout.activity_register)
        registerPresenter.attachView(this)
        loginPresenter.attachView(this)
        mProgressDialog = ProgressDialog(this)

        initListener()
    }

    private fun initListener() {
        btnRegis.setOnClickListener {
            name = etName.text.toString()
            email = etEmail.text.toString()
            password = etPassword.text.toString()
            when {
                name == "" -> {
                    Toast.makeText(this, "Silakan masukkan nama anda", Toast.LENGTH_SHORT).show()
                }
                email == "" -> {
                    Toast.makeText(this, "Silakan masukkan email address anda", Toast.LENGTH_SHORT).show()
                }
                password == "" -> {
                    Toast.makeText(this, "Silakan masukkan password anda", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val data = PostData()
                    data.name = name
                    data.email = email
                    data.password = password
                    registerPresenter.postRegis(data)
                }
            }
        }
        tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSuccessRegister(data: PostData) {
        AwesomeDialog.build(this)
            .position(AwesomeDialog.POSITIONS.BOTTOM)
            .title("Pemberitahuan")
            .body("Pendaftaran telah berhasil")
            .onNegative("Saya login sendiri", buttonBackgroundColor = R.drawable.bg_btn_cancel)
            .onPositive("Silakan login disini", buttonBackgroundColor = R.drawable.bg_btn_standard) {
//                val intent = Intent(this, LoginActivity::class.java)
//                startActivity(intent)
                data.user = data.name
                loginPresenter.postLogin(data)
            }
    }

    override fun onFailedRegister() {
        Toast.makeText(this, "Fail register", Toast.LENGTH_SHORT).show()
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
        TODO("Not yet implemented")
    }

    override fun onDismissLoading(){
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