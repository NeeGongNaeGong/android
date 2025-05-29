package com.ssafy.neegongnaegong.domain.repository

import com.ssafy.neegongnaegong.domain.model.file.PresignedUrlInfo
import kotlinx.coroutines.flow.Flow

interface FileRepository {
    suspend fun issuePresignedUrl(
        id: Long,
        uploadPathType: String,
        imageExtension: String,
    ): Flow<PresignedUrlInfo>
}
