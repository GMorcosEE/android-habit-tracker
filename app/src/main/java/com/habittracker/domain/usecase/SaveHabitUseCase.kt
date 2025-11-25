package com.habittracker.domain.usecase

import com.habittracker.data.repository.HabitRepository
import com.habittracker.domain.model.Habit

class SaveHabitUseCase(
    private val repository: HabitRepository
) {
    suspend operator fun invoke(habit: Habit): Long {
        return if (habit.id == 0L) {
            repository.insertHabit(habit)
        } else {
            repository.updateHabit(habit)
            habit.id
        }
    }
}
