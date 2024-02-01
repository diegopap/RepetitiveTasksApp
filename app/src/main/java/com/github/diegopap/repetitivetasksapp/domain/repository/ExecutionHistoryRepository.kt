package com.github.diegopap.repetitivetasksapp.domain.repository

import com.github.diegopap.repetitivetasksapp.domain.model.ExecutionHistory

interface ExecutionHistoryRepository {

    suspend fun insertExecutionHistory(executionHistory: ExecutionHistory): Long

    suspend fun deleteExecutionHistory(executionHistory: ExecutionHistory)

}