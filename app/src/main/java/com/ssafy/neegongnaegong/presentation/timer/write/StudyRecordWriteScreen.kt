package com.ssafy.neegongnaegong.presentation.timer.write

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.neegongnaegong.domain.model.personal.StudyRecord
import com.ssafy.neegongnaegong.domain.model.write.Tag
import com.ssafy.neegongnaegong.presentation.component.TagList
import com.ssafy.neegongnaegong.presentation.timer.component.write.BottomButtons
import com.ssafy.neegongnaegong.presentation.timer.component.write.ContentTextField
import com.ssafy.neegongnaegong.presentation.timer.component.write.DateTimeHeader
import com.ssafy.neegongnaegong.presentation.timer.component.write.TagSelectDialog
import com.ssafy.neegongnaegong.presentation.timer.component.write.TitleTextField
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.toDateString
import com.ssafy.neegongnaegong.presentation.util.toTimeString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun StudyRecordWriteRoute(
    modifier: Modifier = Modifier,
    viewModel: StudyRecordWriteViewModel = hiltViewModel(),
    popBackStack: () -> Unit = {},
) {

    BackHandler { popBackStack() }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    StudyRecordWriteContent(
        modifier = modifier,
        effect = viewModel.effect,
        uiState = uiState.value,
        onCancelClicked = { viewModel.setEvent(StudyRecordWriteContract.Event.OnCancelClicked) },
        onConfirmClicked = { viewModel.setEvent(StudyRecordWriteContract.Event.OnConfirmClicked) },
        onTitleChanged = { viewModel.setEvent(StudyRecordWriteContract.Event.OnTitleChanged(it)) },
        onContentChanged = { viewModel.setEvent(StudyRecordWriteContract.Event.OnContentChanged(it)) },
        onTagPlusClicked = { viewModel.setEvent(StudyRecordWriteContract.Event.OnTagPlusClicked) },
        onTagEraseClicked = { viewModel.setEvent(StudyRecordWriteContract.Event.OnTagEraseClicked(it)) },
        onDialogClosed = { viewModel.setEvent(StudyRecordWriteContract.Event.OnDialogClose) },
        onDialogConfirmed = { viewModel.setEvent(StudyRecordWriteContract.Event.OnDialogConfirmClicked) },
        onSearchQueryChanged = { viewModel.setEvent(StudyRecordWriteContract.Event.OnSearchTextChanged(it)) },
        onTagSelected = { viewModel.setEvent(StudyRecordWriteContract.Event.OnTagSelected(it)) },
        onTagDeselected = { viewModel.setEvent(StudyRecordWriteContract.Event.OnTagDeselected(it)) },
    )

}


@Composable
fun StudyRecordWriteContent(
    modifier: Modifier = Modifier,
    effect: Flow<StudyRecordWriteContract.Effect>,
    uiState: StudyRecordWriteContract.State,
    onCancelClicked: () -> Unit,
    onConfirmClicked: () -> Unit,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onTagPlusClicked: () -> Unit,
    onTagEraseClicked: (Tag) -> Unit,
    onDialogClosed: () -> Unit,
    onDialogConfirmed: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onTagSelected: (Tag) -> Unit,
    onTagDeselected: (Tag) -> Unit,
) {
    val context = LocalContext.current

    if (uiState.isDialogShow) {
        TagSelectDialog(
            selectedTags = uiState.selectedTags,
            unSelectedTags = uiState.unSelectedTags,
            onCancel = onDialogClosed,
            onConfirm = onDialogConfirmed,
            onSearchQueryChanged = onSearchQueryChanged,
            onTagSelected = onTagSelected,
            onTagDeselected = onTagDeselected,
        )
    }

    LaunchedEffect(effect) {
        effect.collectLatest { effect ->
            when (effect) {
                is StudyRecordWriteContract.Effect.NavigateToHome -> {

                }

                is StudyRecordWriteContract.Effect.ShowErrorToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is StudyRecordWriteContract.Effect.ShowSuccessToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is StudyRecordWriteContract.Effect.ShowTagLimitExceededToast -> {
                    Toast.makeText(context, "태그는 최대 5개만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    StudyRecordWriteScreen(
        modifier = modifier,
        tags = uiState.tags,
        studyRecord = uiState.studyRecord,
        onTitleChanged = onTitleChanged,
        onContentChanged = onContentChanged,
        onTagPlusClicked = onTagPlusClicked,
        onTagEraseClicked = onTagEraseClicked,
        onCancelClicked = onCancelClicked,
        onConfirmClicked = onConfirmClicked,
    )


}

// 처음에 태그 선택할때 (ex.CS,알고리즘.. ) user -> 관련스터디 -> 카테고리

@Composable
fun StudyRecordWriteScreen(
    modifier: Modifier = Modifier,
    studyRecord: StudyRecord,
    tags: List<Tag>,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onTagPlusClicked: () -> Unit,
    onTagEraseClicked: (Tag) -> Unit,
    onCancelClicked: () -> Unit,
    onConfirmClicked: () -> Unit,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 8.dp, end = 8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {

            DateTimeHeader(
                dateText = studyRecord.startTime.toDateString(),
                timeText = "${studyRecord.startTime.toTimeString()} ~ ${studyRecord.endTime.toTimeString()}"
            )

            TitleTextField(
                modifier = Modifier.fillMaxWidth(),
                title = studyRecord.title,
                onTitleChanged = onTitleChanged
            )

            ContentTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.5f),
                content = studyRecord.content,
                onContentChanged = onContentChanged,
            )

            TagList(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                tags = tags,
                onTagPlusClicked = onTagPlusClicked,
                onTagEraseClicked = onTagEraseClicked
            )
        }

        BottomButtons(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onCancelClicked = onCancelClicked,
            onConfirmClicked = onConfirmClicked
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewWriteScreen() {
    NeeGongNaeGongTheme {
        Surface {
            StudyRecordWriteScreen(
                studyRecord = StudyRecord(),
                tags = listOf(
                    Tag(koName = "공부", enName = "Study"),
                    Tag(koName = "운동", enName = "Exercise")
                ),
                onTitleChanged = {},
                onContentChanged = {},
                onTagPlusClicked = {},
                onTagEraseClicked = {},
                onCancelClicked = {},
                onConfirmClicked = {}
            )
        }
    }
}
