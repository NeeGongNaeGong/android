package com.ssafy.neegongnaegong.domain.model

data class Studies(
    val id: Long,
    val category: String,
    val title: String,
    val goalTime: String,
    val memberInfo: String,
    val leader: String,
    val startInfo: String,
    val description: String,
    val imageUrl: String? = null
)
