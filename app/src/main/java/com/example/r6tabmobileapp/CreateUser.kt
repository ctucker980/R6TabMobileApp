package com.example.r6tabmobileapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.r6tabmobileapp.api.User
import com.google.firebase.database.FirebaseDatabase

class CreateUser : AppCompatActivity() {

    lateinit var emailAddress : EditText
    lateinit var userName : EditText
    lateinit var password : EditText
    lateinit var signUpButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        emailAddress = findViewById(R.id.emailAddress_PlainText)
        userName = findViewById(R.id.userName_PlainText)
        password = findViewById(R.id.password_PasswordText)
        signUpButton = findViewById(R.id.signUp_Button)

        signUpButton.setOnClickListener {
            createUser()
        }
    }

        private fun createUser() {
            val email = emailAddress.text.toString().trim()
            if (email.isEmpty()) {
                emailAddress.error = "Please Enter Valid Email Address"
                return
            }

            val username = userName.text.toString().trim()
            if (username.isEmpty()) {
                userName.error = "Please Enter UserName"
                return
            }

            val userPassword = password.text.toString().trim()
            if (userPassword.isEmpty()) {
                password.error = "Please Enter Password"
                return
            }



            val ref = FirebaseDatabase.getInstance().getReference("Users")
            val userId = ref.push().key.toString()
            val user = User(userId ,email, username, userPassword)

            ref.child(userId).setValue(user).addOnCompleteListener {
                Toast.makeText(applicationContext, "User Added Successfully", Toast.LENGTH_LONG).show()
                val backToLogin = Intent (this, MainActivity::class.java)
                startActivity(backToLogin)
            }

        }

}
