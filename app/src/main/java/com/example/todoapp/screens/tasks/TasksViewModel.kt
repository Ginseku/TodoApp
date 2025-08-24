package com.example.todoapp.screens.tasks;

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.API.TaskApi
import com.example.todoapp.DAO.TaskDto
import kotlinx.coroutines.launch

class TasksViewModel(
    private val api: TaskApi,
    private val token: String
) : ViewModel() {

    var tasks by mutableStateOf<List<TaskDto>>(emptyList())
        private set

    fun loadTasks() {
        viewModelScope.launch {
            try {
                tasks = api.getAllTasks("Bearer $token")
            } catch (e: Exception) {
                // Обработка ошибки
            }
        }
    }

    fun createTask(task: TaskDto, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                api.createTask("Bearer $token", task)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Ошибка создания задачи")
            }
        }
    }
}

