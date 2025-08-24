package com.example.todoapp.DAO

data class TaskDto(
    val title: String,
    val content: String,
    val category: String?,
    val timeCategory: String?,
    val taskCreatedTime: String?,
    val reminderTime: String?,
    val dateTime: String?
)
