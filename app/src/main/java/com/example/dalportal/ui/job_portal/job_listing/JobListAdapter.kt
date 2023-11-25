package com.example.dalportal.ui.job_portal.job_listing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dalportal.R
import com.example.dalportal.model.JobListing

class JobListingAdapter(private var jobListings: List<JobListing>) :
    RecyclerView.Adapter<JobListingAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewJobTitle: TextView = view.findViewById(R.id.textViewJobTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_job_listing, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val jobListing = jobListings[position]
        holder.textViewJobTitle.text = jobListing.title
    }

    override fun getItemCount() = jobListings.size

    fun updateData(newJobListings: List<JobListing>) {
        jobListings = newJobListings
        notifyDataSetChanged()
    }
}
