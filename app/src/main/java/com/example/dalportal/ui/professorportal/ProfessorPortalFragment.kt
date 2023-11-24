package com.example.dalportal.ui.professorportal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dalportal.R
import com.example.dalportal.databinding.FragmentProfessorPortalBinding

class ProfessorPortalFragment : Fragment() {

    private var _binding: FragmentProfessorPortalBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val porfessorPortalViewModel =
            ViewModelProvider(this).get(ProfessorPortalViewModel::class.java)

        _binding = FragmentProfessorPortalBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGalleryProfessor
        val assignTaskbutton: Button = binding.assignAtaskbutton
        val progressMonitoringbutton: Button = binding.progressMonitoringButton

        porfessorPortalViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        assignTaskbutton.setOnClickListener {
            // Handle task assignment here
            // Navigate to AssignTaskFragment
            findNavController().navigate(R.id.action_professor_to_assign_task)
        }
        progressMonitoringbutton.setOnClickListener {
            // Handle task assignment here
            // Navigate to AssignTaskFragment
            findNavController().navigate(R.id.action_professor_to_task_dashboard)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}