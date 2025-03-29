package com.ssafy.neegongnaegong.data.local

interface LocalStorageManager {
    fun<T> saveData(key: String, value: T)
    fun<T> getData(key: String): T?
    fun removeData(key: String)
    fun clearData()
}