package com.example.dalportal.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dalportal.MainActivity
import com.example.dalportal.R
import com.example.dalportal.databinding.FragmentDashboardBinding
import com.example.dalportal.ui.chat.users.ChatUserActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DashboardFragment : Fragment() {

    private lateinit var chatButton: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_dashboard, container, false)

        chatButton = view.findViewById(R.id.chatButton)
        chatButton.setOnClickListener {
            val intent =
                Intent(
                    context,
                    ChatUserActivity::class.java
                )
            startActivity(intent)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}