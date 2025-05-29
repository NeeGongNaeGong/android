package com.ssafy.neegongnaegong.domain.usecase.s3

import com.ssafy.neegongnaegong.domain.repository.S3Repository
import okhttp3.RequestBody

class UploadImageToS3UseCase(
    private val s3Repository: S3Repository,
) {
    suspend operator fun invoke(
        presignedUrl: String,
        request: RequestBody,
    ) {
        s3Repository.uploadImageToS3(
            presignedUrl = presignedUrl,
            request = request,
        )
    }
}
