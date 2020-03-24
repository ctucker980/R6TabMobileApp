package com.example.r6tabmobileapp.recyclerviewadpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.r6tabmobileapp.R
import com.example.r6tabmobileapp.modles.HomeModle
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.layout_homemodle_list_item.view.*

class HomeModleRecyclerAdpater : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items : List<HomeModle> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeModleViewHolder (
            LayoutInflater.from(parent.context).inflate(R.layout.layout_homemodle_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is HomeModleViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

    fun submitList(homeList: List <HomeModle>) {
        items = homeList
    }

    class HomeModleViewHolder constructor(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val user_tag = itemView.uesrTag
        val user_rank = itemView.userRank
        val user_KD = itemView.userKD

        fun bind(homeModle: HomeModle) {
            user_tag.setText(homeModle.userTag)
            user_KD.setText(homeModle.userKD)
            user_rank.setText(homeModle.userRank)
        }
    }

}