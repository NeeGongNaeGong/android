package com.ssafy.neegongnaegong.domain.model.preview.studies

import com.ssafy.neegongnaegong.domain.model.studies.Category
import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.domain.model.studies.StudyInfo
import com.ssafy.neegongnaegong.domain.model.studies.StudyMember
import com.ssafy.neegongnaegong.presentation.util.TimeUnit

class StudiesPreviewDataProvider {
    fun getStudies(): List<Studies> =
        listOf(
            Studies(
                id = 6556,
                leader =
                    StudyMember(
                        id = 4158,
                        name = "호빵맨",
                    ),
                currentMembers = 11,
                createdDate = "2025-05-15",
                studyInfo =
                    StudyInfo(
                        name = "호빵이 좋아",
                        maxMembers = 20,
                        description = "호빵입니다",
                        profileImg = null,
                        isPublic = true,
                        targetStudyTime = TimeUnit.HOUR.seconds.toInt() * 20,
                        category =
                            Category(
                                id = 1,
                                name = "식빵",
                            ),
                        tags = listOf(),
                    ),
            ),
            Studies(
                id = 8234,
                leader =
                    StudyMember(
                        id = 3278,
                        name = "새균맨",
                    ),
                currentMembers = 2,
                createdDate = "2025-05-14",
                studyInfo =
                    StudyInfo(
                        name = "세균이 좋아",
                        maxMembers = 15,
                        description = "세균입니다",
                        profileImg = null,
                        isPublic = true,
                        targetStudyTime = TimeUnit.HOUR.seconds.toInt() * 3,
                        category =
                            Category(
                                id = 2,
                                name = "병균",
                            ),
                        tags = listOf(),
                    ),
            ),
            Studies(
                id = 1719,
                leader =
                    StudyMember(
                        id = 9342,
                        name = "카레맨",
                    ),
                currentMembers = 3,
                createdDate = "2025-05-13",
                studyInfo =
                    StudyInfo(
                        name = "카레가 좋아",
                        maxMembers = 30,
                        description = "카레입니다",
                        profileImg = null,
                        isPublic = true,
                        targetStudyTime = TimeUnit.HOUR.seconds.toInt() * 7,
                        category =
                            Category(
                                id = 3,
                                name = "즉석식품",
                            ),
                        tags = listOf(),
                    ),
            ),
        )
}
