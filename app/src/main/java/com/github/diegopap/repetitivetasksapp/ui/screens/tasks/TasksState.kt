package com.github.diegopap.repetitivetasksapp.ui.screens.tasks

import com.github.diegopap.repetitivetasksapp.domain.model.Task
import com.github.diegopap.repetitivetasksapp.domain.util.OrderType
import com.github.diegopap.repetitivetasksapp.domain.util.TaskOrder

data class TasksState (
    val tasks: List<Task> = emptyList(),
    val taskOrder: TaskOrder = TaskOrder.Created(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)