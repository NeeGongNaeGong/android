package com.ssafy.neegongnaegong.data.model.file.request

data class PostIssuePresignedUrlRequest(
    val id: Long,
    val uploadPathType: String,
    val imageExtension: String,
)
