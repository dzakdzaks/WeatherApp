[![Android CI](https://github.com/{dzakdzaks}/{WeatherApp}/actions/workflows/android.yml/badge.svg)](https://github.com/{dzakdzaks}/{WeatherApp}/actions/workflows/android.yml)

# Weather App

A modern Android weather application built with Kotlin, following clean architecture principles and using the latest Android development practices.

## Project Structure

The project follows a modular clean architecture approach with the following modules:

- **app**: Main application module containing the application class and DI setup
- **presentation**: UI layer with Jetpack Compose screens and ViewModels
- **domain**: Business logic and use cases
- **data**: Data layer handling API communication and local storage
- **core**: Shared utilities and base components
- **core-ui**: Shared UI components and resources

## Tech Stack

- **Kotlin**: Primary programming language
- **Jetpack Compose**: Modern UI toolkit
- **Koin**: Dependency injection
- **Ktor**: HTTP client
- **Kotlin Coroutines & Flow**: Asynchronous programming
- **Material Design 3**: UI design system
- **Coil**: Image loading
- **JUnit, Mockk & Turbine**: Testing

## Prerequisites

- Android Studio Hedgehog | 2023.1.1 or newer (Created using Android Studio Ladybug | 2024.2.1 Patch 3)
- Android SDK with minimum API level 24 (Android 7.0)
- Gradle 8.9

## Setup

1. Clone the repository:

```bash
git clone https://github.com/dzakdzaks/WeatherApp.git
```

2. Create a `local.properties` file in the root project directory and add your API credentials:

```properties
BASE_URL=your_weather_api_base_url
KEY=your_weather_api_key
```

3. Sync the project with Gradle files and run the app!

## Testing

Run the unit tests and it's automatically generate the code coverage report:

```bash
./gradlew :domain:test :data:test :presentation:test
```
