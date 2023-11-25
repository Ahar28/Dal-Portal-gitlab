package com.example.dalportal.ui.job_portal.job_posting

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.dalportal.databinding.FragmentJobPostingBinding
import com.google.firebase.firestore.FirebaseFirestore

class JobPostingFragment : Fragment() {

    private var _binding: FragmentJobPostingBinding? = null
    private val binding get() = _binding!!

    // Firebase Firestore instance
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJobPostingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupSpinner()
        setupButtonClickListener()

        return root
    }

    private fun setupSpinner() {
        // Example list of job types
        val jobTypes = listOf("Casual", "Part-time", "Full-time")

        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, jobTypes)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        binding.jobType.adapter = adapter
    }

    private fun setupButtonClickListener() {
        binding.submitJobPosting.setOnClickListener {
            saveJobPostingToFirestore()
        }
    }

    private fun saveJobPostingToFirestore() {
        val jobTitle = binding.jobTitle.text.toString()
        val jobDescription = binding.jobDescription.text.toString()
        val jobType = binding.jobType.selectedItem.toString()
        val numPositions = binding.numPositions.text.toString()
        val jobLocation = binding.jobLocation.text.toString()
        val rateOfPay = binding.rateOfPay.text.toString()
        val jobRequirements = binding.jobRequirements.text.toString()

        // Check if any field is empty
        if (jobTitle.isEmpty() || jobDescription.isEmpty() || jobType.isEmpty() ||
            numPositions.isEmpty() || jobLocation.isEmpty() || rateOfPay.isEmpty() ||
            jobRequirements.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Create a map of data to save
        val jobData = hashMapOf(
            "title" to jobTitle,
            "description" to jobDescription,
            "type" to jobType,
            "positions" to numPositions,
            "location" to jobLocation,
            "pay" to rateOfPay,
            "requirements" to jobRequirements
        )

        // Add a new document with generated ID
        db.collection("jobPostings")
            .add(jobData)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(requireContext(), "Job posting successfully added!", Toast.LENGTH_SHORT).show()
                clearForm()
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error adding job posting: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun clearForm() {
        binding.jobTitle.setText("")
        binding.jobDescription.setText("")
        binding.jobType.setSelection(0)
        binding.numPositions.setText("")
        binding.jobLocation.setText("")
        binding.rateOfPay.setText("")
        binding.jobRequirements.setText("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
