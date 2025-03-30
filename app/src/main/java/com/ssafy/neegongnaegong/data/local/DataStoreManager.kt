package com.ssafy.neegongnaegong.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = "app_preferences")

class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) : LocalStorageManager {
    private val gson = Gson()

    override fun <T> saveData(key: String, value: T) {
        runBlocking {
            context.dataStore.edit { preferences ->
                preferences[stringPreferencesKey(key)] = gson.toJson(value)
            }
        }
    }

    override fun <T> getData(key: String): T? {
        return runBlocking {
            val preferences = context.dataStore.data.first()
            preferences[stringPreferencesKey(key)]?.let { json ->
                gson.fromJson(json, object : TypeToken<T>() {}.type)
            }
        }
    }

    override fun removeData(key: String) {
        runBlocking {
            context.dataStore.edit { preferences ->
                preferences.remove(stringPreferencesKey(key))
            }
        }
    }

    override fun clearData() {
        runBlocking {
            context.dataStore.edit { it.clear() }
        }
    }
}