package com.example.r6tabmobileapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.r6tabmobileapp.api.User
import com.example.r6tabmobileapp.ui.dashboard
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var  mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        Login_Button.setOnClickListener {
            login()
        }

        Sign_Up_Button.setOnClickListener {
            val intent = Intent(this, CreateUser::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }


    private fun login() {
        val emailText = findViewById<View>(R.id.Email_Input) as EditText
        val passwordText = findViewById<View>(R.id.Password_Input) as EditText

        var email = emailText.text.toString()
        var password = passwordText.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener (this,
                OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (mAuth.currentUser!!.isEmailVerified) {
                            val intent = Intent(this, dashboard::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Please Verify Email Address", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(this, "Please Enter Valid Email & Password", Toast.LENGTH_SHORT).show()
                    }
                })
        } else {
            Toast.makeText(this, "Please Enter Valid Email & Password", Toast.LENGTH_SHORT).show()
        }

        if (TextUtils.isEmpty(emailText.text)) { emailText.error = "This Field Must Not Be Empty" }
        if (TextUtils.isEmpty(passwordText.text)){ passwordText.error = "This Field Must Not Be Empty" }
    }



    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            if (user.isEmailVerified) {
                startActivity(Intent(this, dashboard::class.java))
            }
        }
    }
}


