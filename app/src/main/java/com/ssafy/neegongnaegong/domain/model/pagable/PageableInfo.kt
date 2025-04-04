package com.ssafy.neegongnaegong.domain.model.pagable

data class PageableInfo(
    val offset: Long,
    val sort: PageableSort,
    val paged: Boolean,
    val pageNumber: Long,
    val pageSize: Long,
    val unpaged: Boolean
)
