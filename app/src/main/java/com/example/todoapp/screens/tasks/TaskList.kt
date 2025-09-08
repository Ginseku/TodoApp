package com.example.todoapp.screens.tasks

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.DAO.TaskDto
import com.example.todoapp.DAO.TaskEntity
import com.example.todoapp.R


@Composable
fun TaskLists(tasks: List<TaskEntity>, modifier: Modifier = Modifier,viewModel: TasksViewModel) {
    Column(modifier = modifier) {
        Column(modifier = modifier) {
            ExpandableTaskList("All Tasks", tasks, viewModel)
        }
    }
}
//Open tasks lists
@SuppressLint("SuspiciousIndentation")
@Composable
fun ExpandableTaskList(title: String, tasks: List<TaskEntity>,viewModel: TasksViewModel) {
    var titleExpanded by remember { mutableStateOf(false) }
    var listExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
    ) {
        // Заголовок
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { titleExpanded = !titleExpanded },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Text(text = title, modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.open_more_menu),
                contentDescription = "Expand",
                modifier = Modifier.rotate(if (titleExpanded) 180f else 0f)
            )
        }

        if (titleExpanded) {
            val displayedItems = if (listExpanded) tasks else tasks.take(3)

            Column {
                displayedItems.forEach { task ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(top = 8.dp)
                            .clickable { /* обработчик клика */ },
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, top = 8.dp, end = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.options_button),
                                contentDescription = "Options",
                                modifier = Modifier.padding(end = 15.dp),
                                tint = Color.White
                            )
                            Column {
                                Text(
                                    text = task.title,
                                    fontSize = 16.sp
                                )
                                task.content?.let {
                                    Text(
                                        text = it,
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_delete_24),
                                contentDescription = "Delete",
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier
                                    .clickable {
                                        viewModel.deleteTask(task.id) { error ->
                                            println("Ошибка удаления: $error")
                                        }
                                    }
                            )
                        }

                    }
                }
            }

            if (tasks.size > 3) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        modifier = Modifier.width(130.dp),
                        onClick = { listExpanded = !listExpanded },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(0.dp)
                    ) {
                        Text(
                            text = if (listExpanded) "See Less" else "See More",
                            color = Color.White,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.open_more_menu),
                            contentDescription = "",
                            modifier = Modifier.rotate(if (listExpanded) 180f else 0f),
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}