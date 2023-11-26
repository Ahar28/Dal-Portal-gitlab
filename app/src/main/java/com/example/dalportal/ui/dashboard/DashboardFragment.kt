package com.example.dalportal.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dalportal.R
import com.example.dalportal.ui.chat.users.ChatUserActivity
import com.example.dalportal.util.DashItems
import com.example.dalportal.util.UserData
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DashboardFragment : Fragment() {

    private lateinit var chatButton: FloatingActionButton
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var dashRecyclerView: RecyclerView
    private val dashAdapter: DashboardAdapter = DashboardAdapter()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dashRecyclerView = view.findViewById(R.id.dashRecyclerView)
        dashRecyclerView.layoutManager = LinearLayoutManager(context)
        dashRecyclerView.adapter = dashAdapter

        var dashItems: List<String> = DashItems[UserData.role]
        dashAdapter.updateDashItems(dashItems)
    }


}