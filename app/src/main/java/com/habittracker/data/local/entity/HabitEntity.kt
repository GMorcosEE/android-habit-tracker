package com.habittracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val frequency: String, // "DAILY" or "WEEKLY"
    val createdAt: Long,
    val currentStreak: Int = 0,
    val totalCompletions: Int = 0
)
