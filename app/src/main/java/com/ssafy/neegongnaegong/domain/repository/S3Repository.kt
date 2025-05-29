package com.ssafy.neegongnaegong.domain.repository

import okhttp3.RequestBody

interface S3Repository {
    suspend fun uploadImageToS3(
        presignedUrl: String,
        request: RequestBody,
    )
}
