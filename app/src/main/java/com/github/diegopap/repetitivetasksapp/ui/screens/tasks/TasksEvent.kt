package com.github.diegopap.repetitivetasksapp.ui.screens.tasks

import com.github.diegopap.repetitivetasksapp.domain.model.Task
import com.github.diegopap.repetitivetasksapp.domain.util.TaskOrder

sealed class TasksEvent {
    data class Order(val taskOrder: TaskOrder): TasksEvent()
    data class DeleteTask(val task: Task): TasksEvent()
    data class ExecuteTask(val task: Task): TasksEvent()
    data object RestoreTask: TasksEvent()
    data object RestoreExecution: TasksEvent()
    data object ToggleOrderSection: TasksEvent()
}
