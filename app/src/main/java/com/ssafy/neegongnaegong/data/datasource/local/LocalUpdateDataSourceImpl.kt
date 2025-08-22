package com.ssafy.neegongnaegong.data.datasource.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUpdateDataSourceImpl
    @Inject
    constructor(
        private val dataStore: DataStore<Preferences>,
    ) : LocalUpdateDataSource {
        private object PreferencesKeys {
            val SKIPPED_VERSION_CODE = intPreferencesKey("skipped_version_code")
        }

        override fun getSkippedVersionCode(): Flow<Int> =
            dataStore.data.map { preferences ->
                preferences[PreferencesKeys.SKIPPED_VERSION_CODE] ?: 0
            }

        override suspend fun saveSkippedVersionCode(versionCode: Int) {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.SKIPPED_VERSION_CODE] = versionCode
            }
        }
    }
