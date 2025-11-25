package com.habittracker.domain.usecase

import com.habittracker.data.repository.HabitRepository

class DeleteHabitUseCase(
    private val repository: HabitRepository
) {
    suspend operator fun invoke(habitId: Long) {
        repository.deleteHabit(habitId)
    }
}
