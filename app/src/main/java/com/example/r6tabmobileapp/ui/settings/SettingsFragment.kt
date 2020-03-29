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
            val intent = Intent (activity, MainActivity::class.java)
            startActivity(intent)
        }
        return view
    }

}