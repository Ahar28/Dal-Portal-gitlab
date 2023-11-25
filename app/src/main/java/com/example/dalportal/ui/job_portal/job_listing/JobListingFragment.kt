package com.example.dalportal.ui.job_listing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dalportal.databinding.FragmentJobListingBinding
import com.example.dalportal.model.JobListing
import com.example.dalportal.ui.job_portal.job_listing.JobListingAdapter
import com.google.firebase.firestore.FirebaseFirestore

class JobListingFragment : Fragment() {
    private var _binding: FragmentJobListingBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentJobListingBinding.inflate(inflater, container, false)
        setupRecyclerView()
        loadJobPostings()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerViewJobListings.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewJobListings.adapter = JobListingAdapter(listOf())
    }

    private fun loadJobPostings() {
        db.collection("jobPostings").get()
            .addOnSuccessListener { documents ->
                val jobListings = documents.map { doc ->
                    JobListing(title = doc.getString("title") ?: "")
                    // Map other fields if needed
                }
                (binding.recyclerViewJobListings.adapter as JobListingAdapter).updateData(jobListings)
            }
            .addOnFailureListener { e ->
                // Handle error, e.g., show a Toast
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
