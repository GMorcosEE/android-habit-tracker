package com.habittracker.presentation.screens.addedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habittracker.domain.model.Habit
import com.habittracker.domain.model.HabitFrequency
import com.habittracker.domain.usecase.GetHabitByIdUseCase
import com.habittracker.domain.usecase.SaveHabitUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AddEditHabitUiState(
    val habitId: Long = 0,
    val name: String = "",
    val description: String = "",
    val frequency: HabitFrequency = HabitFrequency.DAILY,
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val error: String? = null,
    val nameError: String? = null
)

class AddEditHabitViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val getHabitByIdUseCase: GetHabitByIdUseCase,
    private val saveHabitUseCase: SaveHabitUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddEditHabitUiState())
    val uiState: StateFlow<AddEditHabitUiState> = _uiState.asStateFlow()

    init {
        val habitId = savedStateHandle.get<String>("habitId")?.toLongOrNull() ?: 0L
        if (habitId > 0) {
            loadHabit(habitId)
        }
    }

    private fun loadHabit(habitId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                getHabitByIdUseCase(habitId).collect { habit ->
                    habit?.let {
                        _uiState.value = _uiState.value.copy(
                            habitId = it.id,
                            name = it.name,
                            description = it.description,
                            frequency = it.frequency,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun onNameChange(name: String) {
        _uiState.value = _uiState.value.copy(
            name = name,
            nameError = null
        )
    }

    fun onDescriptionChange(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun onFrequencyChange(frequency: HabitFrequency) {
        _uiState.value = _uiState.value.copy(frequency = frequency)
    }

    fun onSaveClick() {
        if (_uiState.value.name.isBlank()) {
            _uiState.value = _uiState.value.copy(nameError = "Habit name is required")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val habit = Habit(
                    id = _uiState.value.habitId,
                    name = _uiState.value.name,
                    description = _uiState.value.description,
                    frequency = _uiState.value.frequency,
                    createdAt = System.currentTimeMillis()
                )
                saveHabitUseCase(habit)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSaved = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}
