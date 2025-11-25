package com.habittracker.presentation.screens.addedit

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.SavedStateHandle
import com.habittracker.domain.usecase.GetHabitByIdUseCase
import com.habittracker.domain.usecase.SaveHabitUseCase
import com.habittracker.presentation.theme.HabitTrackerTheme
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class AddEditHabitScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun addEditScreen_displaysAllInputFields() {
        // Given
        val savedStateHandle = SavedStateHandle()
        val getHabitByIdUseCase = mockk<GetHabitByIdUseCase>()
        val saveHabitUseCase = mockk<SaveHabitUseCase>()
        coEvery { getHabitByIdUseCase(any()) } returns flowOf(null)

        val viewModel = AddEditHabitViewModel(
            savedStateHandle,
            getHabitByIdUseCase,
            saveHabitUseCase
        )

        // When
        composeTestRule.setContent {
            HabitTrackerTheme {
                AddEditHabitScreen(
                    viewModel = viewModel,
                    onNavigateBack = {}
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("Habit Name").assertIsDisplayed()
        composeTestRule.onNodeWithText("Description").assertIsDisplayed()
        composeTestRule.onNodeWithText("Frequency").assertIsDisplayed()
        composeTestRule.onNodeWithText("Daily").assertIsDisplayed()
        composeTestRule.onNodeWithText("Weekly").assertIsDisplayed()
        composeTestRule.onNodeWithText("Save").assertIsDisplayed()
    }

    @Test
    fun addEditScreen_canEnterHabitName() {
        // Given
        val savedStateHandle = SavedStateHandle()
        val getHabitByIdUseCase = mockk<GetHabitByIdUseCase>()
        val saveHabitUseCase = mockk<SaveHabitUseCase>()
        coEvery { getHabitByIdUseCase(any()) } returns flowOf(null)

        val viewModel = AddEditHabitViewModel(
            savedStateHandle,
            getHabitByIdUseCase,
            saveHabitUseCase
        )

        // When
        composeTestRule.setContent {
            HabitTrackerTheme {
                AddEditHabitScreen(
                    viewModel = viewModel,
                    onNavigateBack = {}
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("Habit Name")
            .performTextInput("Exercise")
        
        composeTestRule.onNodeWithText("Exercise").assertIsDisplayed()
    }

    @Test
    fun addEditScreen_canSelectFrequency() {
        // Given
        val savedStateHandle = SavedStateHandle()
        val getHabitByIdUseCase = mockk<GetHabitByIdUseCase>()
        val saveHabitUseCase = mockk<SaveHabitUseCase>()
        coEvery { getHabitByIdUseCase(any()) } returns flowOf(null)

        val viewModel = AddEditHabitViewModel(
            savedStateHandle,
            getHabitByIdUseCase,
            saveHabitUseCase
        )

        // When
        composeTestRule.setContent {
            HabitTrackerTheme {
                AddEditHabitScreen(
                    viewModel = viewModel,
                    onNavigateBack = {}
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("Weekly").performClick()
        // Verify the radio button is selected (implementation specific)
    }
}
