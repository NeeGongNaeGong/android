package com.ssafy.neegongnaegong.data.local

import kotlinx.coroutines.flow.Flow

interface LocalStorageManager {
    fun<T> saveData(key: String, value: T)
    fun<T> getData(key: String): T?
    fun<T> getDataFlow(key: String, clazz: Class<T>): Flow<T?>
    fun removeData(key: String)
    fun clearData()
}
