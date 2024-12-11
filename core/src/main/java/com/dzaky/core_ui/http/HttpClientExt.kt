package com.dzaky.core_ui.http

import com.dzaky.core_ui.model.DataError
import com.dzaky.core_ui.model.WeResult
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import java.net.UnknownHostException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): WeResult<T, DataError.Remote> {
    val response = try {
        execute()
    } catch (e: SocketTimeoutException) {
        println("safeCall: SocketTimeoutException occurred")
        return WeResult.Error(DataError.Remote.REQUEST_TIMEOUT)
    } catch (e: UnresolvedAddressException) {
        println("safeCall: UnresolvedAddressException occurred")
        return WeResult.Error(DataError.Remote.NO_INTERNET)
    }catch (e: UnknownHostException) {
        println("safeCall: UnknownHostException occurred - Unable to resolve host")
        return WeResult.Error(DataError.Remote.NO_INTERNET)
    }  catch (e: Exception) {
        coroutineContext.ensureActive()
        println("safeCall: Exception occurred - ${e.message}")
        return WeResult.Error(DataError.Remote.UNKNOWN)
    }

    println("safeCall: Received response with status ${response.status.value}")
    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): WeResult<T, DataError.Remote> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                println("responseToResult: Success with status ${response.status.value}")
                WeResult.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                println("responseToResult: Serialization error occurred")
                WeResult.Error(DataError.Remote.SERIALIZATION)
            }
        }
        408 -> {
            println("responseToResult: Request timeout with status ${response.status.value}")
            WeResult.Error(DataError.Remote.REQUEST_TIMEOUT)
        }
        429 -> {
            println("responseToResult: Too many requests with status ${response.status.value}")
            WeResult.Error(DataError.Remote.TOO_MANY_REQUESTS)
        }
        in 500..599 -> {
            println("responseToResult: Server error with status ${response.status.value}")
            WeResult.Error(DataError.Remote.SERVER)
        }
        else -> {
            println("responseToResult: Unknown error with status ${response.status.value}")
            WeResult.Error(DataError.Remote.UNKNOWN)
        }
    }
}
