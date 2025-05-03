package com.ssafy.neegongnaegong.presentation.personal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.domain.model.personal.StudyRecord
import com.ssafy.neegongnaegong.domain.model.preview.personal.PersonalPreviewDataProvider
import com.ssafy.neegongnaegong.domain.model.learning.Tag
import com.ssafy.neegongnaegong.presentation.component.TagList
import com.ssafy.neegongnaegong.presentation.component.studyrecord.StudyRecordList
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun PersonalByTagScreen(
    modifier: Modifier = Modifier,
    tags: List<Tag>,
    studyRecords: List<StudyRecord>,
    onTagPlusClicked: () -> Unit,
    onTagEraseClicked: (Tag) -> Unit,
    navigateToEditScreen: (Long) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        TagList(
            modifier = Modifier
                .wrapContentSize()
                .padding(vertical = 16.dp),
            tags = tags,
            onTagPlusClicked = onTagPlusClicked,
            onTagEraseClicked = onTagEraseClicked
        )

        StudyRecordList(
            modifier = Modifier.weight(1f),
            studyRecords = studyRecords,
            onClick = navigateToEditScreen
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PersonalByTagScreenPreview() {
    NeeGongNaeGongTheme {
        PersonalByTagScreen(
            tags = PersonalPreviewDataProvider().getTags(),
            studyRecords = PersonalPreviewDataProvider().getStudyRecords(),
            onTagPlusClicked = {},
            onTagEraseClicked = {},
            navigateToEditScreen = {}
        )
    }
}
