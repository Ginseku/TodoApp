package com.example.yourapp.ui.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoapp.DAO.CategoryEntity
import com.example.todoapp.screens.tasks.dialog.CategoryViewModel

@Composable
fun CategoryDialog(
    onDismiss: () -> Unit,
    categoryViewModel: CategoryViewModel,
    onCategorySelected: (String) -> Unit
) {
    var showCreateCategoryDialog by remember { mutableStateOf(false) }
    val categories by categoryViewModel.categories.collectAsState()

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Category", style = MaterialTheme.typography.titleLarge) },
        text = {
            Column {
                LazyColumn {
                    items(categories) { category: CategoryEntity ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {
                                    onCategorySelected(category.name)
                                    onDismiss()
                                },
                            shape = MaterialTheme.shapes.medium,
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = when (category.name) {
                                        "No category" -> Icons.Default.Clear
                                        "Work" -> Icons.Default.Work
                                        "Personal" -> Icons.Default.Person
                                        "Study" -> Icons.Default.School
                                        else -> Icons.Default.Folder
                                    },
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(category.name, style = MaterialTheme.typography.bodyLarge)
                                if (category.userToken != "default") {  // дефолтные категории нельзя удалять
                                    IconButton(onClick = { categoryViewModel.deleteCategory(category) }) {
                                        Icon(
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = "Delete",
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = { showCreateCategoryDialog = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Create Category")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Close")
            }
        }
    )

    if (showCreateCategoryDialog) {
        CreateCategoryDialog(
            onDismiss = { showCreateCategoryDialog = false },
            categoryViewModel = categoryViewModel
        )
    }
}

@Composable
fun CreateCategoryDialog(
    onDismiss: () -> Unit,
    categoryViewModel: CategoryViewModel
) {
    var text by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("New category") },
        text = {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Type category name") }
            )
        },
        confirmButton = {
            TextButton(onClick = {
                if (text.isNotBlank()) {
                    categoryViewModel.addCategory(text)
                    onDismiss()
                }
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Close")
            }
        }
    )
}
