package com.dzaky.presentation.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt


@Composable
fun TemperatureText(
    temperature: Double,
    textStyle: TextStyle,
    degreeFontSize: TextUnit,
    degreeBaselineShift: BaselineShift = BaselineShift.Superscript,
) {
    Text(
        text = buildAnnotatedString {
            append(temperature.roundToInt().toString()) // Add main temperature
            withStyle(
                SpanStyle(
                    fontSize = degreeFontSize, // Smaller font size for degree
                    baselineShift = degreeBaselineShift
                )
            ) {
                append("°") // Add degree symbol
            }
        },
        style = textStyle
    )
}

@Preview
@Composable
private fun TemperatureTextPreview() {
    TemperatureText(
        temperature = 22.8,
        textStyle = MaterialTheme.typography.displayMedium,
        degreeFontSize = 30.sp
    )
}