package com.example.todoapp.screens.tasks

import com.example.todoapp.DAO.TaskDto
import com.example.todoapp.screens.Autentification.network.RetrofitInstance

class TaskRepository {
    suspend fun getTasks(token: String): List<TaskDto> {
        return RetrofitInstance.taskApi.getAllTasks("Bearer $token")
    }
}