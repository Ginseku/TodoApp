import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.todoapp.DAO.TaskDto
import com.example.todoapp.screens.tasks.TasksViewModel
import com.example.todoapp.screens.tasks.dialog.CategoryViewModel
import com.example.yourapp.ui.category.CategoryDialog
import com.vanpra.composematerialdialogs.*
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun TaskDialog(
    tasksViewModel: TasksViewModel,
    categoryViewModel: CategoryViewModel,
    onDismiss: () -> Unit
) {

    var text by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedTime by remember { mutableStateOf<LocalTime?>(null) }
    var selectedCategory by remember { mutableStateOf("No category") }
    var showCategoryDialog by remember { mutableStateOf(false) }
    var content by remember { mutableStateOf("") }

    val context = LocalContext.current
    val categories by categoryViewModel.categories.collectAsState()
    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = 8.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Создать задачу", fontSize = 20.sp)

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Название задачи") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Описание задачи") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )

                Spacer(Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = { dateDialogState.show() }) {
                        Text("Дата и время")
                    }
                    Button(onClick = { showCategoryDialog = true }) {
                        Text(selectedCategory)
                    }
                }

                Spacer(Modifier.height(16.dp))

                if (selectedDate != null && selectedTime != null) {
                    val dateTime = LocalDateTime.of(selectedDate, selectedTime)
                    val formatted =
                        dateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm"))
                    Text("Выбрано: $formatted", fontSize = 14.sp)
                }

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (text.isBlank()) {
                            Toast.makeText(context, "Введите название задачи", Toast.LENGTH_SHORT).show()
                        } else {
                            val dateTime = if (selectedDate != null && selectedTime != null) {
                                LocalDateTime.of(selectedDate, selectedTime)
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                            } else null

                            val task = TaskDto(
                                noteId = null,
                                title = text,
                                content = content,
                                category = selectedCategory,
                                timeCategory = null,
                                taskCreatedTime = LocalDateTime.now()
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                                reminderTime = null,
                                dateTime = dateTime
                            )

                            tasksViewModel.createTask(
                                task,
                                onSuccess = {
                                    Toast.makeText(context, "Задача сохранена", Toast.LENGTH_SHORT).show()
                                    onDismiss()
                                },
                                onError = { error ->
                                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Сохранить")
                }


            }
        }
    }

    // Диалог выбора категории
    if (showCategoryDialog) {
        CategoryDialog(
            categoryViewModel = categoryViewModel,
            onCategorySelected = { selectedCategory = it },
            onDismiss = { showCategoryDialog = false }
        )
    }

    // Диалог выбора даты
    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton("OK") { timeDialogState.show() }
            negativeButton("Cancel")
        }
    ) {
        datepicker { date -> selectedDate = date }
    }

    // Диалог выбора времени
    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton("OK")
            negativeButton("Cancel")
        }
    ) {
        timepicker { time -> selectedTime = time }
    }
}
