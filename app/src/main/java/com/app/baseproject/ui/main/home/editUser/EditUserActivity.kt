package com.app.baseproject.ui.main.home.editUser

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.baseproject.R
import com.app.baseproject.base.BaseActivity
import com.app.baseproject.data.local.Preferences
import com.app.baseproject.data.model.local.User
import kotlinx.android.synthetic.main.activity_edit_user.*
import javax.inject.Inject

class EditUserActivity : BaseActivity(), EditUserView {

    @Inject
    lateinit var presenter: EditUserPresenter

    @Inject
    lateinit var preferences: Preferences

    @Suppress("DEPRECATION")
    private var mProgressDialog: ProgressDialog? = null

    private var userId: Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent()?.inject(this)
        setContentView(R.layout.activity_edit_user)
        mProgressDialog = ProgressDialog(this)
        presenter.attachView(this)
        presenter.attachRoom(this)
        userId = intent.getIntExtra("user_id", 0)
        presenter.getData(userId)
        initListener()
    }

    private fun initListener() {
        btnSaveEditUser.setOnClickListener {
            when {
                etEditNameUser.text.toString() == "" -> {
                    Toast.makeText(this, "Nama harus diisi", Toast.LENGTH_SHORT).show()
                }
                etEditEmailUser.text.toString() == "" -> {
                    Toast.makeText(this, "Email harus diisi", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val user = User(userId, etEditNameUser.text.toString(), etEditEmailUser.text.toString())
                    presenter.saveUpdatedData(user)
                }
            }
        }
    }

    override fun onSuccessGetData(data: User) {
        etEditNameUser.hint = data.name
        etEditEmailUser.hint = data.email
    }

    override fun onFailedGetData() {
        Toast.makeText(this, "Fail to get data", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessUpdateData() {
        Toast.makeText(this, "Success update data", Toast.LENGTH_SHORT).show()
        onBackPressed()
    }

    override fun onFailedUpdateData() {
        Toast.makeText(this, "Fail update data", Toast.LENGTH_SHORT).show()
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