package com.example.dalportal.ui.ta_portal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dalportal.R
import com.example.dalportal.ui.ta_portal.firebase.TaTasksModel
import com.example.dalportal.ui.ta_portal.tasks.TasksAdapter
import com.example.dalportal.util.UserData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class TaPortalHomeFragment : Fragment() {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var recyclerView: RecyclerView
    private val adapter = TasksAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ta_portal_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.tasksRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        getTaTasks(view)
    }

    private fun getTaTasks(view: View, showAll: Boolean = false) {
        val collectionRef = db.collection("TA_tasks")
        val tasksEmptyTextView: TextView = view.findViewById(R.id.tasksEmpty)
        val showMoreTasksTextView: TextView = view.findViewById(R.id.showMoreTasks)
        collectionRef.whereEqualTo("assignedTo", UserData.id).get()
            .addOnSuccessListener { documents ->
                var tasks: MutableList<TaTasksModel> = mutableListOf()

                if (documents.isEmpty) {
                    tasksEmptyTextView.visibility = View.VISIBLE
                    showMoreTasksTextView.visibility = View.GONE
                } else {
                    var limit = 3;
                    if (showAll) {
                        limit = 100;
                    }
                    var index = 1;

                    if (documents.size() > 3) {
                        showMoreTasksTextView.visibility = View.VISIBLE
                    }
                    tasksEmptyTextView.visibility = View.GONE
                    for (document in documents) {
                        val task: TaTasksModel = document.toObject(TaTasksModel::class.java)
                        task.id = document.id
                        tasks.add(task)
                        if (++index > limit) {
                            break;
                        }
                    }
                }
                adapter.updateTasks(tasks)
            }
    }
}