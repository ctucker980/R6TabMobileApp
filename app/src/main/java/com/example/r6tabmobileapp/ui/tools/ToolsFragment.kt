package com.example.r6tabmobileapp.ui.tools

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.r6tabmobileapp.MainActivity
import com.example.r6tabmobileapp.R
import com.example.r6tabmobileapp.ui.dashboard
import kotlinx.android.synthetic.main.fragment_tools.*
import kotlinx.android.synthetic.main.fragment_tools.view.*

class ToolsFragment : Fragment() {

    private lateinit var toolsViewModel: ToolsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        toolsViewModel =
            ViewModelProviders.of(this).get(ToolsViewModel::class.java)
        val view : View = inflater.inflate(R.layout.fragment_tools, container, false)

        view.signout_button.setOnClickListener {
            val intent = Intent (activity, MainActivity::class.java)
            startActivity(intent)
        }
        return view
    }

}