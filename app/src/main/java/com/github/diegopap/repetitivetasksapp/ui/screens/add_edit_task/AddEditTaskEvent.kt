package com.github.diegopap.repetitivetasksapp.ui.screens.add_edit_task

import androidx.compose.ui.focus.FocusState
import com.github.diegopap.repetitivetasksapp.domain.model.ExecutionHistory

sealed class AddEditTaskEvent{
    data class EnteredTitle(val value: String): AddEditTaskEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditTaskEvent()
    data class DeletedExecutionHistory(val executionHistory: ExecutionHistory): AddEditTaskEvent()
    data class ChangeColor(val color: Int): AddEditTaskEvent()
    data object SaveTask: AddEditTaskEvent()
}

