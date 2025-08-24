package com.example.todoapp.screens.tasks

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.DAO.AppDatabase
import com.example.todoapp.R
import com.example.todoapp.components.CreateTaskButton
import com.example.todoapp.components.Header
import com.example.todoapp.screens.Autentification.network.RetrofitInstance


@Composable
fun TasksMainScreen() {
    val context = LocalContext.current
    val dao = remember { AppDatabase.getInstance(context).categoryDao() }
    val userToken = remember { "user_token_из_логина" }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Header(true)
        TaskLists(modifier = Modifier.padding(top = 16.dp))

        Spacer(modifier = Modifier.weight(1f))

        CreateTaskButton(dao = dao, context = context, taskApi = RetrofitInstance.taskApi)
    }
}



@Composable
fun TaskLists(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        ExpandableTaskList("Previous Tasks", listOf("Task 1", "Task 2", "Task 3", "Task 4", "Task 5", "Task 6", "Task 7", "Task 8"))
        ExpandableTaskList("Current Tasks", listOf("Task A", "Task B", "Task C", "Task D", "Task E", ))
        ExpandableTaskList("Future Tasks", listOf("Task X", "Task Y", "Task Z", "Task W", "Task G","Task H", "Task J", "Task K", "Task L", "Task Q"))
        ExpandableTaskList("All Tasks", (1..10).map { "Task $it" })
    }
}
//Open tasks lists
@SuppressLint("SuspiciousIndentation")
@Composable
fun ExpandableTaskList(title: String, tasks: List<String>) {
    var titleExpanded by remember { mutableStateOf(false) }
    var listExpanded by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 8.dp,end = 16.dp, bottom = 8.dp)) {
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
                                .height(45.dp)
                                .padding(top = 8.dp)
                                .clickable { /* Обработчик клика */ },
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, top = 8.dp, end = 16.dp),
                                verticalAlignment = Alignment.CenterVertically // Выравнивание по центру по вертикали
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.options_button),
                                    contentDescription = stringResource(id = R.string.option_button),
                                    modifier = Modifier.padding(end = 15.dp), // Небольшой отступ после иконки
                                    tint = Color.White
                                )
                                Text(
                                    text = task,
                                    fontSize = 16.sp
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
                            onClick = {
                                listExpanded = !listExpanded
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent, // Прозрачный фон кнопки
                                contentColor = Color.White // Цвет текста и иконки
                            ),
                            elevation = ButtonDefaults.buttonElevation(0.dp) // Убираем тень
                        ) {

                            Text(text = if (listExpanded) stringResource(id = R.string.see_less) else stringResource(id = R.string.see_more), color = Color.White,
                                modifier = Modifier.weight(1f))
                            Icon(
                                painter = painterResource(id = R.drawable.open_more_menu),
                                contentDescription = "",
                                modifier = Modifier.rotate(
                                    if (listExpanded) 180f else 0f
                                ),
                                tint = Color.White
                            )
                        }
                    }
                }
            }

    }
}



