package com.ssafy.neegongnaegong.domain.usecase.file

import com.ssafy.neegongnaegong.domain.model.file.PresignedUrlInfo
import com.ssafy.neegongnaegong.domain.repository.FileRepository
import kotlinx.coroutines.flow.Flow

class IssuePresignedUrlUseCase(
    private val fileRepository: FileRepository,
) {
    suspend operator fun invoke(
        studyGroupId: Long,
        uploadPathType: String,
        imageExtension: String,
    ): Flow<PresignedUrlInfo> =
        fileRepository.issuePresignedUrl(
            id = studyGroupId,
            uploadPathType = uploadPathType,
            imageExtension = imageExtension,
        )
}
