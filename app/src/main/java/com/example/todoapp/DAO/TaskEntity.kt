package com.example.todoapp.DAO

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val content: String,
    val category: String?,
    val timeCategory: String?,
    val taskCreatedTime: String?,
    val reminderTime: String?,
    val dateTime: String?,
    val userToken: String
)
