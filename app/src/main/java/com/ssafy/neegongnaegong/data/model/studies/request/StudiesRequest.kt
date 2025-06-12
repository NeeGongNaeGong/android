package com.ssafy.neegongnaegong.data.model.studies.request

data class StudiesRequest(
    val name: String,
    val maxMembers: Int,
    val title: String,
    val description: String,
    val profileImage: String?,
    val isPublic: Boolean,
    val targetStudyTime: Int,
    val category: Int,
    val tags: List<Unit>, // TODO : tag지정
)
