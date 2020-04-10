package com.example.r6tabmobileapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.r6tabmobileapp.R
import com.example.r6tabmobileapp.api.PlayerFeed
import com.example.r6tabmobileapp.api.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
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

                val name = homeFeed.player.get("p_name")
                val mmr = homeFeed.ranked.get("mmr")
                val kd = homeFeed.ranked.get("kd")

                var imageUrl : String = "https://ubisoft-avatars.akamaized.net/${user?.displayName.toString()}/default_146_146.png"



                activity?.runOnUiThread {
                    username_API_Input.text = name.asString
                    userMMR_API_Input.text = mmr.asString
                    userKD_API_Input.text = String.format("%.2f",kd.asDouble)

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