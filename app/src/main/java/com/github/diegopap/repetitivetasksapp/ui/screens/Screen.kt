package com.github.diegopap.repetitivetasksapp.ui.screens

sealed class Screen(val route: String) {
    data object TasksScreen: Screen("tasks_screen")
    data object AddEditTaskScreen: Screen("add_edit_task_screen")
}