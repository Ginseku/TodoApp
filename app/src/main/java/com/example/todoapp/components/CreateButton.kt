package com.example.todoapp.components

import TaskDialog
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.todoapp.R

@Composable
fun CreateTaskButton(modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(false) }

    Box() {
        Button(
            onClick = {
                showDialog = true
            },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 5.dp)
                .fillMaxWidth()
                .height(60.dp)
        )
        {
            Icon(
                painter = painterResource(id = R.drawable.add_button),
                contentDescription = "Add Button",
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(text = "Create New Task", fontSize = 16.sp, color = Color.White)

        }
        if (showDialog) {
            TaskDialog(onDismiss = { showDialog = false })
            println("Dialog opened")
        }

    }

}