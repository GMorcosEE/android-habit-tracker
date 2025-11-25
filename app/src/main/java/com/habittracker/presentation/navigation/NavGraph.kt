package com.habittracker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.habittracker.di.ViewModelFactory
import com.habittracker.presentation.screens.addedit.AddEditHabitScreen
import com.habittracker.presentation.screens.detail.HabitDetailScreen
import com.habittracker.presentation.screens.home.HomeScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AddHabit : Screen("add_habit")
    object EditHabit : Screen("edit_habit/{habitId}") {
        fun createRoute(habitId: Long) = "edit_habit/$habitId"
    }
    object HabitDetail : Screen("habit_detail/{habitId}") {
        fun createRoute(habitId: Long) = "habit_detail/$habitId"
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModelFactory: ViewModelFactory
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            val viewModel = viewModel<com.habittracker.presentation.screens.home.HomeViewModel>(
                factory = viewModelFactory
            )
            HomeScreen(
                viewModel = viewModel,
                onNavigateToAddHabit = {
                    navController.navigate(Screen.AddHabit.route)
                },
                onNavigateToHabitDetail = { habitId ->
                    navController.navigate(Screen.HabitDetail.createRoute(habitId))
                }
            )
        }

        composable(Screen.AddHabit.route) {
            val viewModel = viewModel<com.habittracker.presentation.screens.addedit.AddEditHabitViewModel>(
                factory = viewModelFactory
            )
            AddEditHabitScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.EditHabit.route,
            arguments = listOf(
                navArgument("habitId") { type = NavType.StringType }
            )
        ) {
            val viewModel = viewModel<com.habittracker.presentation.screens.addedit.AddEditHabitViewModel>(
                factory = viewModelFactory
            )
            AddEditHabitScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.HabitDetail.route,
            arguments = listOf(
                navArgument("habitId") { type = NavType.StringType }
            )
        ) {
            val viewModel = viewModel<com.habittracker.presentation.screens.detail.HabitDetailViewModel>(
                factory = viewModelFactory
            )
            HabitDetailScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
