package com.ssafy.neegongnaegong.domain.model.studies.combination

import com.ssafy.neegongnaegong.domain.model.studies.StudiesLatestContents
import com.ssafy.neegongnaegong.domain.model.studies.StudiesLatestContentsReadStatus

data class StudiesContentsWithReadStatus(
    val contents: StudiesLatestContents,
    val readStatus: StudiesLatestContentsReadStatus,
)
