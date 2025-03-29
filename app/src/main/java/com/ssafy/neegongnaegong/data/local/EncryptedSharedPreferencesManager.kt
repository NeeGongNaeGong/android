package com.ssafy.neegongnaegong.data.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class EncryptedSharedPreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) : LocalStorageManager {
    private val gson = Gson()

    private val sharedPreferences by lazy {
        EncryptedSharedPreferences(
            context,
            "secure_prefs", // TODO: 숨겨야하는지 생각해보기
            MasterKey(context),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun <T> saveData(key: String, value: T) {
        val editor = sharedPreferences.edit()
        editor.putString(key, gson.toJson(value))
        editor.apply()
    }

    override fun <T> getData(key: String): T? {
        val data = sharedPreferences.getString(key, null)
        return gson.fromJson(data, object : TypeToken<T>() {}.type)
    }

    override fun removeData(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }

    override fun clearData() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}