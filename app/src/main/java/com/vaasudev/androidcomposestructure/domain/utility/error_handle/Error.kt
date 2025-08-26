package com.vaasudev.androidcomposestructure.domain.utility.error_handle

sealed interface Error

enum class NetworkError(val status: Int) : Error {
    SERVER_RESPONSE_EXCEPTION(500),
    CLIENT_BAD_REQUEST(400),
    CLIENT_UNAUTHORIZED(401),
    CLIENT_FORBIDDEN(403),
    CLIENT_NOT_FOUND(404),
    REDIRECT_RESPONSE_EXCEPTION(307),
    ILLEGAL_ARGUMENT_EXCEPTION(400),
    NO_INTERNET_AVAILABLE(503),
    NULL_POINTER_EXCEPTION(500),
    UNKNOWN(520),
    TOO_MANY_REQUEST(429),
    FILE_NOT_FOUND_EXCEPTION(404)
}

enum class InitManage : Error {
    OPTIONAL_UPDATE,
    REQUIRED_UPDATE,
    UNDER_MAINTENANCE,
    CONTINUE
}