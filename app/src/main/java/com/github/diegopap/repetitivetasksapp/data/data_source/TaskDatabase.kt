package com.github.diegopap.repetitivetasksapp.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.diegopap.repetitivetasksapp.domain.model.ExecutionHistory
import com.github.diegopap.repetitivetasksapp.domain.model.Task

@Database(
    entities = [Task::class, ExecutionHistory::class],
    version = 1
)
abstract class TaskDatabase: RoomDatabase() {

    abstract val taskDao: TaskDao
    abstract val executionHistoryDao: ExecutionHistoryDao

    companion object {
        const val DATABASE_NAME = "task_db"
    }
}