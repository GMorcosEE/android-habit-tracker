package com.habittracker.presentation.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.habittracker.domain.model.Habit
import com.habittracker.domain.model.HabitCompletion
import com.habittracker.domain.usecase.CompleteHabitUseCase
import com.habittracker.domain.usecase.DeleteHabitUseCase
import com.habittracker.domain.usecase.GetHabitByIdUseCase
import com.habittracker.domain.usecase.GetHabitCompletionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HabitDetailUiState(
    val habit: Habit? = null,
    val completions: List<HabitCompletion> = emptyList(),
    val isLoading: Boolean = false,
    val isDeleted: Boolean = false,
    val error: String? = null,
    val showDeleteDialog: Boolean = false
)

class HabitDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val getHabitByIdUseCase: GetHabitByIdUseCase,
    private val getHabitCompletionsUseCase: GetHabitCompletionsUseCase,
    private val completeHabitUseCase: CompleteHabitUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HabitDetailUiState())
    val uiState: StateFlow<HabitDetailUiState> = _uiState.asStateFlow()

    private val habitId: Long = savedStateHandle.get<String>("habitId")?.toLongOrNull() ?: 0L

    init {
        if (habitId > 0) {
            loadHabitDetails()
        }
    }

    private fun loadHabitDetails() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                // Load habit
                launch {
                    getHabitByIdUseCase(habitId).collect { habit ->
                        _uiState.value = _uiState.value.copy(
                            habit = habit,
                            isLoading = false
                        )
                    }
                }
                
                // Load completions
                launch {
                    getHabitCompletionsUseCase(habitId).collect { completions ->
                        _uiState.value = _uiState.value.copy(completions = completions)
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

    fun onCompleteHabit() {
        viewModelScope.launch {
            try {
                completeHabitUseCase(habitId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun onDeleteClick() {
        _uiState.value = _uiState.value.copy(showDeleteDialog = true)
    }

    fun onDeleteConfirm() {
        viewModelScope.launch {
            try {
                deleteHabitUseCase(habitId)
                _uiState.value = _uiState.value.copy(
                    showDeleteDialog = false,
                    isDeleted = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    showDeleteDialog = false,
                    error = e.message
                )
            }
        }
    }

    fun onDeleteCancel() {
        _uiState.value = _uiState.value.copy(showDeleteDialog = false)
    }
}
