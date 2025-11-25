package com.habittracker.domain.usecase

import com.habittracker.data.repository.HabitRepository
import com.habittracker.domain.model.Habit
import kotlinx.coroutines.flow.Flow

class GetHabitByIdUseCase(
    private val repository: HabitRepository
) {
    operator fun invoke(habitId: Long): Flow<Habit?> {
        return repository.getHabitById(habitId)
    }
}
