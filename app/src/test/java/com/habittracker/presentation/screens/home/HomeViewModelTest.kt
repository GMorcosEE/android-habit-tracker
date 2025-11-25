package com.habittracker.presentation.screens.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.habittracker.domain.model.Habit
import com.habittracker.domain.model.HabitFrequency
import com.habittracker.domain.usecase.CompleteHabitUseCase
import com.habittracker.domain.usecase.GetAllHabitsUseCase
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

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getAllHabitsUseCase: GetAllHabitsUseCase
    private lateinit var completeHabitUseCase: CompleteHabitUseCase
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getAllHabitsUseCase = mockk()
        completeHabitUseCase = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state loads habits successfully`() = runTest {
        // Given
        val habits = listOf(
            Habit(
                id = 1,
                name = "Exercise",
                description = "Daily workout",
                frequency = HabitFrequency.DAILY,
                createdAt = System.currentTimeMillis(),
                currentStreak = 5,
                totalCompletions = 10
            )
        )
        coEvery { getAllHabitsUseCase() } returns flowOf(habits)

        // When
        viewModel = HomeViewModel(getAllHabitsUseCase, completeHabitUseCase)
        advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertEquals(habits, state.habits)
        assertFalse(state.isLoading)
        assertEquals(null, state.error)
    }

    @Test
    fun `onCompleteHabit calls use case with correct id`() = runTest {
        // Given
        coEvery { getAllHabitsUseCase() } returns flowOf(emptyList())
        viewModel = HomeViewModel(getAllHabitsUseCase, completeHabitUseCase)
        advanceUntilIdle()

        // When
        viewModel.onCompleteHabit(1L)
        advanceUntilIdle()

        // Then
        coVerify { completeHabitUseCase(1L) }
    }

    @Test
    fun `empty habits list shows correct state`() = runTest {
        // Given
        coEvery { getAllHabitsUseCase() } returns flowOf(emptyList())

        // When
        viewModel = HomeViewModel(getAllHabitsUseCase, completeHabitUseCase)
        advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertEquals(emptyList(), state.habits)
        assertFalse(state.isLoading)
    }
}
