package com.app.baseproject.ui.main.home

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.baseproject.R
import com.app.baseproject.base.BaseFragment
import com.app.baseproject.data.local.Preferences
import com.app.baseproject.data.model.local.User
import com.app.baseproject.ui.main.MainActivity
import com.app.baseproject.ui.main.home.editUser.EditUserActivity
import com.app.baseproject.ui.main.home.getLocation.GetLocationActivity
import com.app.baseproject.ui.register.RegisterActivity
import com.example.awesomedialog.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : BaseFragment(), HomeView {

    @Inject
    lateinit var presenter: HomePresenter

    @Inject
    lateinit var preferences: Preferences

//    @Inject
//    lateinit var testHomeAdapter: TestHomeAdapter

    private lateinit var homeAdapter: HomeAdapter

    @Suppress("DEPRECATION")
    private var mProgressDialog: ProgressDialog? = null

    override val layout: Int
        get() = R.layout.fragment_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentComponent()?.inject(this)
        mProgressDialog = ProgressDialog(requireContext())
//        testHomeAdapter.setUpHomeView(this)
        val startDate = Calendar.getInstance()
        val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = df.format(startDate.time)
        val dateNow = df.parse(date)
        val dateExpired = preferences.getExpired()
        if (dateNow > dateExpired){
            Toast.makeText(requireContext(), "Session habis", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        presenter.attachRoom(requireContext())
        initListener()
        presenter.getAllUser()
    }

    private fun initView() {
        homeAdapter = HomeAdapter(arrayListOf(), object: HomeAdapter.adapterListener{
            override fun onClickEdit(user: User) {
                val intent = Intent(requireContext(), EditUserActivity::class.java)
                intent.putExtra("user_id", user.id)
                startActivity(intent)
            }

            override fun onClickDelete(user: User) {
                showDialogDelete(user)
            }

        })
        recyclerUser.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeAdapter
        }
    }

    private fun showDialogDelete(user: User) {
        AwesomeDialog.build(requireActivity())
            .position(AwesomeDialog.POSITIONS.BOTTOM)
            .title("Peringatan")
            .body("Apa anda yakin ingin menghapus item ini ?")
            .onNegative("Tidak", buttonBackgroundColor = R.drawable.bg_btn_standard)
            .onPositive("Ya", buttonBackgroundColor = R.drawable.bg_btn_cancel) {
//                val intent = Intent(this, LoginActivity::class.java)
//                startActivity(intent)
                presenter.deleteData(user)
            }
    }

    private fun initListener() {
        btnMoveTaskFragment.setOnClickListener {
            (activity as MainActivity).pindahTask()
        }
        btnGetLocation.setOnClickListener {
            val mLocationManager: LocationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            var gpsOn = false
            var networkOn = false
            try {
                gpsOn = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            } catch (e: ExceptionInInitializerError){}
            try {
                networkOn = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            } catch (e: ExceptionInInitializerError){}
            if (!gpsOn || !networkOn){
                Toast.makeText(activity, "Mohon aktifkan GPS terlebih dahulu\nUntuk Ambil Lokasi Terkini", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(activity, GetLocationActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onFailGetData() {
        Toast.makeText(requireContext(), "Fail to get data", Toast.LENGTH_SHORT).show()
    }

    override fun showAllData(data: List<User>) {
        initView()
        homeAdapter.setData(data)
    }

    override fun onSuccessDeleteData() {
        Toast.makeText(requireContext(), "Success delete data", Toast.LENGTH_SHORT).show()
        presenter.getAllUser()
    }

    override fun onFailedDeleteData() {
        Toast.makeText(requireContext(), "Failed delete data", Toast.LENGTH_SHORT).show()
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