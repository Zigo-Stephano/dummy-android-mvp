package com.app.baseproject.ui.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.baseproject.R
import com.app.baseproject.data.model.local.User
import kotlinx.android.synthetic.main.card_item_home.view.*

class HomeAdapter(private val users: ArrayList<User>, private val listener: adapterListener) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    class HomeViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_item_home, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val user = users[position]
        holder.view.tvName.text = user.name
        holder.view.tvEmail.text = user.email
        holder.view.icEdit.setOnClickListener {
            listener.onClickEdit(user)
        }
        holder.view.icDelete.setOnClickListener {
            listener.onClickDelete(user)
        }
    }

    override fun getItemCount(): Int = users.size

    fun setData(list: List<User>){
        users.clear()
        users.addAll(list)
        notifyDataSetChanged()
    }

    interface adapterListener {
        fun onClickEdit(user: User)
        fun onClickDelete(user: User)
    }
}