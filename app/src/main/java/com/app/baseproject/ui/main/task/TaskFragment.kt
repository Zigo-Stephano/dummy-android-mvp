package com.app.baseproject.ui.main.task

import android.app.Dialog
import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.baseproject.R
import com.app.baseproject.base.BaseFragment
import com.app.baseproject.data.local.Preferences
import com.app.baseproject.data.model.local.User
import com.app.baseproject.data.model.request.PostToken
import com.app.baseproject.ui.main.MainActivity
import com.example.awesomedialog.*
import kotlinx.android.synthetic.main.fragment_task.*
import kotlinx.android.synthetic.main.layout_input_token.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskFragment : BaseFragment(), TaskView {

    @Inject
    lateinit var presenter: TaskPresenter

    @Inject
    lateinit var preferences: Preferences

    private lateinit var dialogToken: Dialog

    @Suppress("DEPRECATION")
    private var mProgressDialog: ProgressDialog? = null

    override val layout: Int
        get() = R.layout.fragment_task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentComponent()?.inject(this)
        mProgressDialog = ProgressDialog(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        mActivity?.let { presenter.attachRoom(it) }
        initListener()
    }

    private fun initListener() {
        btnAddNewUser.setOnClickListener {
            when {
                etNameNewUser.text.toString() == "" -> {
                    Toast.makeText(requireContext(), "Nama harus diisi", Toast.LENGTH_SHORT).show()
                }
                etEmailNewUser.text.toString() == "" -> {
                    Toast.makeText(requireContext(), "Email harus diisi", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val data = User(0, etNameNewUser.text.toString(), etEmailNewUser.text.toString())
                    presenter.addNewUser(data)
                }
            }
        }
        btnGetSetupData.setOnClickListener {
            showDialogToken()
        }
    }

    fun showDialogToken() {
        // Setup dialog
        dialogToken = Dialog(requireActivity())
        dialogToken.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogToken.setContentView(R.layout.layout_input_token)
        dialogToken.setCancelable(false)
        dialogToken.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogToken.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        dialogToken.btnSubmit.setOnClickListener {
            val tokenSetup = dialogToken.etToken.text.toString()
            val data = PostToken(tokenSetup)
            presenter.getSetupData(preferences.getToken().toString(), data)
            dialogToken.dismiss()
        }

        // If cancel button was clicked
        dialogToken.btnCancelToken.setOnClickListener {
            // Dismiss input token dialog
            dialogToken.dismiss()
        }

        // Showing input token dialog
        dialogToken.show()
    }

    override fun onSuccessAddNewUser() {
        Toast.makeText(requireContext(), "Success add new user", Toast.LENGTH_SHORT).show()
        etNameNewUser.text.clear()
        etEmailNewUser.text.clear()
    }

    override fun onFailedAddNewUser() {
        Toast.makeText(requireContext(), "Failed add new user", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessGetData(data: List<User>) {
        AwesomeDialog.build(requireActivity())
            .position(AwesomeDialog.POSITIONS.BOTTOM)
            .title("Pemberitahuan")
            .body("Data sudah disimpan\nApa anda yakin ingin mengimport datebase ini ?")
            .onNegative("Tidak", buttonBackgroundColor = R.drawable.bg_btn_cancel)
            .onPositive("Ya", buttonBackgroundColor = R.drawable.bg_btn_standard) {
                presenter.importDb(data)
            }
    }

    override fun onFailGetData() {
        Toast.makeText(requireContext(), "Fail get data", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessImportDb() {
        Toast.makeText(requireContext(), "Success import Db", Toast.LENGTH_SHORT).show()
    }

    override fun onFailImportDb() {
        Toast.makeText(requireContext(), "Fail import Db", Toast.LENGTH_SHORT).show()
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