package com.dzaky.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dzaky.domain.model.Weather

@Composable
fun LocationList(
    listState: LazyListState = rememberLazyListState(),
    items: List<Weather>,
    onClick: (Weather) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        state = listState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = 24.dp, horizontal = 16.dp)
    ) {
        items(
            items = items,
            key = { it.id }
        ) { location ->
            LocationItem(
                modifier = Modifier
                    .fillMaxWidth(),
                location = location,
                onClick = { onClick(location) }
            )
        }
    }
}