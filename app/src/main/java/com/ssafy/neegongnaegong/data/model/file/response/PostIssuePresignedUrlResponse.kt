package com.ssafy.neegongnaegong.data.model.file.response

import com.ssafy.neegongnaegong.domain.model.file.PresignedUrlInfo

data class PostIssuePresignedUrlResponse(
    val presignedUrl: String,
    val imageUrl: String,
) {
    fun toDomain(): PresignedUrlInfo =
        PresignedUrlInfo(
            presignedUrl = presignedUrl,
            imageUrl = imageUrl,
        )
}
