package com.example.r6tabmobileapp

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.r6tabmobileapp.ui.search.HomeFeed
import com.example.r6tabmobileapp.ui.search.Profile


class SearchAdapter(val profile: Profile): RecyclerView.Adapter<CustomViewHodler>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHodler {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return itemCount
    }

    override fun onBindViewHolder(holder: CustomViewHodler, position: Int) {
        TODO("Not yet implemented")
    }

}

class CustomViewHodler(val view: View):RecyclerView.ViewHolder(view)