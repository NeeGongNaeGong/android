package com.ssafy.neegongnaegong.data.datasource.network

import okhttp3.RequestBody

interface NetworkS3DataSource {
    suspend fun uploadImageToS3(
        presignedUrl: String,
        request: RequestBody,
    )
}
