package com.example.todoapp.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks WHERE userToken = :token")
    fun getAllTasks(token: String): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE dateTime LIKE :date || '%' AND userToken = :userToken")
    suspend fun getTasksByDate(date: String, userToken: String): List<TaskEntity>

    @Query("SELECT category, COUNT(*) as count FROM tasks GROUP BY category")
    fun getTasksCountByCategory(): Flow<List<CategoryCount>>

    @Query("""
    SELECT substr(taskCreatedTime, 1, 10) as date, COUNT(*) as count
    FROM tasks
    GROUP BY date
    ORDER BY date ASC
""")
    fun getTasksCountByDate(): Flow<List<DateCount>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tasks: List<TaskEntity>)


    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteTask(id: Int)
}

data class CategoryCount(
    val category: String,
    val count: Int
)

data class DateCount(
    val date: String, // например "2025-09-11"
    val count: Int
)