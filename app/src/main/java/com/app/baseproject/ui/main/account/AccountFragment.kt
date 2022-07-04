package com.app.baseproject.ui.main.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.baseproject.R
import com.app.baseproject.base.BaseFragment
import com.app.baseproject.data.local.Preferences
import com.app.baseproject.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.fragment_account.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : BaseFragment(), AccountView {

    @Inject
    lateinit var presenter: AccountPresenter

    @Inject
    lateinit var preferences: Preferences

    override val layout: Int
        get() = R.layout.fragment_account

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentComponent()?.inject(this)
    }

    private fun initListener() {
        btnLogout.setOnClickListener {
            preferences.saveToken("")
            val intent = Intent(requireContext(), RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        initListener()
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