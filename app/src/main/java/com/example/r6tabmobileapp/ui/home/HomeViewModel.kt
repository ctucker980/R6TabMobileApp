package com.example.r6tabmobileapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.r6tabmobileapp.HomeDataSource
import com.example.r6tabmobileapp.recyclerviewadpter.HomeModleRecyclerAdpater
import kotlinx.android.synthetic.main.fragment_home.*

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }


    val text: LiveData<String> = _text
}