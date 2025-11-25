package com.habittracker.presentation.screens.addedit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.habittracker.domain.model.Habit
import com.habittracker.domain.model.HabitFrequency
import com.habittracker.domain.usecase.GetHabitByIdUseCase
import com.habittracker.domain.usecase.SaveHabitUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class AddEditHabitViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var getHabitByIdUseCase: GetHabitByIdUseCase
    private lateinit var saveHabitUseCase: SaveHabitUseCase
    private lateinit var viewModel: AddEditHabitViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        savedStateHandle = SavedStateHandle()
        getHabitByIdUseCase = mockk()
        saveHabitUseCase = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state for new habit is empty`() = runTest {
        // When
        viewModel = AddEditHabitViewModel(
            savedStateHandle,
            getHabitByIdUseCase,
            saveHabitUseCase
        )
        advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertEquals(0L, state.habitId)
        assertEquals("", state.name)
        assertEquals("", state.description)
        assertEquals(HabitFrequency.DAILY, state.frequency)
    }

    @Test
    fun `onNameChange updates name in state`() = runTest {
        // Given
        viewModel = AddEditHabitViewModel(
            savedStateHandle,
            getHabitByIdUseCase,
            saveHabitUseCase
        )
        advanceUntilIdle()

        // When
        viewModel.onNameChange("Exercise")
        advanceUntilIdle()

        // Then
        assertEquals("Exercise", viewModel.uiState.value.name)
    }

    @Test
    fun `onSaveClick with empty name shows error`() = runTest {
        // Given
        viewModel = AddEditHabitViewModel(
            savedStateHandle,
            getHabitByIdUseCase,
            saveHabitUseCase
        )
        advanceUntilIdle()

        // When
        viewModel.onSaveClick()
        advanceUntilIdle()

        // Then
        assertEquals("Habit name is required", viewModel.uiState.value.nameError)
        assertFalse(viewModel.uiState.value.isSaved)
    }

    @Test
    fun `onSaveClick with valid data saves habit`() = runTest {
        // Given
        coEvery { saveHabitUseCase(any()) } returns 1L
        viewModel = AddEditHabitViewModel(
            savedStateHandle,
            getHabitByIdUseCase,
            saveHabitUseCase
        )
        advanceUntilIdle()

        // When
        viewModel.onNameChange("Exercise")
        viewModel.onDescriptionChange("Daily workout")
        viewModel.onSaveClick()
        advanceUntilIdle()

        // Then
        coVerify { saveHabitUseCase(any()) }
        assertTrue(viewModel.uiState.value.isSaved)
    }

    @Test
    fun `onFrequencyChange updates frequency in state`() = runTest {
        // Given
        viewModel = AddEditHabitViewModel(
            savedStateHandle,
            getHabitByIdUseCase,
            saveHabitUseCase
        )
        advanceUntilIdle()

        // When
        viewModel.onFrequencyChange(HabitFrequency.WEEKLY)
        advanceUntilIdle()

        // Then
        assertEquals(HabitFrequency.WEEKLY, viewModel.uiState.value.frequency)
    }
}
