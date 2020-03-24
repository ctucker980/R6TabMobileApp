package com.example.r6tabmobileapp

import com.example.r6tabmobileapp.modles.HomeModle

class HomeDataSource {
    companion object {
        fun createDataSet() : ArrayList<HomeModle> {
            val list = ArrayList<HomeModle>()
            list.add (
                HomeModle(
                    "User Tag: Shortly19",
                    "Rank: Silver 1",
                    "User K/D: 1.0"
                )
            )
             return list
        }
    }
}