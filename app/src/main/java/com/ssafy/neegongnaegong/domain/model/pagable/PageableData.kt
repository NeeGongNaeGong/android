package com.ssafy.neegongnaegong.domain.model.pagable

data class PageableData<T>(
    val pageNo: Long,
    val isLast: Boolean,
    val hasNext: Boolean,
    val getNumberOfElements: Long,
    val pageable: PageableInfo,
    val data: List<T>
)
