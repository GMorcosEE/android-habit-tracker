package com.habittracker.data.repository

import com.habittracker.domain.model.Habit
import com.habittracker.domain.model.HabitCompletion
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    fun getAllHabits(): Flow<List<Habit>>
    fun getHabitById(habitId: Long): Flow<Habit?>
    suspend fun insertHabit(habit: Habit): Long
    suspend fun updateHabit(habit: Habit)
    suspend fun deleteHabit(habitId: Long)
    
    fun getCompletionsForHabit(habitId: Long): Flow<List<HabitCompletion>>
    suspend fun addCompletion(habitId: Long)
    suspend fun deleteCompletion(completionId: Long)
}
