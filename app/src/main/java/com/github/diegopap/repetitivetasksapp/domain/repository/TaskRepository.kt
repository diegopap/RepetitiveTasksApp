package com.github.diegopap.repetitivetasksapp.domain.repository

import com.github.diegopap.repetitivetasksapp.domain.model.Task
import com.github.diegopap.repetitivetasksapp.domain.model.TaskWithExecutionHistory
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getTasks(orderBy: String): Flow<List<Task>>

    suspend fun getTaskById(id: Int): TaskWithExecutionHistory?

    suspend fun insertTask(task: Task)

    suspend fun deleteTask(task: Task)

}