package com.vaasudev.androidcomposestructure.data.repository

import android.content.Context
import com.vaasudev.androidcomposestructure.domain.repository.AuthRepository
import com.vaasudev.androidcomposestructure.domain.response.UserDetailsResponse
import com.vaasudev.androidcomposestructure.domain.utility.Result
import com.vaasudev.androidcomposestructure.domain.utility.error_handle.NetworkError
import com.vaasudev.androidcomposestructure.domain.utility.error_handle.toCustomExceptions
import com.vaasudev.androidcomposestructure.domain.utility.isConnected
import com.vaasudev.composesigninsignup.domain.ktor.KtorUtility
import com.vaasudev.androidcomposestructure.domain.ktor.manageResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException
import javax.inject.Inject

/** # `HTTP Client` - Ktor
 * how to:
 * [Manage Http Response](https://ktor.io/docs/client-responses.html) check this link for more info.
 * */
class AuthRepositoryImpl @Inject constructor(
    private val context: Context,
    private val httpClient: HttpClient
) : AuthRepository {
    override suspend fun userDetails(): Flow<Result<UserDetailsResponse, NetworkError>> =
        flow<Result<UserDetailsResponse, NetworkError>> {
            try {
                if (!isConnected(context)) throw UnknownHostException()

                emit(manageResponse(httpClient.get(KtorUtility.EndPoint.GET_EMPLOYEE_DETAILS)))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(e.toCustomExceptions()))
            }
        }.catch {
            emit(Result.Error(it.toCustomExceptions()))
        }
}