package com.habittracker.domain.usecase

import com.habittracker.data.repository.HabitRepository
import com.habittracker.domain.model.HabitCompletion
import kotlinx.coroutines.flow.Flow

class GetHabitCompletionsUseCase(
    private val repository: HabitRepository
) {
    operator fun invoke(habitId: Long): Flow<List<HabitCompletion>> {
        return repository.getCompletionsForHabit(habitId)
    }
}
