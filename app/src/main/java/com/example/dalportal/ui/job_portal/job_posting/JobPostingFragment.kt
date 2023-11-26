package com.example.dalportal.ui.job_portal.job_posting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.dalportal.databinding.FragmentJobPostingBinding
import com.google.firebase.firestore.FirebaseFirestore
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException


/*
    125892 - IT Developer Coop Student

    Information Technology / Client Solutions within the City of Calgary has an exciting opportunity for a student to fill an IT Developer Coop Student role. The successful applicant will focus on supporting the advancement of several Microsoft technology implementations including Power Automate, Power Apps, Logic Apps, Azure Functions under the leadership of experienced staff. This position will report to the Leader for IT-CS-Resource Centre 1 and will be exposed to a variety of projects related to Client Solutions priorities in the Microsoft power platform space. Specific duties of this position include:
    Researching best practices in the development of power automate, power apps, and or logic apps
    Document governance roles / rules / responsibilities and accountabilities, etc.
    Create samples for others to reuse or modify to fit business needs

    Qualifications:
    Enrolled in a post-secondary program in Computer Science with 2 years completed in the program.
    Previous experience with Power Automate, Power Apps, Logic Apps, or Azure Functions
    Intermediate proficiency in Microsoft Office (Outlook, Word, Excel, and Teams)
    Knowledge and experience using programming languages and databases and related technologies is considered an asset
    Experience documenting system functionality and technical processes (workflow diagrams) is considered an asset.
    Successful candidate will have communication and interpersonal skills along with demonstrated attention to detail.
    Pre-employment Requirements
    Successful applicants must provide proof of qualifications.
    A security clearance will be conducted.
    You must be currently attending a full-time post-secondary program and be required to complete a Work Integrated Learning experience placement (e.g., co-op, practicum, internship) to apply for this competition.
    This position is subject to Union dues.*/
class JobPostingFragment : Fragment() {
    private var _binding: FragmentJobPostingBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJobPostingBinding.inflate(inflater, container, false)
        setupSpinner()
        setupButtonClickListener()
        return binding.root
    }

    private fun setupSpinner() {
        val jobTypes = listOf("Casual", "Part-time", "Full-time")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, jobTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
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
        val numPositions = binding.numPositions.text.toString().toIntOrNull() ?: 0 // Parse as Int, default to 0
        val jobLocation = binding.jobLocation.text.toString()
        val rateOfPay = binding.rateOfPay.text.toString().toIntOrNull() ?: 0 // Parse as Int, default to 0
        val jobRequirements = binding.jobRequirements.text.toString()


        if (jobTitle.isEmpty() || jobDescription.isEmpty() || jobType.isEmpty() ||
            numPositions ==0 || jobLocation.isEmpty() || rateOfPay == 0 ||
            jobRequirements.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            return
        }

        fetchTags("$jobDescription $jobRequirements") { tags ->
            val jobData = hashMapOf(
                "title" to jobTitle,
                "description" to jobDescription,
                "type" to jobType,
                "positions" to numPositions,
                "location" to jobLocation,
                "pay" to rateOfPay,
                "requirements" to jobRequirements,
                "tags" to tags
            )

            db.collection("jobPostings")
                .add(jobData)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Job posting successfully added!", Toast.LENGTH_SHORT).show()
                    clearForm()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Error adding job posting: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun fetchTags(jobDescription: String, onComplete: (List<String>) -> Unit) {
        val client = OkHttpClient()
        val requestJson = JSONObject().apply {
            put("questionText", jobDescription)
        }

        val requestBody = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            requestJson.toString()
        )

        val request = Request.Builder()
            .url("https://auto-tagging-ynig4ve4cq-ue.a.run.app")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle failure
                Toast.makeText(requireContext(), "Error making call: ${e.message}", Toast.LENGTH_LONG).show()

            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseJson = JSONObject(response.body?.string() ?: "")
                    val tags = responseJson.getJSONArray("tags")
                    val tagList = ArrayList<String>()
                    for (i in 0 until tags.length()) {
                        tagList.add(tags.getString(i))
                    }
                    onComplete(tagList)
                } else {
                    // Handle error
                    Toast.makeText(requireContext(), "Error creating tags", Toast.LENGTH_LONG).show()

                }
            }
        })
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
