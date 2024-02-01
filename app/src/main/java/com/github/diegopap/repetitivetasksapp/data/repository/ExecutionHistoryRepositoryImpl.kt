package com.github.diegopap.repetitivetasksapp.data.repository

import com.github.diegopap.repetitivetasksapp.data.data_source.ExecutionHistoryDao
import com.github.diegopap.repetitivetasksapp.domain.model.ExecutionHistory
import com.github.diegopap.repetitivetasksapp.domain.repository.ExecutionHistoryRepository

class ExecutionHistoryRepositoryImpl(private val executionHistoryDao: ExecutionHistoryDao) :
    ExecutionHistoryRepository {

    override suspend fun insertExecutionHistory(executionHistory: ExecutionHistory): Long = executionHistoryDao.insertExecutionHistory(executionHistory)

    override suspend fun deleteExecutionHistory(executionHistory: ExecutionHistory) = executionHistoryDao.deleteExecutionHistory(executionHistory)

}