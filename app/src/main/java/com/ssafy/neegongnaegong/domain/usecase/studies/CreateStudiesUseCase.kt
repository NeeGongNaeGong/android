package com.ssafy.neegongnaegong.domain.usecase.studies

import android.util.Log
import com.ssafy.neegongnaegong.domain.model.file.PresignedUrlInfo
import com.ssafy.neegongnaegong.domain.model.file.UploadPathType
import com.ssafy.neegongnaegong.domain.model.studies.StudyInfo
import com.ssafy.neegongnaegong.domain.repository.FileRepository
import com.ssafy.neegongnaegong.domain.repository.S3Repository
import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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
        operator fun invoke(
            studyInfo: StudyInfo,
            imageRequestBody: RequestBody?,
            imageExtension: String?,
        ): Flow<Unit> =
            flow {
                try {
                    // 1. 스터디 생성 API 호출 -> studyGroupId 발급
                    val studyGroupId = studiesRepository.createStudies(studyInfo).first()
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
                            ).first()
                        // 구독 필요
                    }
                    emit(Unit)
                } catch (e: Exception) {
                    Log.e("CreateStudiesUseCase", "${e.message}")
                    throw e
                }
            }
    }
