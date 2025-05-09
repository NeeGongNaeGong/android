package com.ssafy.neegongnaegong.presentation.personal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import com.ssafy.neegongnaegong.domain.model.learning.Tag
import com.ssafy.neegongnaegong.domain.model.preview.personal.PersonalPreviewDataProvider
import com.ssafy.neegongnaegong.presentation.component.TagList
import com.ssafy.neegongnaegong.presentation.component.studyrecord.StudyRecordList
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun PersonalByTagScreen(
    modifier: Modifier = Modifier,
    tags: List<Tag>,
    learningRecords: List<LearningRecord>,
    onTagPlusClicked: () -> Unit,
    onTagEraseClicked: (Tag) -> Unit,
    navigateToEditScreen: (Long) -> Unit,
    // Paging
    onLoadMore: () -> Unit,
    hasNext: Boolean,
) {
    Column(modifier = modifier.fillMaxSize()) {
        TagList(
            modifier =
                Modifier
                    .wrapContentSize()
                    .padding(vertical = 16.dp),
            tags = tags,
            onTagPlusClicked = onTagPlusClicked,
            onTagEraseClicked = onTagEraseClicked,
        )

        StudyRecordList(
            modifier = Modifier.weight(1f),
            learningRecords = learningRecords,
            onClick = navigateToEditScreen,
            onLoadMore = onLoadMore,
            hasNext = hasNext,
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
fun PersonalByTagScreenPreview() {
    NeeGongNaeGongTheme {
        PersonalByTagScreen(
            tags = PersonalPreviewDataProvider().getTags(),
            learningRecords = PersonalPreviewDataProvider().getStudyRecords(),
            onTagPlusClicked = {},
            onTagEraseClicked = {},
            navigateToEditScreen = {},
            onLoadMore = {},
            hasNext = false,
        )
    }
}
