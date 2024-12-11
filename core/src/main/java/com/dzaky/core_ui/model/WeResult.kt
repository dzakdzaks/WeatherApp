package com.dzaky.core_ui.model

sealed interface WeResult<out D, out E: WeError> {
    data class Success<out D>(val data: D): WeResult<D, Nothing>
    data class Error<out E: WeError>(val error: E):
        WeResult<Nothing, E>
}

inline fun <T, E: WeError, R> WeResult<T, E>.map(map: (T) -> R): WeResult<R, E> {
    return when(this) {
        is WeResult.Error -> WeResult.Error(error)
        is WeResult.Success -> WeResult.Success(map(data))
    }
}

fun <T, E: WeError> WeResult<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {  }
}

inline fun <T, E: WeError> WeResult<T, E>.onSuccess(action: (T) -> Unit): WeResult<T, E> {
    return when(this) {
        is WeResult.Error -> this
        is WeResult.Success -> {
            action(data)
            this
        }
    }
}
inline fun <T, E: WeError> WeResult<T, E>.onError(action: (E) -> Unit): WeResult<T, E> {
    return when(this) {
        is WeResult.Error -> {
            action(error)
            this
        }
        is WeResult.Success -> this
    }
}

typealias EmptyResult<E> = WeResult<Unit, E>