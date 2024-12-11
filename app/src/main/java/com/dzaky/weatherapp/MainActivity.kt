package com.dzaky.weatherapp

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.dzaky.core_ui.ui.Typography
import com.dzaky.core_ui.ui.WeatherColorScheme
import com.dzaky.presentation.ui.home.HomeScreenRoot

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
            enableEdgeToEdge(
                statusBarStyle = systemBarStyle,
                navigationBarStyle = systemBarStyle
            )
            WeatherCompose()
        }
    }
}

@Composable
fun WeatherCompose() {
    MaterialTheme(
        colorScheme = WeatherColorScheme,
        typography = Typography,
    ) {
        HomeScreenRoot()
    }
}