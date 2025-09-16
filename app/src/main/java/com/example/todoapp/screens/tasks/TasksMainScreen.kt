package com.example.todoapp.screens.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.todoapp.DAO.AppDatabase
import com.example.todoapp.components.CreateTaskButton
import com.example.todoapp.components.Header
import com.example.todoapp.components.parser.getUserIdFromToken
import com.example.todoapp.screens.Autentification.network.RetrofitInstance
import com.example.todoapp.screens.tasks.dialog.ViewModelFactory


@Composable
fun TasksMainScreen(savedToken: String) {
    val context = LocalContext.current
    val taskDao = remember { AppDatabase.getInstance(context).taskDao() }
    val categoryDao = remember { AppDatabase.getInstance(context).categoryDao() }
    val userId = getUserIdFromToken(savedToken) ?: ""
    // Создаём ViewModel через фабрику
    val viewModel: TasksViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(
            context = context,
            userToken = savedToken,
            taskApi = RetrofitInstance.taskApi,
            taskDao = taskDao,
            categoryDao = categoryDao,
            userId = userId
        )
    )
    val tasks by viewModel.tasks.collectAsState()

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Header(true)

        TaskScreen(
            factory = ViewModelFactory(
                categoryDao = categoryDao,
                taskDao = taskDao,
                context = LocalContext.current,
                userToken = savedToken,  // здесь твой JWT токен
                taskApi = RetrofitInstance.taskApi,
                userId = userId
            )

        )

        Spacer(modifier = Modifier.weight(1f))

        // кнопка создания задачи
        CreateTaskButton(
            categoryDao = categoryDao,
            taskDao = taskDao,
            context = context,
            taskApi = RetrofitInstance.taskApi,
            onTaskCreated = { newTaskDto ->
                viewModel.createTask(
                    task = newTaskDto,
                    onSuccess = { /* UI обновится автоматически через collectAsState */ },
                    onError = { println("Ошибка: $it") }
                )
            }
        )
    }


}






