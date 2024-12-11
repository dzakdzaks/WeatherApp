package com.dzaky.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dzaky.domain.model.Weather
import com.dzaky.presentation.R
import kotlin.math.roundToInt

@Composable
fun WeatherView(
    modifier: Modifier = Modifier,
    weather: Weather
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .wrapContentSize()
        ) {
            WeImage(
                imageUrl = weather.icon,
                contentDescription = weather.name,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(170.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = weather.name,
                    style = MaterialTheme.typography.headlineLarge
                )
                Icon(
                    painter = painterResource(R.drawable.ic_navigation),
                    contentDescription = null
                )
            }

            TemperatureText(
                temperature = weather.tempInCelsius,
                textStyle = MaterialTheme.typography.displayLarge,
                degreeFontSize = 22.sp
            )

            Spacer(Modifier.height(24.dp))

            Surface(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    ContentItem(
                        label = stringResource(R.string.humidity),
                        value = {
                            Text(
                                text = "${weather.humidity}%",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    )
                    ContentItem(
                        label = stringResource(R.string.uv),
                        value = {
                            Text(
                                text = weather.uv.roundToInt().toString(),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    )
                    ContentItem(
                        label = stringResource(R.string.feels_like),
                        value = {
                            TemperatureText(
                                temperature = weather.feelsLikeInCelsius,
                                textStyle = MaterialTheme.typography.titleMedium,
                                degreeFontSize = 16.sp,
                                degreeBaselineShift = BaselineShift(0.0f)
                            )
                        }
                    )
                }
            }
        }
    }
}


@Composable
private fun ContentItem(
    label: String,
    value: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium
        )
        value()
    }
}

@Preview
@Composable
private fun WeatherViewPreview() {
    WeatherView(
        modifier = Modifier.fillMaxSize(),
        weather = Weather(
            id = 123213,
            name = "Bekasi",
            lat = 12.2,
            lon = 33.2
        )
    )
}