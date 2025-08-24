package com.example.todoapp.API

import com.example.todoapp.DAO.TaskDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TaskApi {
    @POST("task/create")
    suspend fun createTask(
        @Header("Authorization") token: String,
        @Body task: TaskDto
    ): TaskDto

    @GET("task/getAll")
    suspend fun getAllTasks(
        @Header("Authorization") token: String
    ): List<TaskDto>
}

