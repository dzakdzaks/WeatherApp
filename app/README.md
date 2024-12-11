weather-app/
├── app/                    # Application module (entry point and DI initialization)
│   ├── src/
│   │   ├── main/
│   │   │   ├── kotlin/
│   │   │   │   ├── com.example.weatherapp/
│   │   │   │   │   ├── WeatherApp.kt            # Application class
│   │   │   │   │   ├── di/
│   │   │   │   │   │   ├── AppModule.kt         # DI setup for app
│   │   │   │   │   ├── navigation/
│   │   │   │   │   │   ├── NavigationGraph.kt   # App-wide navigation graph
│   │   │   │   │   ├── ui/
│   │   │   │   │   │   ├── theme/               # Jetpack Compose theme
│   │   │   │   │   │   │   ├── Color.kt
│   │   │   │   │   │   │   ├── Typography.kt
│   │   │   │   │   │   │   ├── Theme.kt
│   │   │   │   │   │   ├── MainActivity.kt      # Main entry point for the app
├── core/                   # Shared resources and utilities
│   ├── src/
│   │   ├── main/
│   │   │   ├── kotlin/
│   │   │   │   ├── com.example.weatherapp.core/
│   │   │   │   │   ├── util/                   # Utility classes and extensions
│   │   │   │   │   │   ├── NetworkUtils.kt
│   │   │   │   │   │   ├── Result.kt           # Sealed class for success/error
│   │   │   │   │   ├── data/                   # Core data-related resources
│   │   │   │   │   │   ├── ApiConstants.kt
│   │   │   │   │   │   ├── ErrorHandler.kt
│   │   │   │   │   ├── di/                     # Shared DI modules
│   │   │   │   │   │   ├── CoreModule.kt
│   │   │   │   │   ├── model/                  # Shared models
│   │   │   │   │   │   ├── WeatherCondition.kt # Shared domain model
│   │   │   │   │   ├── extensions/            # Kotlin extensions
│   │   │   │   │   │   ├── StringExtensions.kt
├── data/                   # Data layer (API, local storage, repositories)
│   ├── src/
│   │   ├── main/
│   │   │   ├── kotlin/
│   │   │   │   ├── com.example.weatherapp.data/
│   │   │   │   │   ├── api/                   # Retrofit interfaces
│   │   │   │   │   │   ├── WeatherApi.kt
│   │   │   │   │   ├── repository/            # Data repositories
│   │   │   │   │   │   ├── WeatherRepository.kt
│   │   │   │   │   ├── local/                 # Local storage (e.g., DataStore)
│   │   │   │   │   │   ├── PreferencesManager.kt
│   │   │   │   │   ├── di/                    # DI modules for data layer
│   │   │   │   │   │   ├── NetworkModule.kt
│   │   │   │   │   │   ├── RepositoryModule.kt
├── domain/                 # Domain layer (use cases and domain models)
│   ├── src/
│   │   ├── main/
│   │   │   ├── kotlin/
│   │   │   │   ├── com.example.weatherapp.domain/
│   │   │   │   │   ├── model/                 # Domain models
│   │   │   │   │   │   ├── City.kt
│   │   │   │   │   │   ├── WeatherInfo.kt
│   │   │   │   │   ├── usecase/               # Business logic
│   │   │   │   │   │   ├── GetWeatherUseCase.kt
│   │   │   │   │   │   ├── SaveCityUseCase.kt
├── presentation/           # Presentation layer (UI and ViewModels)
│   ├── src/
│   │   ├── main/
│   │   │   ├── kotlin/
│   │   │   │   ├── com.example.weatherapp.presentation/
│   │   │   │   │   ├── ui/
│   │   │   │   │   │   ├── home/              # Home screen UI
│   │   │   │   │   │   │   ├── HomeScreen.kt
│   │   │   │   │   │   │   ├── HomeViewModel.kt
│   │   │   │   │   │   ├── search/            # Search screen UI
│   │   │   │   │   │   │   ├── SearchScreen.kt
│   │   │   │   │   │   │   ├── SearchViewModel.kt
│   │   │   │   │   │   ├── components/        # Reusable UI components
│   │   │   │   │   │   │   ├── WeatherCard.kt
│   │   │   │   │   │   │   ├── SearchBar.kt
│   │   │   │   │   ├── di/                   # DI modules for presentation
│   │   │   │   │   │   ├── ViewModelModule.kt
