package com.github.diegopap.repetitivetasksapp.ui.screens.add_edit_task

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.diegopap.repetitivetasksapp.domain.model.ExecutionHistory
import com.github.diegopap.repetitivetasksapp.domain.model.InvalidTaskException
import com.github.diegopap.repetitivetasksapp.domain.model.Task
import com.github.diegopap.repetitivetasksapp.domain.repository.ExecutionHistoryRepository
import com.github.diegopap.repetitivetasksapp.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val executionHistoryRepository: ExecutionHistoryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _taskTitle = mutableStateOf(
        TextFieldState(
        hint = "Enter title..."
    )
    )
    val taskTitle: State<TextFieldState> = _taskTitle

    private val _executionHistory = mutableStateOf(
        listOf<ExecutionHistory>()
    )
    val executionHistory: State<List<ExecutionHistory>> = _executionHistory

    private val _taskColor = mutableIntStateOf(Task.taskColors.random().toArgb())
    val taskColor: State<Int> = _taskColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentTaskId: Long? = null

    init {
        savedStateHandle.get<Int>("taskId")?.let { taskId ->
            if(taskId != -1) {
                viewModelScope.launch(Dispatchers.IO) {
                    taskRepository.getTaskById(taskId)?.also { taskWithExecutionHistory ->
                        withContext(Dispatchers.Main) {
                            currentTaskId = taskWithExecutionHistory.task.id
                            _taskTitle.value = taskTitle.value.copy(
                                text = taskWithExecutionHistory.task.title,
                                isHintVisible = false
                            )
                            _executionHistory.value = taskWithExecutionHistory.executionHistory
                            _taskColor.intValue = taskWithExecutionHistory.task.color
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditTaskEvent) {
        when(event) {
            is AddEditTaskEvent.EnteredTitle -> {
                _taskTitle.value = taskTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditTaskEvent.ChangeTitleFocus -> {
                _taskTitle.value = taskTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            taskTitle.value.text.isBlank()
                )
            }
            is AddEditTaskEvent.DeletedExecutionHistory -> {
                viewModelScope.launch(Dispatchers.IO) {
                    executionHistoryRepository.deleteExecutionHistory(event.executionHistory)
                    _executionHistory.value = executionHistory.value.minus(event.executionHistory)
                }
            }
            is AddEditTaskEvent.ChangeColor -> {
                _taskColor.intValue = event.color
            }
            is AddEditTaskEvent.SaveTask -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        taskRepository.insertTask(
                            Task(
                                title = taskTitle.value.text,
                                executed = executionHistory.value.firstOrNull()?.executed ?: -1,
                                created = System.currentTimeMillis(),
                                color = taskColor.value,
                                id = currentTaskId,
                                frequency = 1
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveTask)
                    } catch(e: InvalidTaskException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save task"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        data object SaveTask: UiEvent()
    }
}