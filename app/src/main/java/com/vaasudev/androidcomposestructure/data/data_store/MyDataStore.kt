package com.vaasudev.androidcomposestructure.data.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "localData")


class MyDataStore @Inject constructor(private val context: Context) {

    object PrefKey {
        const val FCM_TOKEN = "token"
        const val X_API_KEY = "x_api_key"
        const val THEME_BRAND = "theme_brand"
        const val DARK_THEME_CONFIG = "dark_theme_config"
        const val USE_DYNAMIC_COLOR = "use_dynamic_color"
    }

    val token = stringPreferencesKey(PrefKey.FCM_TOKEN)
    val xApiKey = stringPreferencesKey(PrefKey.X_API_KEY)
    val themeBrand = intPreferencesKey(PrefKey.THEME_BRAND)
    val darkThemeConfig = intPreferencesKey(PrefKey.DARK_THEME_CONFIG)
    val useDynamicColor = booleanPreferencesKey(PrefKey.USE_DYNAMIC_COLOR)

    /** # `Set Data` - DataStore Functions */
    suspend fun setStringData(key: Preferences.Key<String>, value: String) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun setIntData(key: Preferences.Key<Int>, value: Int) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun setBooleanData(key: Preferences.Key<Boolean>, value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun setLongData(key: Preferences.Key<Long>, value: Long) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    /** # `Get Data` - DataStore Functions */
    fun getStringData(key: Preferences.Key<String>): Flow<String> {
        return context.dataStore.data.map {
            it[key] ?: ""
        }
    }

    fun getIntData(key: Preferences.Key<Int>): Flow<Int> {
        return context.dataStore.data.map {
            it[key] ?: -1
        }
    }

    fun getBooleanData(key: Preferences.Key<Boolean>): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[key] ?: false
        }
    }

    fun getLongData(key: Preferences.Key<Long>): Flow<Long> {
        return context.dataStore.data.map { preferences ->
            preferences[key] ?: 0L
        }
    }

    /** # `For All Data` - DataStore Functions */
    suspend fun clearDataStore() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}