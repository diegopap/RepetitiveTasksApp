package com.github.diegopap.repetitivetasksapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.diegopap.repetitivetasksapp.ui.screens.Screen
import com.github.diegopap.repetitivetasksapp.ui.screens.add_edit_task.AddEditTaskScreen
import com.github.diegopap.repetitivetasksapp.ui.screens.tasks.TasksScreen
import com.github.diegopap.repetitivetasksapp.ui.theme.RepetitiveTasksAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RepetitiveTasksAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.TasksScreen.route
                    ) {
                        composable(route = Screen.TasksScreen.route) {
                            TasksScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditTaskScreen.route +
                                    "?taskId={taskId}&taskColor={taskColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "taskId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "taskColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                            )
                        ) {
                            val color = it.arguments?.getInt("taskColor") ?: -1
                            AddEditTaskScreen(
                                navController = navController,
                                taskColor = color
                            )
                        }
                    }
                }
            }
        }
    }
}
