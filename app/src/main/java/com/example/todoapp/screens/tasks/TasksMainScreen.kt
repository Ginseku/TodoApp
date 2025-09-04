package com.example.todoapp.screens.tasks

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.DAO.AppDatabase
import com.example.todoapp.DAO.TaskDto
import com.example.todoapp.R
import com.example.todoapp.components.CreateTaskButton
import com.example.todoapp.components.Header
import com.example.todoapp.screens.Autentification.network.RetrofitInstance
import com.example.todoapp.screens.tasks.dialog.ViewModelFactory


@Composable
fun TasksMainScreen(savedToken: String) {
    val context = LocalContext.current
    val taskDao = remember { AppDatabase.getInstance(context).taskDao() }
    val categoryDao = remember { AppDatabase.getInstance(context).categoryDao() }

    // Создаём ViewModel через фабрику
    val viewModel: TasksViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(
            context = context,
            userToken = savedToken,
            taskApi = RetrofitInstance.taskApi,
            taskDao = taskDao,
            categoryDao = categoryDao
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






