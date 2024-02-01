package com.github.diegopap.repetitivetasksapp.di

import android.app.Application
import androidx.room.Room
import com.github.diegopap.repetitivetasksapp.data.data_source.TaskDatabase
import com.github.diegopap.repetitivetasksapp.data.repository.ExecutionHistoryRepositoryImpl
import com.github.diegopap.repetitivetasksapp.data.repository.TaskRepositoryImpl
import com.github.diegopap.repetitivetasksapp.domain.repository.ExecutionHistoryRepository
import com.github.diegopap.repetitivetasksapp.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(app: Application): TaskDatabase {
        return Room.databaseBuilder(
            app,
            TaskDatabase::class.java,
            TaskDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(db: TaskDatabase): TaskRepository {
        return TaskRepositoryImpl(db.taskDao)
    }

    @Provides
    @Singleton
    fun provideExecutionHistoryRepository(db: TaskDatabase): ExecutionHistoryRepository {
        return ExecutionHistoryRepositoryImpl(db.executionHistoryDao)
    }

}