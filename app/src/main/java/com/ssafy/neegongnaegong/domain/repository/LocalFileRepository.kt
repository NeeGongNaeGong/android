package com.ssafy.neegongnaegong.domain.repository

import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface LocalFileRepository {
    fun getFileType(url: String): Flow<String?>

    fun getRequestBody(url: String): Flow<RequestBody?>
}
