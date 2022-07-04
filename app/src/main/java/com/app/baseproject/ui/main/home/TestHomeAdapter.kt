package com.app.baseproject.ui.main.home

import android.app.Activity
import android.view.ViewGroup
import com.app.baseproject.R
import com.app.baseproject.base.BaseAdapterItemReyclerView
import com.app.baseproject.data.model.local.User
import javax.inject.Inject

class TestHomeAdapter @Inject
constructor(activity: Activity) : BaseAdapterItemReyclerView<User, TestHomeHolder>(activity) {

    private var hView: HomeView? = null

    override fun getItemResourceLayout(viewType: Int): Int {
        return  R.layout.card_item_home
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestHomeHolder {
        return TestHomeHolder(context, getView(parent, viewType), itemClickListener, longItemClickListener, hView)
    }

    fun setUpHomeView(homeView: HomeView){
        this.hView = homeView
    }
}