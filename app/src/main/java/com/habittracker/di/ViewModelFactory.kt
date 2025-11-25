package com.habittracker.di

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.habittracker.data.repository.HabitRepository
import com.habittracker.domain.usecase.*
import com.habittracker.presentation.screens.addedit.AddEditHabitViewModel
import com.habittracker.presentation.screens.detail.HabitDetailViewModel
import com.habittracker.presentation.screens.home.HomeViewModel

class ViewModelFactory(
    private val repository: HabitRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(
                    getAllHabitsUseCase = GetAllHabitsUseCase(repository),
                    completeHabitUseCase = CompleteHabitUseCase(repository)
                ) as T
            }
            modelClass.isAssignableFrom(AddEditHabitViewModel::class.java) -> {
                AddEditHabitViewModel(
                    savedStateHandle = SavedStateHandle(),
                    getHabitByIdUseCase = GetHabitByIdUseCase(repository),
                    saveHabitUseCase = SaveHabitUseCase(repository)
                ) as T
            }
            modelClass.isAssignableFrom(HabitDetailViewModel::class.java) -> {
                HabitDetailViewModel(
                    savedStateHandle = SavedStateHandle(),
                    getHabitByIdUseCase = GetHabitByIdUseCase(repository),
                    getHabitCompletionsUseCase = GetHabitCompletionsUseCase(repository),
                    completeHabitUseCase = CompleteHabitUseCase(repository),
                    deleteHabitUseCase = DeleteHabitUseCase(repository)
                ) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
