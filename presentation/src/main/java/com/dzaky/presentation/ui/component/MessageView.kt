package com.dzaky.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dzaky.presentation.R

@Composable
fun MessageView(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 200.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = subtitle,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ErrorMessage(message: String, modifier: Modifier = Modifier) {
    MessageView(
        title = stringResource(com.dzaky.core.R.string.error),
        subtitle = message,
        modifier = modifier
    )
}

@Composable
fun NoCitySelectedMessage(modifier: Modifier = Modifier) {
    MessageView(
        title = stringResource(R.string.no_city_selected),
        subtitle = stringResource(R.string.please_search_for_a_city),
        modifier = modifier
    )
}

@Composable
fun NoLocationFoundMessage(modifier: Modifier = Modifier) {
    MessageView(
        title = stringResource(R.string.no_location_found),
        subtitle = stringResource(R.string.please_search_with_other_keywords),
        modifier = modifier
    )
}

@Preview
@Composable
fun MessageViewPreview() {
    MessageView(
        title = stringResource(R.string.no_city_selected),
        subtitle = stringResource(R.string.please_search_for_a_city)
    )
}