package com.sarftec.lovequotes.application.file

import android.content.Context
import android.content.res.Configuration
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

val APP_CREATED = booleanPreferencesKey("app_created")
val ENABLE_VIBRATION = booleanPreferencesKey("enable_vibration")
val APP_START_COUNT  = intPreferencesKey("app_start_count")
val SHOW_RATINGS = booleanPreferencesKey("show_ratings_interval")

val Context.dataStore by preferencesDataStore(name = "settings_store")

fun <T> Context.readSettings(key: Preferences.Key<T>, default: T): Flow<T> {
    return dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw Exception("Setting data store exception occurred")
        }
        .map { preferences ->
            preferences[key] ?: default
        }
}

suspend fun <T> Context.editSettings(key: Preferences.Key<T>, value: T) {
    dataStore.edit { preferences ->
        preferences[key] = value
    }
}

fun Context.isPhoneInDarkMode() : Boolean {
    val flag = resources.configuration.uiMode  and Configuration.UI_MODE_NIGHT_MASK
    return flag == Configuration.UI_MODE_NIGHT_YES
}

fun CoroutineScope.vibrate(context: Context) {
    launch {
        if(context.readSettings(ENABLE_VIBRATION, true).first()) context.vibrate()
    }
}