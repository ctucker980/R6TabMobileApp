package com.example.r6tabmobileapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.r6tabmobileapp.R
import com.google.gson.*
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.user_layout.*
import kotlinx.android.synthetic.main.user_layout.view.*
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList


// Search Method
class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    private val client = OkHttpClient()


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
                fectchJson(query.toString())
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        return view
    }

    fun fectchJson (userurl: String) {
        println("GET Request")
        val url = "https://r6.apitab.com/search/uplay/$userurl"


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
                val homeFeed = gson.fromJson(body, HomeFeed::class.java)

                //val getFirstKey = homeFeed.players.get("69afb9b7-cdf7-4be7-909b-d244081e93e1")
                //val firstUser = getFirstKey.asJsonObject

               val Id = homeFeed.players.keySet()
                var userKey : JsonElement
                var runUser : JsonObject
                var profile : JsonElement
                var userProfile : Profile


                val userList = arrayListOf<Profile?>()



                for (i in Id) {
                   userKey = homeFeed.players.get(i)
                    runUser = userKey.asJsonObject
                    profile = runUser.get("profile")
                    userProfile = gson.fromJson(profile, Profile::class.java)
                    userList.add(userProfile)
                }

               // val profile = firstUser.get("profile")
                //val firstUserProfile = gson.fromJson(profile, Profile::class.java)

                //val ranked = firstUser.get("ranked")
                //val firstUserRanked = gson.fromJson(ranked, Ranked::class.java)

                activity?.runOnUiThread {
                    // result_TextView.text = firstUserProfile.p_name
                    //mmr_TextView.text = firstUserRanked.mmr.toString()

                    val recyclerView = recycler_view
                    recyclerView.layoutManager = LinearLayoutManager(activity)
                    class RecyclerViewAdapter( val items : ArrayList<Profile?>): RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder>() {
                        inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                            var userName = itemView.userName_TextView
                        }

                        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
                            val view = LayoutInflater.from(parent.context)
                                .inflate(R.layout.user_layout, parent, false)
                            return CustomViewHolder(view)
                        }

                        override fun getItemCount(): Int {
                            return items.size
                        }

                        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
                            holder.userName.text = items[position]!!.p_name
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
class Ranked(val kd: Double, val mmr: Int, val rank: Int)




