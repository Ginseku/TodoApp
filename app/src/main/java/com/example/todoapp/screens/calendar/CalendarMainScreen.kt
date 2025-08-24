package com.example.todoapp.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.API.TaskApi
import com.example.todoapp.DAO.AppDatabase
import com.example.todoapp.R
import com.example.todoapp.components.CreateTaskButton
import com.example.todoapp.screens.Autentification.TokenManager
import com.example.todoapp.screens.Autentification.network.RetrofitInstance
import com.example.todoapp.screens.tasks.dialog.ViewModelFactory
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

val productSans = FontFamily(
    Font(R.font.product_sans_regular, FontWeight.Normal)
)

val tasksByDate = mapOf(
    LocalDate.of(2025, 3, 22) to listOf("Task 1", "Task 2", "Task 3"),
    LocalDate.of(2025, 3, 23) to listOf("Task A", "Task B", "Task C"),
    LocalDate.of(2025, 3, 24) to listOf(
        "Task X",
        "Task Y",
        "Task Z",
        "Task S",
        "Task D",
        "Task F",
        "Task H",
        "Task J",
        "Task K"
    )
)

@Composable
fun Calendar(
) {
    val currentMonth = remember { YearMonth.now() }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var listExpanded by remember { mutableStateOf(false) }
    val startMonth = currentMonth.minusYears(30)
    val endMonth = currentMonth.plusYears(30)
    val context = LocalContext.current
    val tokenManager = remember { TokenManager(context) }
    val userToken = tokenManager.getToken() ?: ""


    val dao = remember { AppDatabase.getInstance(context).categoryDao() }
    val calendarViewModel: CalendarViewModel = viewModel(
        factory = ViewModelFactory(
            dao = dao,
            context = context,
            userToken = userToken,
            taskApi = RetrofitInstance.taskApi
        )
    )

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstDayOfWeek = DayOfWeek.MONDAY,
        firstVisibleMonth = currentMonth // using this for not return back after see new month
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        HorizontalCalendar(
            state = state,


            monthHeader = { calendarMonth ->
                val month = calendarMonth.yearMonth // Получаем YearMonth
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                                .height(110.dp)
                                .background(MaterialTheme.colorScheme.surface),
                            Arrangement.Start,
                            Alignment.CenterVertically
                        ) {
                            Button(modifier = Modifier
                                .padding(start = 16.dp)
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(8.dp)
                                ),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),

                                onClick = { }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.open_calendar),
                                    contentDescription = stringResource(id = R.string.expand),
//                                    modifier = Modifier.rotate(if (titleExpanded) 180f else 0f)
                                )
                            }
                            Text(
                                text = month.format(
                                    DateTimeFormatter.ofPattern(
                                        "MMMM yyyy",
                                        Locale.getDefault()
                                    )
                                ),
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }

                    DaysOfWeekTitle(daysOfWeek = daysOfWeek())
                }

            },


            dayContent = { day ->
                val isSelected = selectedDate == day.date
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(40.dp)
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { selectedDate = day.date }, contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = day.date.dayOfMonth.toString(),
                        fontSize = 22.sp,
                        fontFamily = productSans,
                        color = if (day.position == DayPosition.MonthDate) Color.White else Color.Gray
                    )
                }
            },

            )


        selectedDate?.let { date ->
            Text(
                "Tasks for: ${date.format(DateTimeFormatter.ISO_DATE)}",
                modifier = Modifier.padding(16.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            // Проверяем, есть ли задачи для этой даты
            val tasks = tasksByDate[date] ?: emptyList()

            if (tasks.isEmpty()) {
                Text(stringResource(id = R.string.no_tasks_for_this_date), modifier = Modifier.padding(16.dp))
            } else {
                val displayedItems = if (listExpanded) tasks else tasks.take(3)
                Column {
                    displayedItems.forEach { task ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(45.dp)
                                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
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
                                    text = task, fontSize = 16.sp
                                )
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

                                Text(
                                    text = if (listExpanded) stringResource(id = R.string.see_less) else stringResource(id = R.string.see_more),
                                    color = Color.White,
                                    modifier = Modifier.weight(1f)
                                )
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
        CreateTaskButton(dao = dao, context = context, taskApi = RetrofitInstance.taskApi)
    }
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                fontSize = 24.sp,
                color = Color.Gray,
            )
        }
    }
}


