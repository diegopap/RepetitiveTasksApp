package com.github.diegopap.repetitivetasksapp.ui.screens.tasks

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.diegopap.repetitivetasksapp.domain.model.ExecutionHistory
import com.github.diegopap.repetitivetasksapp.domain.model.Task
import com.github.diegopap.repetitivetasksapp.domain.repository.ExecutionHistoryRepository
import com.github.diegopap.repetitivetasksapp.domain.repository.TaskRepository
import com.github.diegopap.repetitivetasksapp.domain.util.OrderType
import com.github.diegopap.repetitivetasksapp.domain.util.TaskOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val executionHistoryRepository: ExecutionHistoryRepository
) : ViewModel() {

    private val _state = mutableStateOf(TasksState())
    val state: State<TasksState> = _state

    private var recentlyDeletedTask: Task? = null
    private var recentlyExecutedTask: Task? = null
    private var recentlyHistoryExecution: ExecutionHistory? = null

    init {
        getTasks(TaskOrder.Created(OrderType.Descending))
    }

    fun onEvent(event: TasksEvent) {
        when (event) {
            is TasksEvent.Order -> {
                if (state.value.taskOrder::class == event.taskOrder::class &&
                    state.value.taskOrder.orderType == event.taskOrder.orderType
                ) {
                    return
                }
                getTasks(event.taskOrder)
            }
            is TasksEvent.DeleteTask -> {
                viewModelScope.launch(Dispatchers.IO) {
                    taskRepository.deleteTask(event.task)
                    recentlyDeletedTask = event.task
                }
            }
            is TasksEvent.RestoreTask -> {
                viewModelScope.launch(Dispatchers.IO) {
                    taskRepository.insertTask(recentlyDeletedTask ?: return@launch)
                    recentlyDeletedTask = null
                }
            }
            is TasksEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
            is TasksEvent.ExecuteTask -> {
                viewModelScope.launch(Dispatchers.IO) {
                    recentlyExecutedTask = event.task
                    val executed = Date().time
                    val task = event.task.copy(
                        executed = executed
                    )
                    taskRepository.insertTask(task)
                    task.id?.let {
                        taskId ->
                        val executionHistory = ExecutionHistory(
                            executed = executed,
                            taskId = taskId
                        )
                        executionHistoryRepository.insertExecutionHistory(executionHistory).let {
                            executionHistoryId ->
                            recentlyHistoryExecution = executionHistory.copy(
                                id = executionHistoryId
                            )
                        }

                    }

                }
            }
            is TasksEvent.RestoreExecution -> {
                viewModelScope.launch(Dispatchers.IO) {
                    taskRepository.insertTask(recentlyExecutedTask ?: return@launch)
                    recentlyExecutedTask = null
                    executionHistoryRepository.deleteExecutionHistory(recentlyHistoryExecution ?: return@launch)
                }
            }
        }
    }

    private fun getTasks(taskOrder: TaskOrder) {
        viewModelScope.launch(Dispatchers.IO) {
            val orderBy = taskOrder::class.simpleName + "," + taskOrder.orderType::class.simpleName
            taskRepository.getTasks(orderBy).collectLatest { tasks ->
                _state.value = state.value.copy(
                    tasks = tasks,
                    taskOrder = taskOrder
                )
            }
        }
    }

}