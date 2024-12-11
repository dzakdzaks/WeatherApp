package com.dzaky.core_ui.model

sealed interface DataError: WeError {
    enum class Remote: DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERIALIZATION,
        SERVER,
        UNKNOWN,
    }
}