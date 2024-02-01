package com.github.diegopap.repetitivetasksapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.github.diegopap.repetitivetasksapp.ui.theme.*

@Entity
data class Task (

    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val title: String,
    val color: Int,
    val frequency: Int,
    val executed: Long,
    val created: Long

){
    companion object {
        val taskColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

@Entity(
    foreignKeys = [ForeignKey(
        entity = Task::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("taskId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class ExecutionHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val executed: Long,
    @ColumnInfo(index = true)
    val taskId: Long
)

data class TaskWithExecutionHistory(
    @Embedded
    val task: Task,
    @Relation(
        parentColumn = "id",
        entityColumn = "taskId"
    )
    val executionHistory: List<ExecutionHistory>
)

class InvalidTaskException(message: String): Exception(message)