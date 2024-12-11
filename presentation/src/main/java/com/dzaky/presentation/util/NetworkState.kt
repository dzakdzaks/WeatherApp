package com.dzaky.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import com.dzaky.core_ui.util.NetworkConnectionState
import com.dzaky.core_ui.util.currentConnectivityState
import com.dzaky.core_ui.util.observeConnectivityAsFlow

@Composable
fun rememberConnectivityState(): State<NetworkConnectionState> {
    val context = LocalContext.current

    return produceState(initialValue = context.currentConnectivityState) {
        context.observeConnectivityAsFlow().collect {
            value = it
        }
    }
}