package com.example.r6tabmobileapp.ui.settings.accountsettings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowId
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.r6tabmobileapp.R
import com.example.r6tabmobileapp.api.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_account_settings.*
import okhttp3.*
import java.io.IOException

class AccountSettings : AppCompatActivity() {
    val user = FirebaseAuth.getInstance().currentUser
    val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_settings)

        setPlayerId_Button.setOnClickListener {
            setPlayerId_Layout.isVisible = setPlayerId_Button.isChecked
        }

        changePassword_Button.setOnClickListener {
            val emailAddress = user?.email
            auth.sendPasswordResetEmail(emailAddress.toString()).addOnCompleteListener {task -> if (task.isSuccessful) {
                    runOnUiThread { Toast.makeText(applicationContext, "Password Rest Sent To Your Email", Toast.LENGTH_LONG).show() }
                }
            }
        }

        setUserNameComfirm_Button.setOnClickListener {
            val newUserId = findViewById<View>(R.id.setNewUserName_PlainText) as EditText
            fetchJson(newUserId.text.toString())
        }
    }

    fun fetchJson(userId: String) {
        println("GET Request")
        val url = "https://r6.apitab.com/player/$userId"


        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed To execute request")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()
                println(body)

                try {
                    val gson = GsonBuilder().create()
                    val homeFeed = gson.fromJson(body, PlayerFeed::class.java)
                    try {
                        val userId = homeFeed.player.get("p_id")

                        if (!user?.displayName.equals(userId.asString)) {
                            val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName(userId.asString)
                                .build()
                            user?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    runOnUiThread {
                                        Toast.makeText(
                                            applicationContext,
                                            "Player Id Has Been Updated To: " + user.displayName,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }

                                } else {
                                    runOnUiThread {
                                        Toast.makeText(
                                            applicationContext,
                                            "Error Setting Player Id",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }
                        } else {
                            runOnUiThread { Toast.makeText(applicationContext, "Id Already Set To This", Toast.LENGTH_LONG).show() }
                        }
                    } catch (e: java.lang.NullPointerException) {
                        runOnUiThread {
                            Toast.makeText(applicationContext, "Please Enter Valid Player Id", Toast.LENGTH_LONG).show()
                        }
                    }
                } catch (e : com.google.gson.JsonSyntaxException) {

                }
            }
        })
    }
}
