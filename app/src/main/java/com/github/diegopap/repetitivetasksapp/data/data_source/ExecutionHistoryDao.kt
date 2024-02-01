package com.github.diegopap.repetitivetasksapp.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Upsert
import com.github.diegopap.repetitivetasksapp.domain.model.ExecutionHistory

@Dao
interface ExecutionHistoryDao {

    @Upsert
    fun insertExecutionHistory(executionHistory: ExecutionHistory): Long

    @Delete
    fun deleteExecutionHistory(executionHistory: ExecutionHistory)

}