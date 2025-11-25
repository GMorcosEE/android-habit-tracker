package com.habittracker.domain.model

data class Habit(
    val id: Long = 0,
    val name: String,
    val description: String,
    val frequency: HabitFrequency,
    val createdAt: Long,
    val currentStreak: Int = 0,
    val totalCompletions: Int = 0
)

enum class HabitFrequency {
    DAILY,
    WEEKLY
}
