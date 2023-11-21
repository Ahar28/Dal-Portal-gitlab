package com.example.dalportal.model

data class Assignment(
    val name: String = "",
    val deadline: String = "",
    var completionStatus: String = "",
    var score: Int = 0
)
