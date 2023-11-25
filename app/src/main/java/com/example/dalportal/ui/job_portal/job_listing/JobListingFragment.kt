package com.example.dalportal.ui.job_portal.job_listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dalportal.R
import com.example.dalportal.databinding.FragmentJobListingBinding
import com.example.dalportal.model.JobListing
import com.google.firebase.firestore.FirebaseFirestore

class JobListingFragment : Fragment() {
    private var _binding: FragmentJobListingBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addButton = view.findViewById<Button>(R.id.addJobButton)
        addButton.setOnClickListener {
            findNavController().navigate(R.id.action_jobListingFragment_to_jobPostingFragment)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentJobListingBinding.inflate(inflater, container, false)

        setupRecyclerView()
        loadJobPostings()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerViewJobListings.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewJobListings.adapter = JobListingAdapter(mutableListOf(), requireContext())
    }

    private fun loadJobPostings() {
        db.collection("jobPostings").get()
            .addOnSuccessListener { documents ->
                val jobListings = documents.map { doc ->
                    JobListing(
                        id = doc.id, // Save the document ID
                        title = doc.getString("title") ?: "",
                        // Add other fields from the document as necessary
                    )
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
