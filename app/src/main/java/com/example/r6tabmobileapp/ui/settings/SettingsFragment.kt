package com.example.r6tabmobileapp.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.r6tabmobileapp.MainActivity
import com.example.r6tabmobileapp.R
import com.example.r6tabmobileapp.ui.settings.accountsettings.AccountSettings
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.fragment_settings.view.*

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsViewModel =
            ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        val view : View = inflater.inflate(R.layout.fragment_settings, container, false)
        view.signout_button.setOnClickListener {
            signOut()
        }

        view.account_Button.setOnClickListener {
            accountUserSettings()
        }
        return view
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this.requireActivity())
            .addOnCompleteListener {
                val intent = Intent (activity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
    }

    private fun accountUserSettings () {
        val intent = Intent(activity, AccountSettings::class.java)
        startActivity(intent)
    }
}