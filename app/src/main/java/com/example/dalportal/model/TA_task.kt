package com.example.dalportal.model

import java.security.Timestamp

data class TA_task(
    val description: String,
    val deadline: Timestamp,
    val priority: String,
    val assignedTo: String,
    val status: String
)
