package com.example.todoapp.components

import androidx.compose.material.icons.Icons
import com.example.todoapp.R

object Constants {
    val BottomNavItems = listOf(
        // Menu screen
        BottomNavigationItems(
            label = "Menu",
            icon = R.drawable.menu,
            route = "Menu"
        ),
        // Tasks screen
        BottomNavigationItems(
            label = "Tasks",
            icon = R.drawable.tasks_unselected,
            route = "Tasks"
        ),
        // Calendar screen
        BottomNavigationItems(
            label = "Calendar",
            icon = R.drawable.calendar,
            route = "Calendar"
        ),
        // Mine account screen
        BottomNavigationItems(
            label = "Mine",
            icon = R.drawable.mine_side,
            route = "Mine"
        ),
        // Registration screen
        BottomNavigationItems(
            label = "Registration",
            icon = R.drawable.baseline_app_registration_24,
            route = "Registration"

        )
    )
}