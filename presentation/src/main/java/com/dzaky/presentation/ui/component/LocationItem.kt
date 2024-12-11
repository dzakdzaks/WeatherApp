package com.dzaky.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dzaky.domain.model.Weather

@Composable
fun LocationItem(
    location: Weather,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 32.dp
                )
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f).padding(top = 16.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = location.name,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        textAlign = TextAlign.Start
                    ),
                )
                TemperatureText(
                    temperature = location.tempInCelsius,
                    textStyle = MaterialTheme.typography.displayMedium,
                    degreeFontSize = 18.sp
                )
            }

            WeImage(
                imageUrl = location.icon,
                contentDescription = location.name,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(100.dp)
            )
        }
    }
}

@Preview
@Composable
private fun LocationItemPreview() {
    LocationItem(
        location = Weather(
            id = 123231,
            name = "London",
            lat = 123.1,
            lon = 322.5
        ),
        onClick = {}
    )
}