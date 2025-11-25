package com.habittracker

import android.app.Application
import androidx.room.Room
import com.habittracker.data.local.HabitDatabase
import com.habittracker.data.repository.HabitRepository
import com.habittracker.data.repository.HabitRepositoryImpl
import com.habittracker.di.ViewModelFactory

class HabitTrackerApplication : Application() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            HabitDatabase::class.java,
            "habit_database"
        ).build()
    }

    val repository: HabitRepository by lazy {
        HabitRepositoryImpl(
            habitDao = database.habitDao(),
            completionDao = database.habitCompletionDao()
        )
    }

    val viewModelFactory by lazy {
        ViewModelFactory(repository)
    }
}
