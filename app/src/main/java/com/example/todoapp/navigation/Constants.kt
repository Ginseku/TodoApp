package com.example.todoapp.navigation

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
        )
    )
}