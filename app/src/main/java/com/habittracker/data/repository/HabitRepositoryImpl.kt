package com.habittracker.data.repository

import com.habittracker.data.local.dao.HabitCompletionDao
import com.habittracker.data.local.dao.HabitDao
import com.habittracker.data.local.entity.HabitCompletionEntity
import com.habittracker.data.local.entity.HabitEntity
import com.habittracker.domain.model.Habit
import com.habittracker.domain.model.HabitCompletion
import com.habittracker.domain.model.HabitFrequency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar

class HabitRepositoryImpl(
    private val habitDao: HabitDao,
    private val completionDao: HabitCompletionDao
) : HabitRepository {

    override fun getAllHabits(): Flow<List<Habit>> {
        return habitDao.getAllHabits().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getHabitById(habitId: Long): Flow<Habit?> {
        return habitDao.getHabitByIdFlow(habitId).map { it?.toDomain() }
    }

    override suspend fun insertHabit(habit: Habit): Long {
        return habitDao.insertHabit(habit.toEntity())
    }

    override suspend fun updateHabit(habit: Habit) {
        habitDao.updateHabit(habit.toEntity())
    }

    override suspend fun deleteHabit(habitId: Long) {
        habitDao.deleteHabitById(habitId)
    }

    override fun getCompletionsForHabit(habitId: Long): Flow<List<HabitCompletion>> {
        return completionDao.getCompletionsForHabit(habitId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addCompletion(habitId: Long) {
        val currentTime = System.currentTimeMillis()
        val completion = HabitCompletionEntity(
            habitId = habitId,
            completedAt = currentTime
        )
        completionDao.insertCompletion(completion)
        
        // Update habit stats
        val habit = habitDao.getHabitById(habitId) ?: return
        val completions = completionDao.getCompletionsForHabitSync(habitId)
        
        val updatedHabit = habit.copy(
            totalCompletions = completions.size,
            currentStreak = calculateStreak(completions, habit.frequency)
        )
        habitDao.updateHabit(updatedHabit)
    }

    override suspend fun deleteCompletion(completionId: Long) {
        // Implementation would require fetching the completion first
        // Simplified for demo purposes
    }

    private fun calculateStreak(completions: List<HabitCompletionEntity>, frequency: String): Int {
        if (completions.isEmpty()) return 0
        
        val calendar = Calendar.getInstance()
        var streak = 0
        var currentDate = calendar.timeInMillis
        
        val sortedCompletions = completions.sortedByDescending { it.completedAt }
        
        for (completion in sortedCompletions) {
            val completionCalendar = Calendar.getInstance().apply {
                timeInMillis = completion.completedAt
            }
            
            val daysDiff = ((currentDate - completion.completedAt) / (1000 * 60 * 60 * 24)).toInt()
            
            when (frequency) {
                "DAILY" -> {
                    if (daysDiff <= 1) {
                        streak++
                        currentDate = completion.completedAt
                    } else {
                        break
                    }
                }
                "WEEKLY" -> {
                    if (daysDiff <= 7) {
                        streak++
                        currentDate = completion.completedAt
                    } else {
                        break
                    }
                }
            }
        }
        
        return streak
    }

    private fun HabitEntity.toDomain(): Habit {
        return Habit(
            id = id,
            name = name,
            description = description,
            frequency = HabitFrequency.valueOf(frequency),
            createdAt = createdAt,
            currentStreak = currentStreak,
            totalCompletions = totalCompletions
        )
    }

    private fun Habit.toEntity(): HabitEntity {
        return HabitEntity(
            id = id,
            name = name,
            description = description,
            frequency = frequency.name,
            createdAt = createdAt,
            currentStreak = currentStreak,
            totalCompletions = totalCompletions
        )
    }

    private fun HabitCompletionEntity.toDomain(): HabitCompletion {
        return HabitCompletion(
            id = id,
            habitId = habitId,
            completedAt = completedAt
        )
    }
}
