package com.example.todoapp.screens.tasks


import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.screens.tasks.dialog.ViewModelFactory

@Composable
fun TaskScreen(factory: ViewModelFactory) {
    val viewModel: TasksViewModel = viewModel(factory = factory)

    val tasks by viewModel.tasks.collectAsState(initial = emptyList())

    TaskLists(
        tasks = tasks, // здесь tasks уже List<TaskEntity>
        modifier = Modifier.padding(top = 16.dp)
    )
}