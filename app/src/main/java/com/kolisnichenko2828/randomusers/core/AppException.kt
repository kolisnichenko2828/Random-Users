package com.kolisnichenko2828.randomusers.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.kolisnichenko2828.randomusers.R

sealed class AppException(cause: Throwable? = null) : Exception(cause) {
    class DatabaseError(cause: Throwable? = null) : AppException(cause)
    class NoInternetConnection(cause: Throwable? = null) : AppException(cause)
    class RateLimitExceeded(cause: Throwable? = null) : AppException(cause)
    class ServerError(cause: Throwable? = null) : AppException(cause)
    class Timeout(cause: Throwable? = null) : AppException(cause)
    class Unknown(cause: Throwable? = null) : AppException(cause)
}

@Composable
fun Throwable.toUserReadableMessage(): String {
    return when (this) {
        is AppException.DatabaseError -> ""
        is AppException.NoInternetConnection -> stringResource(R.string.error_no_internet)
        is AppException.RateLimitExceeded -> stringResource(R.string.error_rate_limit)
        is AppException.ServerError -> stringResource(R.string.error_server)
        is AppException.Timeout -> stringResource(R.string.error_timeout)
        else -> stringResource(R.string.error_unknown)
    }
}