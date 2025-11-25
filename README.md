# Habit Tracker - Android Demo App

A modern Android application for tracking daily and weekly habits, built with Jetpack Compose and following clean architecture principles with MVVM pattern.

## Features

- **Create Habits**: Add new habits with name, description, and frequency (daily/weekly)
- **Track Progress**: Mark habits as complete and track your streak
- **View History**: See all completions for each habit with timestamps
- **Delete Habits**: Remove habits you no longer want to track
- **Material 3 Design**: Modern UI with Material Design 3 components
- **Offline First**: All data stored locally using Room database

## Screenshots

The app includes three main screens:
- **Home Screen**: List of all habits with quick complete button
- **Add/Edit Screen**: Form to create or modify habits
- **Detail Screen**: View habit statistics and completion history

## Architecture

This project follows **Clean Architecture** principles with clear separation of concerns:

### Layers

```
app/
├── data/                    # Data layer
│   ├── local/              # Local data sources
│   │   ├── dao/           # Room DAOs
│   │   ├── entity/        # Room entities
│   │   └── HabitDatabase  # Room database
│   └── repository/         # Repository implementations
├── domain/                  # Domain layer
│   ├── model/             # Domain models
│   └── usecase/           # Business logic use cases
├── presentation/            # Presentation layer
│   ├── screens/           # UI screens
│   │   ├── home/         # Home screen + ViewModel
│   │   ├── addedit/      # Add/Edit screen + ViewModel
│   │   └── detail/       # Detail screen + ViewModel
│   ├── navigation/        # Navigation graph
│   └── theme/            # Material 3 theme
└── di/                     # Dependency injection
```

### Design Patterns

- **MVVM (Model-View-ViewModel)**: Separates UI logic from business logic
- **Repository Pattern**: Abstracts data sources
- **Use Cases**: Encapsulates single business operations
- **Unidirectional Data Flow**: State flows down, events flow up
- **Single Source of Truth**: Room database as the source of truth

## Technology Stack

### Core
- **Kotlin**: 100% Kotlin codebase
- **Jetpack Compose**: Modern declarative UI toolkit
- **Material 3**: Latest Material Design components
- **Coroutines**: Asynchronous programming
- **Flow**: Reactive data streams

### Architecture Components
- **ViewModel**: Lifecycle-aware UI state management
- **Room**: Local database with SQLite
- **Navigation Compose**: Type-safe navigation

### Testing
- **JUnit 4**: Unit testing framework
- **MockK**: Mocking library for Kotlin
- **Compose Testing**: UI testing for Compose
- **Coroutines Test**: Testing coroutines

## Project Structure

```
com.habittracker/
├── data/
│   ├── local/
│   │   ├── dao/
│   │   │   ├── HabitDao.kt
│   │   │   └── HabitCompletionDao.kt
│   │   ├── entity/
│   │   │   ├── HabitEntity.kt
│   │   │   └── HabitCompletionEntity.kt
│   │   └── HabitDatabase.kt
│   └── repository/
│       ├── HabitRepository.kt (interface)
│       └── HabitRepositoryImpl.kt
├── domain/
│   ├── model/
│   │   ├── Habit.kt
│   │   ├── HabitFrequency.kt
│   │   └── HabitCompletion.kt
│   └── usecase/
│       ├── GetAllHabitsUseCase.kt
│       ├── GetHabitByIdUseCase.kt
│       ├── SaveHabitUseCase.kt
│       ├── DeleteHabitUseCase.kt
│       ├── CompleteHabitUseCase.kt
│       └── GetHabitCompletionsUseCase.kt
├── presentation/
│   ├── screens/
│   │   ├── home/
│   │   │   ├── HomeScreen.kt
│   │   │   └── HomeViewModel.kt
│   │   ├── addedit/
│   │   │   ├── AddEditHabitScreen.kt
│   │   │   └── AddEditHabitViewModel.kt
│   │   └── detail/
│   │       ├── HabitDetailScreen.kt
│   │       └── HabitDetailViewModel.kt
│   ├── navigation/
│   │   └── NavGraph.kt
│   └── theme/
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
├── di/
│   └── ViewModelFactory.kt
├── HabitTrackerApplication.kt
└── MainActivity.kt
```

## Build and Run

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or later
- JDK 17
- Android SDK with minimum API 24 (Android 7.0)
- Gradle 8.2

### Build Instructions

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd android-habit-tracker
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to the `android-habit-tracker` directory
   - Click "OK"

3. **Sync Gradle**
   - Android Studio will automatically sync Gradle
   - Wait for the sync to complete

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click the "Run" button (green play icon)
   - Select your device/emulator
   - The app will build and install

### Build from Command Line

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Install on connected device
./gradlew installDebug
```

## Testing

### Run Unit Tests

```bash
# Run all unit tests
./gradlew test

# Run tests for a specific module
./gradlew :app:testDebugUnitTest

# Run with coverage
./gradlew testDebugUnitTest jacocoTestReport
```

### Run Instrumented Tests

```bash
# Run all instrumented tests
./gradlew connectedAndroidTest

# Run specific test class
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.habittracker.presentation.screens.home.HomeScreenTest
```

### Test Coverage

The project includes:
- **Unit Tests**: ViewModels, Use Cases, Repository
- **UI Tests**: Compose screens with user interactions
- **Integration Tests**: Database operations

## Configuration

### Database

The app uses Room database with the following configuration:
- Database name: `habit_database`
- Version: 1
- Tables: `habits`, `habit_completions`

### Gradle Configuration

Key dependencies and versions:
- Kotlin: 1.9.20
- Compose BOM: 2023.10.01
- Room: 2.6.1
- Navigation: 2.7.5
- Coroutines: 1.7.3

## Future Enhancements

### Planned Features
- [ ] Habit reminders with notifications
- [ ] Habit categories and tags
- [ ] Statistics and charts
- [ ] Habit templates
- [ ] Export/import data
- [ ] Dark mode toggle
- [ ] Widget support
- [ ] Cloud sync
- [ ] Social features (share progress)
- [ ] Habit notes and journal

### Technical Improvements
- [ ] Implement Hilt for dependency injection
- [ ] Add DataStore for preferences
- [ ] Implement WorkManager for background tasks
- [ ] Add Paging 3 for large lists
- [ ] Implement offline-first sync strategy
- [ ] Add Crashlytics and Analytics
- [ ] Improve test coverage to 80%+
- [ ] Add UI/UX animations
- [ ] Implement accessibility features
- [ ] Add multi-language support

## Contributing

This is a demo project, but contributions are welcome:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is created for demonstration purposes.

## Contact

For questions or feedback, please open an issue in the repository.

---

**Built with ❤️ using Jetpack Compose**
