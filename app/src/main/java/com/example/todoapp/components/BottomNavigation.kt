package com.example.todoapp.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.todoapp.screens.calendar.Calendar
import com.example.todoapp.screens.menuApp.MenuMainScreen
import com.example.todoapp.screens.profile.ProfileMainScreen
import com.example.todoapp.screens.tasks.TasksMainScreen

@Composable
fun NavHostConteiner (
    navController: NavHostController,
    padding : PaddingValues
){
    NavHost(
        navController = navController,
        startDestination = "Tasks",
        modifier = Modifier.padding(paddingValues = padding),
        builder = {
            // route : tasks
            composable("Tasks") {
                TasksMainScreen()
            }

            // route : calendar
            composable("Calendar") {
                Calendar()
            }
            // route : mine
            composable("Mine") {
                ProfileMainScreen()
            }
            // route : menu
            composable("Menu") {
                MenuMainScreen()
            }
        }
    )
}
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    println("BottomNavigationBar вызван!")
    NavigationBar(
        modifier = Modifier.padding(top = 10.dp).clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        // set background color
        containerColor = MaterialTheme.colorScheme.surface) {

        // observe the backstack
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        // observe current route to change the icon
        // color,label color when navigated
        val currentRoute = navBackStackEntry?.destination?.route

        // Bottom nav items we declared
        Constants.BottomNavItems.forEach { navItem ->

            // Place the bottom nav items
            NavigationBarItem(

                // it currentRoute is equal then its selected route
                selected = currentRoute == navItem.route,

                // navigate on click
                onClick = {
                    navController.navigate(navItem.route)
                },

                // Icon of navItem
                icon = {
                    Icon(painterResource(id = navItem.icon), contentDescription = navItem.label)
                },

                // label
                label = {
                    Text(text = navItem.label)
                },
                alwaysShowLabel = false,

                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary, // Icon color when selected
                    unselectedIconColor = MaterialTheme.colorScheme.secondary, // Icon color when not selected
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary, // Label color when selected
                    indicatorColor = MaterialTheme.colorScheme.primary // Highlight color for selected item
                )
            )
        }
    }
}