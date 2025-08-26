package com.vaasudev.androidcomposestructure._di

import android.app.Application
import android.content.Context
import com.vaasudev.androidcomposestructure.BuildConfig
import com.vaasudev.androidcomposestructure.data.data_store.MyDataStore
import com.vaasudev.androidcomposestructure.data.repository.AuthRepositoryImpl
import com.vaasudev.androidcomposestructure.domain.repository.AuthRepository
import com.vaasudev.androidcomposestructure.domain.utility.printLog
import com.vaasudev.composesigninsignup.domain.ktor.KtorUtility
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /** # Application Context - `Context`  */
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    /** # DataStore - `MyDataStore` */
    @Provides
    @Singleton
    fun provideMyDataStore(context: Context): MyDataStore {
        return MyDataStore(context)
    }

    /** # Ktor - `HTTP Client`
     *
     * For a full guide on setting up Ktor Client, check this article:
     * [How to use Ktor Client on Android](https://medium.com/google-developer-experts/how-to-use-ktor-client-on-android-dcdeddc066b9)
     * */
    @Provides
    @Singleton
    fun httpClient(dataStore: MyDataStore): HttpClient {
        return HttpClient(Android) {

            engine {
                connectTimeout = KtorUtility.Client.HTTP_CLIENT_TIMEOUT
                socketTimeout = KtorUtility.Client.HTTP_CLIENT_TIMEOUT
            }

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                    allowStructuredMapKeys = true
                })

                register(
                    ContentType.Text.Html, KotlinxSerializationConverter(
                        Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                        }
                    )
                )
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        printLog("Logger Ktor =>", message)
                    }
                }
                level = LogLevel.ALL
            }

            install(ResponseObserver) {
                onResponse { response ->
                    printLog("HTTP status:", "${response.status.value}")
                    printLog("HTTP Content Type:", "${response.contentType()}")
                }
            }

            defaultRequest {
                url(BuildConfig.BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }

            install(DefaultRequest) {
                url(BuildConfig.BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)

                val xApiKey = runBlocking {
                    dataStore.getStringData(dataStore.xApiKey).first()
                }

                if (xApiKey.isNotEmpty()) {
                    headers.append(KtorUtility.HeaderKey.X_API_KEY, xApiKey)
                }

                printLog(tag = "Header", value = "${KtorUtility.HeaderKey.X_API_KEY} - $xApiKey")
            }
        }
    }

    /** # Repository - `Auth Repository` */
    @Provides
    @Singleton
    fun providesAuthRepository(context: Context, httpClient: HttpClient): AuthRepository {
        return AuthRepositoryImpl(context = context, httpClient = httpClient)
    }
}