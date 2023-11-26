package com.example.dalportal.ui.dashboard

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.dalportal.R

class DashboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val dashLayout: RelativeLayout = view.findViewById(R.id.dashItemLayout)
    private val dashButton: Button = view.findViewById(R.id.dashItemButton)
    private var navController: NavController? = null

    init {
        // Initialize the NavController here
        try {
            navController = Navigation.findNavController(itemView)
        } catch (e: Exception) {
            Log.e("DashboardViewHolder", "Error finding NavController: ${e.message}")
        }
    }

    fun bind(item: String) {
        when (item) {
            "assignments_submit" -> {
                dashButton.text = "Submit assignment"
                dashLayout.setBackgroundResource(R.drawable.img_student_assignment)
            }

            "events" -> {
                dashButton.text = "View events"
                dashLayout.setBackgroundResource(R.drawable.img_events)
            }

            "forum" -> {
                dashButton.text = "Discussion forum"
                dashLayout.setBackgroundResource(R.drawable.img_discussion_forums)
            }

            "ta_portal" -> {
                dashButton.text = "TA portal"
                dashLayout.setBackgroundResource(R.drawable.img_portal)
            }

            "update_availability" -> {
                dashButton.text = "Update availability"
                dashLayout.setBackgroundResource(R.drawable.img_availability)
            }

            "assignments_review" -> {
                dashButton.text = "Review assignments"
                dashLayout.setBackgroundResource(R.drawable.img_assignments)
            }

            "content" -> {
                dashButton.text = "Upload files"
                dashLayout.setBackgroundResource(R.drawable.img_content)
            }

            "prof_portal" -> {
                dashButton.text = "Professor portal"
                dashLayout.setBackgroundResource(R.drawable.img_portal)
            }

            "review_availability" -> {
                dashButton.text = "Review availability"
                dashLayout.setBackgroundResource(R.drawable.img_availability)
            }

            "admin_portal" -> {
                dashButton.text = "Admin portal"
                dashLayout.setBackgroundResource(R.drawable.img_admin_portal)
            }

            "job_portal" -> {
                dashButton.text = "Job portal"
                dashLayout.setBackgroundResource(R.drawable.img_job_portal)
            }

            else -> {}
        }

        dashButton.setOnClickListener {
            val navId = navigateItem(item)
            navController = Navigation.findNavController(itemView)
            navController!!.navigate(navId)
        }
    }

    private fun navigateItem(item: String): Int {
        return when (item) {
            "assignments_submit" -> R.id.nav_assignment
            "events" -> R.id.nav_event
            "forum" -> R.id.nav_discussion
            "ta_portal" -> R.id.nav_ta_portal
            "update_availability" -> R.id.nav_availability_calendar
            "assignments_review" -> R.id.nav_assignment_review
            "content" -> R.id.nav_content
            "prof_portal" -> R.id.nav_professor_portal
            "review_availability" -> R.id.nav_availability_calendar_prof
            "admin_portal" -> R.id.nav_admin_portal
            "job_portal" -> R.id.nav_jobListing
            else -> 0
        }
    }
}