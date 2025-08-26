package com.vaasudev.androidcomposestructure.domain.repository

import com.vaasudev.androidcomposestructure.domain.response.UserDetailsResponse
import com.vaasudev.androidcomposestructure.domain.utility.Result
import com.vaasudev.androidcomposestructure.domain.utility.error_handle.NetworkError
import kotlinx.coroutines.flow.Flow

/** # Repository - `AuthRepository`
 * for authentication purpose*/
interface AuthRepository {
    suspend fun userDetails(): Flow<Result<UserDetailsResponse, NetworkError>>
}