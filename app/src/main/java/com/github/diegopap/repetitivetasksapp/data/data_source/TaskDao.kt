package com.github.diegopap.repetitivetasksapp.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.github.diegopap.repetitivetasksapp.domain.model.Task
import com.github.diegopap.repetitivetasksapp.domain.model.TaskWithExecutionHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM task"+
            " ORDER BY  "+
            "      CASE :orderBy WHEN 'Title,Ascending' THEN title END ASC," +
            "      CASE :orderBy WHEN 'Title,Descending' THEN title END DESC," +
            "      CASE :orderBy WHEN 'Created,Ascending' THEN created END ASC," +
            "      CASE :orderBy WHEN 'Created,Descending' THEN created END DESC," +
            "      CASE :orderBy WHEN 'Color,Ascending' THEN color END ASC," +
            "      CASE :orderBy WHEN 'Color,Descending' THEN color END DESC" )
    fun getTasks(orderBy: String): Flow<List<Task>>

    @Transaction
    @Query("SELECT * FROM task WHERE id = :id")
    fun getTaskById(id: Int): TaskWithExecutionHistory?

    @Upsert
    fun insertTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

}