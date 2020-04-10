package com.example.r6tabmobileapp.ui.search

import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.r6tabmobileapp.R
import com.google.firebase.database.android.AndroidPlatform
import com.google.gson.*
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.user_layout.*
import kotlinx.android.synthetic.main.user_layout.view.*
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Type
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList


// Search Method
class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProviders.of(this).get(SearchViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_search, container, false)
        view.search_View.setOnQueryTextListener( object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (view.pc_RadioButton.isChecked) {
                    fetchJson(query.toString(), "uplay")
                } else if (view.xbox_radioButton2.isChecked){
                    fetchJson(query.toString(), "xbl")
                } else if (view.psn_radioButton3.isChecked){
                    fetchJson(query.toString(), "psn")
                } else {
                    Toast.makeText(activity, "Please Select Platform", Toast.LENGTH_SHORT).show()
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        return view
    }

    fun fetchJson (userUrl: String, platform: String) {
        println("GET Request")
        val url = "https://r6.apitab.com/search/$platform/$userUrl"


        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
               println("Failed To execute request")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()
                println(body)

                val userList = arrayListOf<User?>()

                try {
                    val gson = GsonBuilder().create()
                    val homeFeed = gson.fromJson(body, HomeFeed::class.java)
                    try {
                        val id = homeFeed.players.keySet()
                        for (i in id) {
                            var userKey = homeFeed.players.get(i)
                            var runUser = userKey.asJsonObject
                            var profile = runUser.get("profile")
                            var rank = runUser.get("ranked")
                            var userProfile = gson.fromJson(profile, Profile::class.java)
                            var userRank = gson.fromJson(rank, Ranked::class.java)
                            var user = User(userProfile, userRank)
                            userList.add(user)
                        }
                    } catch (e : java.lang.NullPointerException) {
                        activity?.runOnUiThread { Toast.makeText(activity, "Please Enter Valid Username", Toast.LENGTH_LONG).show() }
                    }
                } catch (e : com.google.gson.JsonSyntaxException) { 
                    activity?.runOnUiThread { Toast.makeText(activity, "No User Found", Toast.LENGTH_LONG).show() }
                }

                activity?.runOnUiThread {
                    val recyclerView = recycler_view
                    recyclerView.layoutManager = LinearLayoutManager(activity)
                    class RecyclerViewAdapter(val items: ArrayList<User?>) :
                        RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder>() {
                        inner class CustomViewHolder(itemView: View) :
                            RecyclerView.ViewHolder(itemView) {
                            var userName = itemView.userName_TextView
                            var userKD = itemView.userKD_TextView
                            var userMMR = itemView.userMMR_TextView
                            var userPlatform = itemView.userPlatform_TextView
                        }

                        override fun onCreateViewHolder(
                            parent: ViewGroup,
                            viewType: Int
                        ): CustomViewHolder {
                            val view = LayoutInflater.from(parent.context)
                                .inflate(R.layout.user_layout, parent, false)
                            return CustomViewHolder(view)
                        }

                        override fun getItemCount(): Int {
                            return items.size
                        }

                        override fun onBindViewHolder(
                            holder: CustomViewHolder,
                            position: Int
                        ) {
                            holder.userName.text = items[position]!!.profile.p_name
                            holder.userKD.text = String.format("%.2f", items[position]!!.ranked.kd)
                            holder.userMMR.text = items[position]!!.ranked.mmr
                            holder.userPlatform.text = items[position]!!.profile.p_platform
                        }
                    }

                    val userAdapter = RecyclerViewAdapter(userList)
                    recyclerView.adapter = userAdapter
                }
            }
        })
    }
}
class HomeFeed( val status: Int, val foundmatch: Boolean, val requested: String, val players: JsonObject)
class Profile(val p_name: String, val p_user: String, val p_platform: String, val verified: Boolean)
class Ranked(val kd: Double, val mmr: String, val rank: String)
class User(val profile: Profile, val ranked : Ranked)