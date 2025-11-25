package com.habittracker.data.local.dao

import androidx.room.*
import com.habittracker.data.local.entity.HabitCompletionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitCompletionDao {
    
    @Query("SELECT * FROM habit_completions WHERE habitId = :habitId ORDER BY completedAt DESC")
    fun getCompletionsForHabit(habitId: Long): Flow<List<HabitCompletionEntity>>
    
    @Query("SELECT * FROM habit_completions WHERE habitId = :habitId ORDER BY completedAt DESC")
    suspend fun getCompletionsForHabitSync(habitId: Long): List<HabitCompletionEntity>
    
    @Insert
    suspend fun insertCompletion(completion: HabitCompletionEntity)
    
    @Delete
    suspend fun deleteCompletion(completion: HabitCompletionEntity)
    
    @Query("DELETE FROM habit_completions WHERE habitId = :habitId")
    suspend fun deleteCompletionsForHabit(habitId: Long)
    
    @Query("""
        SELECT * FROM habit_completions 
        WHERE habitId = :habitId 
        AND completedAt >= :startTime 
        AND completedAt <= :endTime
        ORDER BY completedAt DESC
    """)
    suspend fun getCompletionsInRange(habitId: Long, startTime: Long, endTime: Long): List<HabitCompletionEntity>
}
