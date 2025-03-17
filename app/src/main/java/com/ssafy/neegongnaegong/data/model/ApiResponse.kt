package com.ssafy.neegongnaegong.data.model

data class ApiResponse<T>(
    val message: String,
    val data : T
)
