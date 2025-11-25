package com.habittracker.domain.usecase

import com.habittracker.data.repository.HabitRepository

class CompleteHabitUseCase(
    private val repository: HabitRepository
) {
    suspend operator fun invoke(habitId: Long) {
        repository.addCompletion(habitId)
    }
}
