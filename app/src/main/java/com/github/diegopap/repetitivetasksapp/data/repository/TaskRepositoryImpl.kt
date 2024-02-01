package com.github.diegopap.repetitivetasksapp.data.repository

import com.github.diegopap.repetitivetasksapp.data.data_source.TaskDao
import com.github.diegopap.repetitivetasksapp.domain.model.InvalidTaskException
import com.github.diegopap.repetitivetasksapp.domain.model.Task
import com.github.diegopap.repetitivetasksapp.domain.model.TaskWithExecutionHistory
import com.github.diegopap.repetitivetasksapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {

    override fun getTasks(orderBy: String): Flow<List<Task>> = taskDao.getTasks(orderBy)

    override suspend fun getTaskById(id: Int): TaskWithExecutionHistory? = taskDao.getTaskById(id)

    override suspend fun insertTask(task: Task) {
        if (task.title.isBlank()) {
            throw InvalidTaskException("Title cannot be empty.")
        } else {
            taskDao.insertTask(task)
        }
    }

    override suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
}