package com.ssafy.neegongnaegong.domain.usecase.studies

import com.ssafy.neegongnaegong.domain.model.file.PresignedUrlInfo
import com.ssafy.neegongnaegong.domain.model.file.UploadPathType
import com.ssafy.neegongnaegong.domain.model.studies.StudyInfo
import com.ssafy.neegongnaegong.domain.repository.FileRepository
import com.ssafy.neegongnaegong.domain.repository.S3Repository
import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody
import javax.inject.Inject

class CreateStudiesUseCase
    @Inject
    constructor(
        private val studiesRepository: StudiesRepository,
        private val fileRepository: FileRepository,
        private val s3Repository: S3Repository,
    ) {
        @OptIn(ExperimentalCoroutinesApi::class)
        operator fun invoke(
            studyInfo: StudyInfo,
            imageRequestBody: RequestBody?,
            imageExtension: String?,
        ): Flow<Unit> {
            // 1. 스터디 생성 API 호출 -> studyGroupId 발급
            return studiesRepository.createStudies(studyInfo).flatMapLatest { studyGroupId ->
                // 2. studyGroupId로 Presigned URL 발급
                val presignedUrlInfo: PresignedUrlInfo? =
                    imageExtension?.let {
                        fileRepository
                            .issuePresignedUrl(
                                id = studyGroupId,
                                uploadPathType = UploadPathType.PROFILE_STUDY.path,
                                imageExtension = it,
                            ).first()
                    }
                if (presignedUrlInfo != null && imageRequestBody != null) {
                    // 3. 발급 받은 URL로 S3에 이미지 업로드
                    s3Repository.uploadImageToS3(
                        presignedUrl = presignedUrlInfo.presignedUrl,
                        request = imageRequestBody,
                    )
                    // 4. 등록한 이미지 변경
                    studiesRepository
                        .changeStudiesProfileImage(
                            studyGroupId = studyGroupId,
                            profileImage = presignedUrlInfo.imageUrl,
                        )
                } else {
                    flow { emit(Unit) }
                }
            }
        }
    }
