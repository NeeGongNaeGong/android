package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.file.request.PostIssuePresignedUrlRequest
import com.ssafy.neegongnaegong.data.model.file.response.PostIssuePresignedUrlResponse
import kotlinx.coroutines.flow.Flow

interface NetworkFileDataSource {
    suspend fun issuePresignedUrl(request: PostIssuePresignedUrlRequest): Flow<PostIssuePresignedUrlResponse>
}
