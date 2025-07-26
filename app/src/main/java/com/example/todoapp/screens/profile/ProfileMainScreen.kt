package com.example.todoapp.screens.profile


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.todoapp.R
import com.example.todoapp.components.Header
import com.example.todoapp.utilits.formatToSinglePrecision


@Composable
fun ProfileMainScreen() {
    Column {
        Header(showSearch = false)
        Spacer(modifier = Modifier.height(16.dp))
        Charts()
        Spacer(modifier = Modifier.height(16.dp))
        TaskCategoriesSection()
    }


}

@Composable
fun Charts() {
    val steps = 5


    val weekDays: List<String> = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sut", "Sun")
    val pointsData: List<Point> =
        listOf(
            Point(0f, 40f),
            Point(1f, 90f),
            Point(2f, 0f),
            Point(3f, 60f),
            Point(4f, 10f),
            Point(5f, 20f),
            Point(6f, 30f))

    Column {
        Row(modifier = Modifier
            .width(400.dp)
            .padding(start = 10.dp)
            .height(35.dp)
            .background(MaterialTheme.colorScheme.surface),
            verticalAlignment = Alignment.Bottom

        ) {
            Text(modifier = Modifier
                .padding(start = 17.dp),
                text = stringResource(id = R.string.completion_of_daily_tasks)
            )
        }

        val xAxisData = AxisData.Builder()
            .axisStepSize(100.dp)
            .backgroundColor(MaterialTheme.colorScheme.surface)
            .steps(pointsData.size - 1)
            .labelData { i -> weekDays[i] }
            .axisLineColor(MaterialTheme.colorScheme.secondary)
            .axisLabelColor(MaterialTheme.colorScheme.secondary)
            .labelAndAxisLinePadding(15.dp)
            .build()

        val yAxisData = AxisData.Builder()
            .steps(5)
            .backgroundColor(MaterialTheme.colorScheme.surface)
            .axisLineColor(Color.Green) // Цвет оси Y
            .axisLabelColor(Color.Green) // Цвет подписей оси Y
            .build()

        val lineChartData = LineChartData(
            linePlotData = LinePlotData(
                lines = listOf(
                    Line(
                        dataPoints = pointsData,
                        LineStyle(
                            color = MaterialTheme.colorScheme.primary
                        ),
                        IntersectionPoint(
                            color = Color.White
                        ),
                        SelectionHighlightPoint(MaterialTheme.colorScheme.primary),
                        ShadowUnderLine(
                            alpha = 0.5f,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.surface
                                )
                            ),
//                        color = MaterialTheme.colorScheme.surface

                        ),
                        SelectionHighlightPopUp(),
                    )
                ),
            ),

            xAxisData = xAxisData,
            yAxisData = yAxisData,
            gridLines = GridLines(
                color = MaterialTheme.colorScheme.secondary,
                enableVerticalLines = false,
                alpha = 0.1f,
                lineWidth = 0.3.dp

            ),
            backgroundColor = MaterialTheme.colorScheme.surface,
        )



        LineChart(
            modifier = Modifier
                .width(400.dp)
                .padding(start = 10.dp)
                .height(300.dp)
                .clip(RoundedCornerShape(bottomStart = 13.dp, bottomEnd = 13.dp)),
            lineChartData = lineChartData
        )

        Spacer(modifier = Modifier.height(16.dp))


    }


}


