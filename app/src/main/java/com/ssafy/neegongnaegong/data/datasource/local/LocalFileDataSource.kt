package com.ssafy.neegongnaegong.data.datasource.local

import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface LocalFileDataSource {
    fun getFileType(url: String): Flow<String?>

    fun getRequestBody(url: String): Flow<RequestBody?>
}
