package com.example.r6tabmobileapp.ui.settings.accountsettings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.r6tabmobileapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_account_settings.*

class AccountSettings : AppCompatActivity() {
    val user = FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_settings)

        setUserName_Button.setOnClickListener {
            setUserName_Layout.isVisible = setUserName_Button.isChecked
        }

        changePassword_Button.setOnClickListener {
            changePassword_Layout.isVisible = changePassword_Button.isChecked
        }

        setUserNameComfirm_Button.setOnClickListener {
            val newUserName = findViewById<View>(R.id.setNewUserName_PlainText) as EditText

            if (newUserName.text.isNotEmpty()) {
                if (newUserName.text.toString() == user!!.displayName) {
                    Toast.makeText(this, "Username Already Set To: " + newUserName.text, Toast.LENGTH_LONG).show()
                } else {
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(newUserName.text.toString())
                        .build()
                    user?.updateProfile(profileUpdates)?.addOnCompleteListener{ task ->
                        if (task.isSuccessful){
                            Toast.makeText(this, "Username Has Been Updated To: " + user.displayName, Toast.LENGTH_LONG).show()
                        }else {
                            Toast.makeText(this, "Error Setting New Username", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } else {
                newUserName.error = "This Field Needs To Be Filled Out"
            }

        }
    }


}
