package com.example.r6tabmobileapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.r6tabmobileapp.api.User
import com.example.r6tabmobileapp.ui.dashboard
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_user.*

class CreateUser : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        auth = FirebaseAuth.getInstance()

        signUp_Button.setOnClickListener {
            createUser()
        }
    }

    private fun createUser (){
        val userEmail = findViewById<View>(R.id.emailAddress_PlainText) as EditText
        val userPassword = findViewById<View>(R.id.password_PasswordText) as EditText

        var email = userEmail.text.toString()
        var password = userPassword.text.toString()

        if (!email.isEmpty() && !password.isEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Toast.makeText(this, "Authentication Successful", Toast.LENGTH_SHORT).show()
                        updateUI(user)
                    } else {
                        Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                    }
                }

        } else { Toast.makeText(this, "Please Enter Valid Email & Password", Toast.LENGTH_SHORT).show() }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, dashboard::class.java))
        }
    }
}
