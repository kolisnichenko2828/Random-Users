package com.kolisnichenko2828.randomusers.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.kolisnichenko2828.randomusers.R

sealed class AppException(
    val messageResId: Int,
    cause: Throwable? = null
) : Exception(cause) {
    class DatabaseError : AppException(R.string.database_error)
    class NoInternetConnection : AppException(R.string.error_no_internet)
    class RateLimitExceeded : AppException(R.string.error_rate_limit)
    class ServerError : AppException(R.string.error_server)
    class Timeout : AppException(R.string.error_timeout)
    class Unknown : AppException(R.string.error_unknown)
}

@Composable
fun Throwable.toUserReadableMessage(): String {
    return if (this is AppException) {
        stringResource(this.messageResId)
    } else {
        stringResource(R.string.error_unknown)
    }
}