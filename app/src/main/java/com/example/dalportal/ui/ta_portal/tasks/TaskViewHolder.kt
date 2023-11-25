package com.example.dalportal.ui.ta_portal.tasks

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dalportal.R
import com.example.dalportal.ui.ta_portal.firebase.TaTasksModel
import com.example.dalportal.util.TaskStatus

class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val deadlineTextView: TextView = view.findViewById(R.id.deadline)
    private val detailsTextView: TextView = view.findViewById(R.id.taskDetails)
    private val professorTextView: TextView = view.findViewById(R.id.taskProfessor)
    private val statusTextView:TextView = view.findViewById(R.id.taskStatus)

    fun bind(task: TaTasksModel) {
        val status = TaskStatus.valueOf(task.status).statusString
        detailsTextView.text = task.description
        deadlineTextView.text = task.deadline.toString()
        professorTextView.text = task.profName
        statusTextView.text = status
    }
}