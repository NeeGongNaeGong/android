package com.ssafy.neegongnaegong.data.remote

import com.ssafy.neegongnaegong.data.model.ApiResponse
import com.ssafy.neegongnaegong.data.model.file.request.PostIssuePresignedUrlRequest
import com.ssafy.neegongnaegong.data.model.file.response.PostIssuePresignedUrlResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface FileApi {
    @POST("api/files")
    suspend fun issuePreSignedUrl(
        @Body request: PostIssuePresignedUrlRequest,
    ): Result<ApiResponse<PostIssuePresignedUrlResponse>>
}
