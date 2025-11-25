package com.habittracker.presentation.screens.home

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.habittracker.domain.model.Habit
import com.habittracker.domain.model.HabitFrequency
import com.habittracker.domain.usecase.CompleteHabitUseCase
import com.habittracker.domain.usecase.GetAllHabitsUseCase
import com.habittracker.presentation.theme.HabitTrackerTheme
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_displaysNoHabitsMessage_whenListIsEmpty() {
        // Given
        val getAllHabitsUseCase = mockk<GetAllHabitsUseCase>()
        val completeHabitUseCase = mockk<CompleteHabitUseCase>()
        every { getAllHabitsUseCase() } returns flowOf(emptyList())

        val viewModel = HomeViewModel(getAllHabitsUseCase, completeHabitUseCase)

        // When
        composeTestRule.setContent {
            HabitTrackerTheme {
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToAddHabit = {},
                    onNavigateToHabitDetail = {}
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("No habits yet. Add one to get started!")
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_displaysHabits_whenListIsNotEmpty() {
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
        val getAllHabitsUseCase = mockk<GetAllHabitsUseCase>()
        val completeHabitUseCase = mockk<CompleteHabitUseCase>()
        every { getAllHabitsUseCase() } returns flowOf(habits)

        val viewModel = HomeViewModel(getAllHabitsUseCase, completeHabitUseCase)

        // When
        composeTestRule.setContent {
            HabitTrackerTheme {
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToAddHabit = {},
                    onNavigateToHabitDetail = {}
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("Exercise").assertIsDisplayed()
        composeTestRule.onNodeWithText("Daily workout").assertIsDisplayed()
    }

    @Test
    fun homeScreen_fabIsDisplayed() {
        // Given
        val getAllHabitsUseCase = mockk<GetAllHabitsUseCase>()
        val completeHabitUseCase = mockk<CompleteHabitUseCase>()
        every { getAllHabitsUseCase() } returns flowOf(emptyList())

        val viewModel = HomeViewModel(getAllHabitsUseCase, completeHabitUseCase)

        // When
        composeTestRule.setContent {
            HabitTrackerTheme {
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToAddHabit = {},
                    onNavigateToHabitDetail = {}
                )
            }
        }

        // Then
        composeTestRule.onNodeWithContentDescription("Add Habit").assertIsDisplayed()
    }
}
