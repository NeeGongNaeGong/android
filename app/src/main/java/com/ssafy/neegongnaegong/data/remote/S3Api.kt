package com.ssafy.neegongnaegong.data.remote

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Url

interface S3Api {
    @PUT
    suspend fun uploadImageToS3(
        @Url presignedUrl: String,
        @Body body: RequestBody,
    ): Response<Unit>
}
