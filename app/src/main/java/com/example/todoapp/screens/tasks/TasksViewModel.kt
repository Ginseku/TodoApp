package com.example.todoapp.screens.tasks;

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.API.TaskApi
import com.example.todoapp.DAO.TaskDao
import com.example.todoapp.DAO.TaskDto
import com.example.todoapp.DAO.TaskEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TasksViewModel(
    private val api: TaskApi,
    private val dao: TaskDao,
    private val token: String
) : ViewModel() {

    val tasks: StateFlow<List<TaskEntity>> = dao.getAllTasks(token)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    init {
        refreshFromApi()
    }

    fun refreshFromApi() {
        viewModelScope.launch {
            try {
                val apiTasks = api.getAllTasks("Bearer $token")
                val entities = apiTasks.map { it.toEntity(token) }

                // очистка + вставка (или просто вставка с REPLACE)
                dao.insertAll(entities)
            } catch (e: Exception) {
                println("Ошибка синхронизации: ${e.message}")
            }
        }
    }

    fun syncTasks() {
        viewModelScope.launch {
            try {
                val apiTasks = api.getAllTasks("Bearer $token")
                // конвертируем TaskDto -> TaskEntity и вставляем в БД
                apiTasks.forEach { dto ->
                    dao.insertTask(dto.toEntity(token))
                }
            } catch (e: Exception) {
                // обработка ошибки
            }
        }
    }

    fun createTask(task: TaskDto, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val created = api.createTask("Bearer $token", task)
                // сохраняем в БД
                dao.insertTask(created.toEntity(token))
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Ошибка создания задачи")
            }
        }
    }
    fun deleteTask(taskId: Int, onError: (String) -> Unit = {}) {
        viewModelScope.launch {
            try {
                // 1. Удаляем на сервере
                api.deleteTask("Bearer $token", taskId)

                // 2. Если успешно, удаляем локально
                dao.deleteTask(taskId)

            } catch (e: Exception) {
                onError(e.message ?: "Ошибка при удалении")
            }
        }
    }
}


// 🔹 Маппер TaskDto -> TaskEntity
private fun TaskDto.toEntity(userToken: String): TaskEntity {
    return TaskEntity(
        id = this.noteId ?:0,
        title = this.title,
        content = this.content,
        category = this.category,
        timeCategory = this.timeCategory,
        taskCreatedTime = this.taskCreatedTime,
        reminderTime = this.reminderTime,
        dateTime = this.dateTime,
        userToken = userToken
    )
}