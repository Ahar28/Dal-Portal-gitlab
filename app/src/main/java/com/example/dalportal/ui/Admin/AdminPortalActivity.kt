package com.example.dalportal.ui.Admin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dalportal.R
import com.example.dalportal.model.AdminStat
import com.example.dalportal.util.FirestoreHelper

class AdminPortalActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdminStatsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_portal)

        recyclerView = findViewById(R.id.rvAdminStats)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchAdminData()
    }

    private fun fetchAdminData() {
        FirestoreHelper.fetchAdminData(onSuccess = { data ->
            val stats = data.map { AdminStat(it.key.replaceFirstChar { it.uppercase() }, it.value.toString()) }
            adapter = AdminStatsAdapter(stats)
            recyclerView.adapter = adapter
        }, onFailure = { exception ->
            Toast.makeText(this, "Error fetching admin data: ${exception.message}", Toast.LENGTH_LONG).show()
        })
    }
}
