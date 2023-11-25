package com.example.dalportal.ui.professorportal

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.dalportal.databinding.FragmentAssignTaskBinding
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// AssignTaskFragment.kt
class AssignTaskFragment : Fragment() {

    private var _binding: FragmentAssignTaskBinding? = null
    private val binding get() = _binding!!

    // Initialize Firestore
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAssignTaskBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize your form fields
        val descriptionEditText = binding.editTextTaskDescription
        val deadlineEditText = binding.datePickerDeadline
        val prioritySpinner = binding.spinnerPriority
       // val assignedToEditText = binding.editTextAssignedTo

        // Initialize your form fields
        val assignedToSpinner = binding.spinnerAssignedTo

        // Replace "dummy_course_name" with the  course name based on our app's logic
        val courseName = "CSCI_5708"

        // Fetch teaching assistants and populate spinner
        fetchTeachingAssistants(courseName) { tasList ->
            val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, tasList)
            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            assignedToSpinner.adapter = adapter
        }

        val assignButton = binding.assignButton
        assignButton.setOnClickListener {
            // Handle task assignment here
            val description = descriptionEditText.text.toString()
            //val deadline = deadlineEditText.date  //  might need to format this based on our UI element
            val priority = prioritySpinner.selectedItem.toString()
            val assignedTo = assignedToSpinner.selectedItem.toString()  // TA's user ID

            // Convert DatePicker date to a readable format
            val day = deadlineEditText.dayOfMonth
            val month = deadlineEditText.month
            val year = deadlineEditText.year

            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val formattedDeadline = sdf.format(calendar.time)

            val task = mapOf(
                "description" to description,
                "deadline" to formattedDeadline,
                "priority" to priority,
                "assignedTo" to assignedTo,
                "status" to "Not Started",
                "timestamp" to FieldValue.serverTimestamp()
            )

            // Add the task to Firestore
            db.collection("TA")
                .add(task)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Task assigned successfully", Toast.LENGTH_SHORT).show()

                    // You can also navigate back or perform any other actions if needed
                    // For example, navigate back to the ProfessorPortalFragment
                    findNavController().popBackStack()
                }
                .addOnFailureListener {
                    // Task addition failed

                    Toast.makeText(requireContext(), "Error assigning task", Toast.LENGTH_SHORT).show()
                }

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchTeachingAssistants(courseName: String,callback: (List<String>) -> Unit) {
        db.collection("course_info")

            .whereEqualTo("course_name", courseName)
            .get()
            .addOnSuccessListener { result ->
                if (result.documents.isNotEmpty()) {
                    val tasList = result.documents[0].get("TAs") as? List<String> ?: emptyList()
                    callback(tasList)
                } else {
                    // Handle case where no document is found for the specified course
                    Toast.makeText(requireContext(), "No data found for the course", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                // Handle failure
                Toast.makeText(requireContext(), "Error fetching teaching assistants", Toast.LENGTH_SHORT).show()
            }
    }

}
