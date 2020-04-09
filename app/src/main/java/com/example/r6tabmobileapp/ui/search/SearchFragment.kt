package com.example.r6tabmobileapp.ui.search

import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.r6tabmobileapp.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_search.view.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.lang.reflect.Type

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
        val T = view.findViewById<View>(R.id.result_TextView) as TextView
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

                val player = homeFeed?.players?.toString()
                val playersTest = gson.fromJson(player, PlayersFeed::class.java)
            }

        })
    }
}
class HomeFeed( val status: Int, val foundmatch: Boolean, val requested: String, val players: JsonObject)
class PlayersFeed(val profile: JsonObject)


