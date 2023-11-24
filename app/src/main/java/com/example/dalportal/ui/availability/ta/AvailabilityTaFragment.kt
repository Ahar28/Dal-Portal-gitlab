package com.example.dalportal.ui.availability.ta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.example.dalportal.R

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.example.dalportal.ui.availability.ta.adapter.TabViewAdapter
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class AvailabilityTaFragment : Fragment() {

    private lateinit var tabViewAdapter: TabViewAdapter
    private lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_availability_ta, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tabViewAdapter = TabViewAdapter(this)
        viewPager = view.findViewById(R.id.ava_ta_pager)
        viewPager.adapter = tabViewAdapter
        val tabLayout: TabLayout = view.findViewById(R.id.tabLayout)

        // TabLayoutMediator to set tab names based on the current province
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = arrayOf("Add", "View", "Time Off").get(position)
        }.attach()
    }
}