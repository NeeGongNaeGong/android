package com.ssafy.neegongnaegong.domain.usecase.file

import com.ssafy.neegongnaegong.domain.model.file.PresignedUrlInfo
import com.ssafy.neegongnaegong.domain.repository.FileRepository
import kotlinx.coroutines.flow.Flow

class IssuePresignedUrlUseCase(
    private val fileRepository: FileRepository,
) {
    suspend operator fun invoke(
        uploadPathType: String,
        imageExtension: String,
    ): Flow<PresignedUrlInfo> =
        fileRepository.issuePresignedUrl(
            id = 0,
            uploadPathType = uploadPathType,
            imageExtension = imageExtension,
        )
}
