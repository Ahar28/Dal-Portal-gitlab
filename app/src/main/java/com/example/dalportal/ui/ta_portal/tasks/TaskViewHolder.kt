package com.example.dalportal.ui.ta_portal.tasks

import android.app.AlertDialog
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dalportal.R
import com.example.dalportal.ui.ta_portal.firebase.TaTasksModel
import com.example.dalportal.util.FirestoreHelper
import com.example.dalportal.util.TaskStatus
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val deadlineTextView: TextView = view.findViewById(R.id.deadline)
    private val detailsTextView: TextView = view.findViewById(R.id.taskDetails)
    private val professorTextView: TextView = view.findViewById(R.id.taskProfessor)
    private val statusTextView: TextView = view.findViewById(R.id.taskStatus)
    private val spinnerItems: Array<String> =
        view.context.resources.getStringArray(R.array.ta_edit_status_spinner_items)
    private val context = view.context

    fun bind(task: TaTasksModel) {
        val deadline: Date? = task.deadline
        var formatter = SimpleDateFormat("dd-MM-yyyy HH:MM")
        val dateString: String = formatter.format(deadline)

        detailsTextView.text = task.description
        deadlineTextView.text = "Due on $dateString"
        professorTextView.text = "Assigned by ${task.profName}"

        val statusString = TaskStatus.valueOf(task.status).statusString
        setStatus(statusString, task.id)

        statusTextView.setOnClickListener {
            AlertDialog.Builder(context)
                .setItems(spinnerItems) { dialog, which ->
                    when (val status = spinnerItems[which]) {
                        TaskStatus.valueOf(task.status).statusString -> {}
                        else -> setStatus(status, task.id)
                    }
                }
                .show()
        }
    }

    private fun setStatus(status: String, taskId: String) {
        statusTextView.text = status

        when (status) {
            TaskStatus.PENDING.statusString -> {
                statusTextView.setTextColor(
                    itemView.context.getColor(
                        R.color.orange
                    )
                )
                statusTextView.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_pending_orange,
                    0,
                    R.drawable.ic_edit_property,
                    0,

                    )
                FirestoreHelper.updateTaskStatus(
                    TaskStatus.PENDING.toString(),
                    taskId
                )
            }

            TaskStatus.COMPLETED.statusString -> {
                statusTextView.setTextColor(
                    itemView.context.getColor(
                        R.color.green
                    )
                )
                statusTextView.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_success_green,
                    0,
                    R.drawable.ic_edit_property,
                    0,
                )

                FirestoreHelper.updateTaskStatus(
                    TaskStatus.COMPLETED.toString(),
                    taskId
                )
            }

            else -> {
                statusTextView.setTextColor(itemView.context.getColor(R.color.cobalt_blue))
                statusTextView.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_in_progress_blue,
                    0,
                    R.drawable.ic_edit_property,
                    0,
                )
                FirestoreHelper.updateTaskStatus(
                    TaskStatus.IN_PROGRESS.toString(),
                    taskId
                )
            }
        }
    }
}