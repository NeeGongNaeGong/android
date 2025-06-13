package com.ssafy.neegongnaegong.domain.usecase.user

import com.ssafy.neegongnaegong.domain.exception.UnsupportedFileTypeException
import com.ssafy.neegongnaegong.domain.model.file.PresignedUrlInfo
import com.ssafy.neegongnaegong.domain.model.file.UploadPathType
import com.ssafy.neegongnaegong.domain.repository.FileRepository
import com.ssafy.neegongnaegong.domain.repository.LocalFileRepository
import com.ssafy.neegongnaegong.domain.repository.S3Repository
import com.ssafy.neegongnaegong.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okhttp3.RequestBody
import javax.inject.Inject

class UpdateProfileImageUseCase
    @Inject
    constructor(
        private val localFileRepository: LocalFileRepository,
        private val networkFileRepository: FileRepository,
        private val s3Repository: S3Repository,
        private val userRepository: UserRepository,
    ) {
        @OptIn(ExperimentalCoroutinesApi::class)
        operator fun invoke(
            userId: Long,
            url: String,
        ): Flow<Unit> =
            flow<PresignedUrlInfo> {
                val fileType: String? = localFileRepository.getFileType(url = url).first()
                if (fileType == null) throw UnsupportedFileTypeException()
                val path: String = UploadPathType.PROFILE_USER.path
                val presignedUrlFlow: Flow<PresignedUrlInfo> =
                    networkFileRepository.issuePresignedUrl(
                        id = userId,
                        uploadPathType = path,
                        imageExtension = fileType,
                    )
                emitAll(flow = presignedUrlFlow)
            }.map { presignedUrl: PresignedUrlInfo ->
                val request: RequestBody? = localFileRepository.getRequestBody(url = url).first()
                if (request == null) throw UnsupportedFileTypeException()
                s3Repository.uploadImageToS3(
                    presignedUrl = presignedUrl.presignedUrl,
                    request = request,
                )
                presignedUrl.imageUrl
            }.flatMapLatest { imageUrl: String ->
                userRepository.updateProfileImage(profileImg = imageUrl)
            }
    }
