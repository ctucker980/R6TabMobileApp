package com.example.r6tabmobileapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.r6tabmobileapp.R
import com.example.r6tabmobileapp.api.PlayerFeed
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import okhttp3.*
import java.io.IOException

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var  mAuth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        mAuth = FirebaseAuth.getInstance()

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        view.username_API_Input.text
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            if (user.displayName.isNullOrBlank()){
                view!!.username_API_Input.text = "No Username Set"
            } else {
               fetchJson(user)
            }
        }
    }

    fun fetchJson(user: FirebaseUser?) {
        println("GET Request")
        val url = "https://r6.apitab.com/player/${user?.displayName.toString()}"


        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed To execute request")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()
                println(body)
                val gson = GsonBuilder().create()
                val homeFeed = gson.fromJson(body, PlayerFeed::class.java)

                //User Top Profile Card Data
                val name = homeFeed.player.get("p_name")
                val level = homeFeed.stats.get("level")
                val mmr = homeFeed.ranked.get("mmr")
                val kd = homeFeed.ranked.get("kd")
                val platform = homeFeed.player.get("p_platform")

                //Causal Stats Card Data
                val casualPVPKills = homeFeed.stats.get("casualpvp_kills")
                val casualPVPDeaths = homeFeed.stats.get("casualpvp_death")
                val casualPVPMatchWon = homeFeed.stats.get("casualpvp_matchwon")
                val casualPVPMatchLost = homeFeed.stats.get("casualpvp_matchlost")
                val casualKD = homeFeed.stats.get("casualpvp_kd")
                val casualWinLoss = homeFeed.stats.get("casualpvp_wl")
                val casualMatchesPlayed = homeFeed.stats.get("casualpvp_matches")

                //Rank Stats Card Data
                val rankPVPKills = homeFeed.stats.get("rankedpvp_kills")
                val rankPVPDeaths = homeFeed.stats.get("rankedpvp_death")
                val rankKD = homeFeed.stats.get("rankedpvp_kd")
                val rankPVPMatchWon = homeFeed.stats.get("rankedpvp_matchwon")
                val rankPVPMatchLost = homeFeed.stats.get("rankedpvp_matchlost")
                val rankMatchesPlayed = homeFeed.stats.get("rankedpvp_matches")
                val rankWinLoss = homeFeed.stats.get("rankedpvp_wl")

                var imageUrl : String = "https://ubisoft-avatars.akamaized.net/${user?.displayName.toString()}/default_146_146.png"



                activity?.runOnUiThread {
                    //User Main Card Data Binding
                    username_API_Input.text = name.asString
                    userLevel_API_Input.text = level.asString
                    userMMR_API_Input.text = mmr.asString
                    userKD_API_Input.text = String.format("%.2f",kd.asDouble)
                    userPlatform_API_Input.text = platform.asString

                    //User Causal Card Data Binding
                    userCasualPVPKills_API_Input.text = casualPVPKills.asString
                    userCasualDeaths_API_Input.text = casualPVPDeaths.asString
                    userCasualKD_API_Input.text = casualKD.asString
                    userCasualMatchesWon_API_Input.text = casualPVPMatchWon.asString
                    userCasualMatchesLost_API_Input.text = casualPVPMatchLost.asString
                    userCasualMatchesPlayed_API_Input.text = casualMatchesPlayed.asString
                    userCasualWinLoss_API_Input.text = casualWinLoss.asString

                    //User Rank Card Data Binding
                    userRankedKills_API_Input.text = rankPVPKills.asString
                    userRankedDeaths_API_Input.text = rankPVPDeaths.asString
                    userRankedKD_API_Input.text = rankKD.asString
                    userRankedMatchesWon_API_Input.text = rankPVPMatchWon.asString
                    userRankedMatchesLost_API_Input.text = rankPVPMatchLost.asString
                    userRankedMatchesPlayed_API_Input.text = rankMatchesPlayed.asString
                    userRankedWinLoss_API_Input.text = rankWinLoss.asString


                    val requestOptions = RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)

                    Glide.with(activity!!.baseContext)
                        .load(imageUrl)
                        .into(profile_picture)
                }
            }
        })
    }
}