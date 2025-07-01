package com.ssafy.neegongnaegong.data.mapper.studygroup

import com.ssafy.neegongnaegong.data.model.studygroup.response.MyStudyGroupListResponse
import com.ssafy.neegongnaegong.domain.model.studygroup.MyStudyGroupInfo

internal object MyStudyGroupListMapper {
    fun MyStudyGroupListResponse.MyStudyGroupResponse.toDomain() =
        MyStudyGroupInfo(
            id = id,
            leader = leader.toDomain(),
            name = name,
            maxMembers = maxMembers,
            currentMembers = currentMembers,
            description = description,
            profileImg = profileImg,
            isPublic = isPublic,
            targetStudyTime = targetStudyTime,
            category = category.toDomain(),
            createdDate = createdDate,
            tags = tags.map { it.toDomain() },
        )

    fun MyStudyGroupListResponse.MyStudyGroupResponse.LeaderResponse.toDomain() =
        MyStudyGroupInfo.LeaderInfo(
            id = id,
            name = name,
        )

    fun MyStudyGroupListResponse.MyStudyGroupResponse.CategoryResponse.toDomain() =
        MyStudyGroupInfo.CategoryInfo(
            id = id,
            name = name,
        )

    fun MyStudyGroupListResponse.MyStudyGroupResponse.TagResponse.toDomain() =
        MyStudyGroupInfo.TagInfo(
            id = id,
            name = name,
        )
}
