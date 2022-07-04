package com.app.baseproject.ui.main.home

import android.content.Context
import android.view.View
import com.app.baseproject.base.BaseAdapterItemReyclerView
import com.app.baseproject.base.BaseItemRecyclerViewHolder
import com.app.baseproject.data.model.local.User
import kotlinx.android.synthetic.main.card_item_home.view.*

class TestHomeHolder(mContext: Context, itemView: View, itemClickListener: BaseAdapterItemReyclerView.OnItemClickListener,
    longItemClickListener: BaseAdapterItemReyclerView.OnLongItemClickListener, val hView: HomeView?) :
    BaseItemRecyclerViewHolder<User>(mContext, itemView, itemClickListener, longItemClickListener) {

    override fun bind(data: User?) {
        itemView.tvName.text = data?.name.toString()
        itemView.tvEmail.text = data?.email.toString()
        itemView.icDelete.setOnClickListener {

        }
    }
}