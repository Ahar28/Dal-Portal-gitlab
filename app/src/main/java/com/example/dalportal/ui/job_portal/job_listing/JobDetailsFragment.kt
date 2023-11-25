package com.example.dalportal.ui.job_portal.job_listing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dalportal.R
import com.example.dalportal.databinding.FragmentJobDetailsBinding
import com.example.dalportal.model.JobListing

class JobDetailsFragment : Fragment() {
    private var _binding: FragmentJobDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJobDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val jobListing: JobListing? = arguments?.getParcelable("jobListing")
        displayJobDetails(jobListing)
    }

    private fun displayJobDetails(jobListing: JobListing?) {
        jobListing?.let {
            // Bind the job details to your UI components here
            binding.textViewTitle.text = it.title
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
