package com.example.dalportal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.dalportal.databinding.ActivityMainBinding
import com.example.dalportal.ui.DiscussionForm.PostListActivity
import com.example.dalportal.util.UserData
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val navView: NavigationView = binding.navView
        val headerView = navView.getHeaderView(0)
        val currentEmailTextView = headerView.findViewById<TextView>(R.id.current_email)
        val currentNameTextView = headerView.findViewById<TextView>(R.id.current_user)
    val currentRoleTextView=headerView.findViewById<TextView>(R.id.current_role)
        // Set the text to user's email
        currentEmailTextView.text = UserData.email ?: "John@gmail.com"
        currentNameTextView.text=UserData.name ?: "John Doe"
            currentRoleTextView.text=UserData.role?:"Professor"
        val drawerLayout: DrawerLayout = binding.drawerLayout

        val navController = findNavController(R.id.nav_host_fragment_content_main)


        // Set up NavigationItemSelectedListener
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_feedback_form -> {
                    // Navigate to FeedbackFragment
                    navController.navigate(R.id.nav_feedback_form)
                    drawerLayout.closeDrawers()
                    true
                }
                // Handle other menu items...
                else -> false
            }
        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_availability_calendar
            ), drawerLayout
        )

        val logoutMenuItem = navView.menu.findItem(R.id.logout)
        logoutMenuItem.setOnMenuItemClickListener {
            logoutUser()
            true
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
//        navView.setNavigationItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.nav_discussion -> {
//                    // Open the PostListActivity where all posts are listed
//                    val intent = Intent(this, PostListActivity::class.java)
//                    startActivity(intent)
//                    drawerLayout.closeDrawers()
//                    true
//                }
//                // Handle other menu items if necessary...
//                else -> false
//            }
//        }
    }
    private fun logoutUser() {
        // Clear user data
        UserData.clear()

        // Redirect to LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Close the current activity
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}