package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.apiFlow
import com.ssafy.neegongnaegong.data.model.file.request.PostIssuePresignedUrlRequest
import com.ssafy.neegongnaegong.data.model.file.response.PostIssuePresignedUrlResponse
import com.ssafy.neegongnaegong.data.remote.FileApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkFileDataSourceImpl
    @Inject
    constructor(
        private val fileApi: FileApi,
    ) : NetworkFileDataSource {
        override suspend fun issuePresignedUrl(request: PostIssuePresignedUrlRequest): Flow<PostIssuePresignedUrlResponse> =
            apiFlow {
                fileApi.issuePreSignedUrl(request)
            }
    }
