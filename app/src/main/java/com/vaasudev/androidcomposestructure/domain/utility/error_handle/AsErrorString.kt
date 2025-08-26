package com.vaasudev.androidcomposestructure.domain.utility.error_handle

import android.content.Context
import com.vaasudev.androidcomposestructure.R
import io.ktor.client.plugins.ClientRequestException
import java.io.FileNotFoundException
import java.net.UnknownHostException

fun Throwable.toCustomExceptions(): NetworkError = when (this) {
    is ClientRequestException ->
        when (this.response.status.value) {
            400 -> NetworkError.CLIENT_BAD_REQUEST
            401 -> NetworkError.CLIENT_UNAUTHORIZED
            403 -> NetworkError.CLIENT_FORBIDDEN
            404 -> NetworkError.CLIENT_NOT_FOUND
            429 -> NetworkError.TOO_MANY_REQUEST
            else -> NetworkError.UNKNOWN
        }
    is IllegalArgumentException -> NetworkError.ILLEGAL_ARGUMENT_EXCEPTION
    is IllegalStateException -> NetworkError.UNKNOWN
    is UnknownHostException -> NetworkError.NO_INTERNET_AVAILABLE
    is NullPointerException -> NetworkError.NULL_POINTER_EXCEPTION
    is FileNotFoundException -> NetworkError.FILE_NOT_FOUND_EXCEPTION
    else -> NetworkError.UNKNOWN
}

fun Int.checkStatus(): NetworkError = when (this) {
    400 -> NetworkError.CLIENT_BAD_REQUEST
    401 -> NetworkError.CLIENT_UNAUTHORIZED
    403 -> NetworkError.CLIENT_FORBIDDEN
    404 -> NetworkError.CLIENT_NOT_FOUND
    429 -> NetworkError.TOO_MANY_REQUEST
    else -> NetworkError.UNKNOWN
}

fun NetworkError.asNetworkErrorString(context: Context): String = when (this) {
    NetworkError.SERVER_RESPONSE_EXCEPTION -> context.getString(R.string.error_server_response_error)
    NetworkError.CLIENT_BAD_REQUEST -> context.getString(R.string.error_bad_request)
    NetworkError.CLIENT_UNAUTHORIZED -> context.getString(R.string.error_unauthorized)
    NetworkError.CLIENT_FORBIDDEN -> context.getString(R.string.error_forbidden)
    NetworkError.CLIENT_NOT_FOUND -> context.getString(R.string.error_not_found)
    NetworkError.REDIRECT_RESPONSE_EXCEPTION -> context.getString(R.string.error_redirect_response_error)
    NetworkError.UNKNOWN -> context.getString(R.string.error_something_want_to_wrong)
    NetworkError.ILLEGAL_ARGUMENT_EXCEPTION -> context.getString(R.string.error_illegal_argument)
    NetworkError.NO_INTERNET_AVAILABLE -> context.getString(R.string.error_no_internet_available)
    NetworkError.NULL_POINTER_EXCEPTION -> context.getString(R.string.error_something_want_to_wrong)
    NetworkError.TOO_MANY_REQUEST -> context.getString(R.string.error_too_many_request)
    NetworkError.FILE_NOT_FOUND_EXCEPTION -> context.getString(R.string.file_not_found_exception)
}