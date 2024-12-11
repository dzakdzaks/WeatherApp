package com.dzaky.core_ui.util

import android.content.Context
import com.dzaky.core.R
import com.dzaky.core_ui.model.DataError

sealed class UiText {
    data class DynamicString(val text: String): UiText()
    data class StringResource(val resId: Int): UiText()

    fun asString(context: Context): String {
        return when(this) {
            is DynamicString -> text
            is StringResource -> context.getString(resId)
        }
    }
}

fun DataError.toUiText(): UiText {
    val stringRes = when (this) {
        DataError.Remote.SERVER, DataError.Remote.UNKNOWN -> R.string.error_unknown
        DataError.Remote.REQUEST_TIMEOUT -> R.string.error_request_timeout
        DataError.Remote.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
        DataError.Remote.NO_INTERNET -> R.string.error_no_internet
        DataError.Remote.SERIALIZATION -> R.string.error_serialization
    }

    return UiText.StringResource(stringRes)
}