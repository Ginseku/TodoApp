package com.example.todoapp.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.todoapp.DAO.TaskDao

@Composable
fun TaskCategoriesSection(taskDao: TaskDao) {

    val categoryCounts by taskDao.getTasksCountByCategory()
        .collectAsState(initial = emptyList())

    // Сопоставим каждой категории цвет
    val categoryColors = mapOf(
        "Work" to Color(0xFF4285F4),
        "Personal" to Color(0xFF34A853),
        "Birthday" to Color(0xFFEA4335),
        "Wishlist" to Color(0xFFFBBC05),
        "No category" to Color(0xFF9E9E9E)
    )

    val categories = categoryCounts.map { cc ->
        TaskCategory(
            name = cc.category,
            count = cc.count,
            color = categoryColors[cc.category] ?: colorFromCategory(cc.category) // если категории нет в словаре
        )
    }

    val totalTasks = categories.sumOf { it.count }

    var expanded by remember { mutableStateOf(false) }
    var selectedPeriod by remember { mutableStateOf("30 Days") }

    Box(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        TaskCategoriesCard(
            totalTasks = totalTasks,
            period = selectedPeriod,
            categories = categories,
            onPeriodClick = { expanded = true }
        )

        PeriodDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            onPeriodSelected = {
                selectedPeriod = it
                expanded = false
            }
        )
    }
}

@Composable
fun TaskCategoriesCard(
    totalTasks: Int,
    period: String,
    categories: List<TaskCategory>,
    modifier: Modifier = Modifier,
    onPeriodClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
                .height(600.dp)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally // Центрируем содержимое
        ) {
            // Header
            Row {
                Text(
                    text = "Pending Tasks In Categories",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier, // Выравниваем по левому краю
                )

                Spacer(modifier = Modifier.height(16.dp))
                Spacer(modifier = Modifier.width(70.dp))

                // Период
                Text(
                    text = "In $period",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .clickable { onPeriodClick() },
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }


            Spacer(modifier = Modifier.height(16.dp))
            if (categories.isEmpty()) {
                // Заглушка, если нет данных
                Text(
                    text = "Нет задач для отображения",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            } else {
                // Основное содержимое
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start // Центрируем по горизонтали
                ) {
                    // Donut Chart
                    Box(
                        modifier = Modifier.size(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        DonutPieChart(
                            modifier = Modifier.size(200.dp),
                            pieChartData = PieChartData(
                                slices = categories.map {
                                    PieChartData.Slice(it.name, it.count.toFloat(), it.color)
                                },
                                plotType = PlotType.Donut,

                                ),
                            pieChartConfig = PieChartConfig(
                                strokeWidth = 13f,
                                isAnimationEnable = true,
                                showSliceLabels = false,
                                activeSliceAlpha = 0.9f,
                                chartPadding = 2,
                                backgroundColor = Color.Transparent
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))


                    // Текстовая часть
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = totalTasks.toString(),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = "Total Tasks",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 200.dp) // ограничение по высоте (можешь менять)
                        ) {
                            items(categories) { category ->
                                Text(
                                    text = "• ${category.name} ${category.count}",
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(vertical = 2.dp),
                                    color = category.color
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


        }
    }
}


@Composable
fun PeriodDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onPeriodSelected: (String) -> Unit,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,

        ) {
        DropdownMenuItem(
            text = { Text("7 Days") },
            onClick = { onPeriodSelected("7 Days") }
        )
        DropdownMenuItem(
            text = { Text("30 Days") },
            onClick = { onPeriodSelected("30 Days") }
        )
        DropdownMenuItem(
            text = { Text("All Time") },
            onClick = { onPeriodSelected("All Time") }
        )
    }
}

data class TaskCategory(
    val name: String,
    val count: Int,
    val color: Color
)

fun colorFromCategory(name: String): Color {
    val hash = name.hashCode()
    val r = (hash shr 16 and 0xFF)
    val g = (hash shr 8 and 0xFF)
    val b = (hash and 0xFF)
    return Color(r, g, b)
}