package com.example.r6tabmobileapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.r6tabmobileapp.ui.dashboard
import kotlinx.android.synthetic.main.activity_login.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (TextUtils.isEmpty(Email_Input.text)) {
            Email_Input.setError("This Field Must Not Be Empty", null)
        }
        if (TextUtils.isEmpty(Password_Input.text)) {
            Password_Input.setError("This Field Must Not Be Empty", null)
        }


        Login_Button.setOnClickListener {
            val userEmail = Email_Input.text.toString()

            val userPassword = Password_Input.text.toString()

            if (TextUtils.isEmpty(userEmail) && TextUtils.isEmpty(userPassword)) {
                Toast.makeText(applicationContext, "Please Enter A Email & Password", Toast.LENGTH_SHORT).show()
            }

            else if (TextUtils.isEmpty(userEmail)) {
                Toast.makeText(applicationContext, "Please Enter A Email", Toast.LENGTH_SHORT).show()
            }

            else if ( TextUtils.isEmpty(userPassword)) {
                Toast.makeText(applicationContext, "Please Enter A Password", Toast.LENGTH_SHORT).show()
            }

            else if (userEmail == "admin" && userPassword == "password") {
               val userDash = Intent(this, dashboard::class.java)
                   startActivity(userDash)
            }

            else {
                Toast.makeText(applicationContext, "Please Enter A Valid Email & Password", Toast.LENGTH_SHORT).show()
            }
        }

        Forgot_Password_Button.setOnClickListener {
            //val forgotPasswordNav = Intent (this, )
        }
    }
}
