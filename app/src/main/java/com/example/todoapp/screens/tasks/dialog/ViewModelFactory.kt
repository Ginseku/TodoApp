package com.example.todoapp.screens.tasks.dialog

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.API.TaskApi
import com.example.todoapp.DAO.CategoryDao
import com.example.todoapp.DAO.TaskDao
import com.example.todoapp.screens.calendar.CalendarViewModel
import com.example.todoapp.screens.tasks.TasksViewModel

class ViewModelFactory(
    private val categoryDao: CategoryDao,
    private val taskDao: TaskDao,
    private val context: Context,
    private val userToken: String,
    private val taskApi: TaskApi
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CategoryViewModel::class.java) -> {
                CategoryViewModel(categoryDao, userToken) as T
            }
            modelClass.isAssignableFrom(TasksViewModel::class.java) -> {
                TasksViewModel(taskApi, taskDao, userToken) as T
            }
            modelClass.isAssignableFrom(CalendarViewModel::class.java) -> {
                CalendarViewModel() as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
        }
    }
}
