package com.example.todoapp.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.todoapp.DAO.AppDatabase
import com.example.todoapp.DAO.TaskEntity
import com.example.todoapp.R
import com.example.todoapp.components.CreateTaskButton
import com.example.todoapp.screens.Autentification.TokenManager
import com.example.todoapp.screens.Autentification.network.RetrofitInstance
import com.example.todoapp.screens.tasks.TasksViewModel
import com.example.todoapp.screens.tasks.dialog.ViewModelFactory
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

val productSans = FontFamily(
    Font(R.font.product_sans_regular, FontWeight.Normal)
)
@Composable
fun Calendar() {
    val currentMonth = remember { YearMonth.now() }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var listExpanded by remember { mutableStateOf(false) }
    var showCalendarDialog by remember { mutableStateOf(false) }


    val context = LocalContext.current
    val tokenManager = remember { TokenManager(context) }
    val userToken = tokenManager.getToken() ?: ""

    val taskDao = remember { AppDatabase.getInstance(context).taskDao() }
    val categoryDao = remember { AppDatabase.getInstance(context).categoryDao() }

    val tasksViewModel: TasksViewModel = viewModel(
        factory = ViewModelFactory(
            categoryDao = categoryDao,
            taskDao = taskDao,
            context = context,
            userToken = userToken,
            taskApi = RetrofitInstance.taskApi,
            userId = ""
        )
    )

    val state = rememberCalendarState(
        startMonth = currentMonth.minusYears(30),
        endMonth = currentMonth.plusYears(30),
        firstDayOfWeek = DayOfWeek.MONDAY,
        firstVisibleMonth = currentMonth
    )
    val mainCoroutineScope = rememberCoroutineScope()

    // ---------- СИНХРОНИЗАЦИЯ С ДИАЛОГОМ ----------
    // При выборе даты в диалоге основной календарь автоматически прокручивается на эту дату
    selectedDate?.let { date ->
        LaunchedEffect(date) {
            mainCoroutineScope.launch {
                state.scrollToMonth(YearMonth.from(date))
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        HorizontalCalendar(
            state = state,
            monthHeader = { calendarMonth ->
                val month = calendarMonth.yearMonth
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                                .height(110.dp)
                                .background(MaterialTheme.colorScheme.surface),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .border(1.dp, MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp)),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                                onClick = { showCalendarDialog = true }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.open_calendar),
                                    contentDescription = stringResource(id = R.string.expand)
                                )
                            }
                            Text(
                                text = month.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault())),
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                            if (showCalendarDialog) {
                                CalendarDialog(
                                    initialDate = selectedDate ?: LocalDate.now(),
                                    onDateSelected = {
                                        selectedDate = it
                                        showCalendarDialog = false
                                    },
                                    onDismissRequest = { showCalendarDialog = false }
                                )
                            }
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
                        .clickable { selectedDate = day.date },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = day.date.dayOfMonth.toString(),
                        fontSize = 22.sp,
                        fontFamily = productSans,
                        color = if (day.position == DayPosition.MonthDate) Color.White else Color.Gray
                    )
                }
            }
        )

        selectedDate?.let { date ->
            val tasksForSelectedDate by tasksViewModel.getTasksForDate(date).collectAsState(initial = emptyList())

            Text(
                "Tasks for: ${date.format(DateTimeFormatter.ISO_DATE)}",
                modifier = Modifier.padding(16.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            if (tasksForSelectedDate.isEmpty()) {
                Text(stringResource(id = R.string.no_tasks_for_this_date), modifier = Modifier.padding(16.dp))
            } else {
                val displayedItems = if (listExpanded) tasksForSelectedDate else tasksForSelectedDate.take(3)
                Column {
                    displayedItems.forEach { task -> TaskCard(task = task) }

                    if (tasksForSelectedDate.size > 3) {
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
                                    text = if (listExpanded) stringResource(id = R.string.see_less)
                                    else stringResource(id = R.string.see_more),
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

        CreateTaskButton(
            categoryDao = categoryDao,
            taskDao = taskDao,
            context = context,
            taskApi = RetrofitInstance.taskApi,
            onTaskCreated = {
                    newTaskDto ->
                tasksViewModel.createTask(
                    task = newTaskDto,
                    onSuccess = { /* UI обновится автоматически через collectAsState */ },
                    onError = { println("Ошибка: $it") }
                )
            }
        )
    }
}
@Composable
fun CalendarDialog(
    initialDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit
) {
    var dialogYearMonth by remember { mutableStateOf(YearMonth.from(initialDate)) }
    var showMonthDropdown by remember { mutableStateOf(false) }
    var showYearDropdown by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val calendarState = rememberCalendarState(
        startMonth = YearMonth.now().minusYears(30),
        endMonth = YearMonth.now().plusYears(30),
        firstDayOfWeek = DayOfWeek.MONDAY,
        firstVisibleMonth = dialogYearMonth
    )

    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(onClick = onDismissRequest) { Text("OK") }
        },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Верхняя панель с месяцем/годом и стрелками
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_left),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                dialogYearMonth = dialogYearMonth.minusMonths(1)
                                coroutineScope.launch {
                                calendarState.scrollToMonth(dialogYearMonth)
                                    }
                            }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Box {
                        Text(
                            text = dialogYearMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault()),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.clickable { showMonthDropdown = !showMonthDropdown }
                        )
                        if (showMonthDropdown) {
                            DropdownMenu(
                                expanded = true,
                                onDismissRequest = { showMonthDropdown = false }
                            ) {
                                Month.values().forEach { month ->
                                    DropdownMenuItem(
                                        text = { Text(month.name.lowercase().replaceFirstChar { it.uppercase() }) },
                                        onClick = {
                                            dialogYearMonth = YearMonth.of(dialogYearMonth.year, month)
                                            coroutineScope.launch {
                                                calendarState.scrollToMonth(dialogYearMonth)
                                            }
                                            showMonthDropdown = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Box {
                        Text(
                            text = dialogYearMonth.year.toString(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.clickable { showYearDropdown = !showYearDropdown }
                        )
                        if (showYearDropdown) {
                            DropdownMenu(
                                expanded = true,
                                onDismissRequest = { showYearDropdown = false }
                            ) {
                                val startYear = dialogYearMonth.year - 5
                                val endYear = dialogYearMonth.year + 4
                                (startYear..endYear).forEach { year ->
                                    DropdownMenuItem(
                                        text = { Text(year.toString()) },
                                        onClick = {
                                            dialogYearMonth = YearMonth.of(year, dialogYearMonth.month)
                                            coroutineScope.launch {
                                                calendarState.scrollToMonth(dialogYearMonth)
                                            }
                                            showYearDropdown = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.arrow_right),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                dialogYearMonth = dialogYearMonth.plusMonths(1)
                                coroutineScope.launch {
                                    calendarState.scrollToMonth(dialogYearMonth)
                                }
                            }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalCalendar(
                    state = calendarState,
                    dayContent = { day ->
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .clickable { onDateSelected(day.date) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(day.date.dayOfMonth.toString())
                        }
                    }
                )
            }
        }
    )
}
@Composable
fun TaskCard(task: TaskEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
            .clickable { },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 8.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.options_button),
                contentDescription = stringResource(id = R.string.option_button),
                modifier = Modifier.padding(end = 15.dp),
                tint = Color.White
            )
            Column {
                Text(task.title, fontSize = 16.sp)
                task.content?.let { Text(it, fontSize = 12.sp, color = Color.Gray) }
            }
        }
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

