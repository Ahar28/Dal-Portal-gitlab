package com.example.dalportal.model

data class Assignment(
    val name: String = "",
    val deadline: String = "",
    var completionStatus: String = "",
    var score: String = "",
    var fileUrl: String = "",
    var studentId: String = ""
)
