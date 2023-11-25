package com.example.dalportal.ui.ta_portal.tasks

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dalportal.R
import com.example.dalportal.ui.ta_portal.firebase.TaTasksModel

class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val deadlineTextView: TextView = view.findViewById(R.id.deadline)
    private val detailsTextView: TextView = view.findViewById(R.id.taskDetails)
    private val professorTextView: TextView = view.findViewById(R.id.taskProfessor)

    fun bind(task: TaTasksModel) {
        detailsTextView.text = task.description
        deadlineTextView.text = task.deadline
        professorTextView.text = task.assignedBy
    }
}