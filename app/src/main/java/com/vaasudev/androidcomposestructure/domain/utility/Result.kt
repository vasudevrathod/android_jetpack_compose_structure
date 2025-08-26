package com.vaasudev.androidcomposestructure.domain.utility

import com.vaasudev.androidcomposestructure.domain.dto.ErrorDto
import com.vaasudev.androidcomposestructure.domain.utility.error_handle.Error

typealias RootError = Error

sealed interface Result<out D, out E: RootError> {
    data class Success<out D, out E: RootError>(val data: D): Result<D, E>
    data class Error<out D, out E: RootError>(val error: E): Result<D, E>
}

sealed class Status {
    data object Loading : Status()
    data class Success<out D>(val data: D): Status()
    //data class Error<out E>(val error: E): Status()
    data class Error(val error: ErrorDto): Status()
}