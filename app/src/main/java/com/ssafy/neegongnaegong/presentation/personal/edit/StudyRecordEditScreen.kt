package com.ssafy.neegongnaegong.presentation.personal.edit

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
import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import com.ssafy.neegongnaegong.domain.model.learning.Tag
import com.ssafy.neegongnaegong.domain.model.preview.personal.PersonalPreviewDataProvider
import com.ssafy.neegongnaegong.presentation.component.TagList
import com.ssafy.neegongnaegong.presentation.timer.component.write.BottomButtons
import com.ssafy.neegongnaegong.presentation.timer.component.write.ContentTextField
import com.ssafy.neegongnaegong.presentation.timer.component.write.DateTimeHeader
import com.ssafy.neegongnaegong.presentation.timer.component.write.TagSelectDialog
import com.ssafy.neegongnaegong.presentation.timer.component.write.TitleTextField
import com.ssafy.neegongnaegong.presentation.timer.learning.LearningRecordWriteScreen
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.toDateString
import com.ssafy.neegongnaegong.presentation.util.toTimeString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun StudyRecordEditRoute(
    modifier: Modifier = Modifier,
    viewModel: StudyRecordEditViewModel = hiltViewModel(),
    studyRecordId: Long,
    popBackStack: () -> Unit,
) {
    BackHandler { popBackStack() }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.loadStudyRecord(studyRecordId)

    StudyRecordEditContent(
        modifier = modifier,
        effect = viewModel.effect,
        uiState = uiState.value,
        onCancelClicked = { viewModel.setEvent(StudyRecordEditContract.Event.OnCancelClicked) },
        onConfirmClicked = { viewModel.setEvent(StudyRecordEditContract.Event.OnConfirmClicked) },
        onTitleChanged = { viewModel.setEvent(StudyRecordEditContract.Event.OnTitleChanged(it)) },
        onContentChanged = { viewModel.setEvent(StudyRecordEditContract.Event.OnContentChanged(it)) },
        onTagPlusClicked = { viewModel.setEvent(StudyRecordEditContract.Event.OnTagPlusClicked) },
        onTagEraseClicked = { viewModel.setEvent(StudyRecordEditContract.Event.OnTagEraseClicked(it)) },
        onDialogClosed = { viewModel.setEvent(StudyRecordEditContract.Event.OnDialogClose) },
        onDialogConfirmed = { viewModel.setEvent(StudyRecordEditContract.Event.OnDialogConfirmClicked) },
        onSearchQueryChanged = {
            viewModel.setEvent(
                StudyRecordEditContract.Event.OnSearchTextChanged(
                    it,
                ),
            )
        },
        onTagSelected = { viewModel.setEvent(StudyRecordEditContract.Event.OnTagSelected(it)) },
        onTagDeselected = { viewModel.setEvent(StudyRecordEditContract.Event.OnTagDeselected(it)) },
    )
}

@Composable
fun StudyRecordEditContent(
    modifier: Modifier = Modifier,
    effect: Flow<StudyRecordEditContract.Effect>,
    uiState: StudyRecordEditContract.State,
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
                is StudyRecordEditContract.Effect.NavigateToHome -> {
                }

                is StudyRecordEditContract.Effect.ShowErrorToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is StudyRecordEditContract.Effect.ShowSuccessToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is StudyRecordEditContract.Effect.ShowTagLimitExceededToast -> {
                    Toast.makeText(context, "태그는 최대 5개만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LearningRecordWriteScreen(
        modifier = modifier,
        tags = uiState.tags,
        learningRecord = uiState.learningRecord,
        onTitleChanged = onTitleChanged,
        onContentChanged = onContentChanged,
        onTagPlusClicked = onTagPlusClicked,
        onTagEraseClicked = onTagEraseClicked,
        onCancelClicked = onCancelClicked,
        onConfirmClicked = onConfirmClicked,
    )
}

@Composable
fun StudyRecordEditScreen(
    modifier: Modifier = Modifier,
    learningRecord: LearningRecord,
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
        modifier =
            modifier
                .fillMaxSize()
                .padding(top = 16.dp, start = 8.dp, end = 8.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            DateTimeHeader(
                dateText = learningRecord.startAt.toDateString(),
                timeText = "${learningRecord.startAt.toTimeString()} ~ ${learningRecord.endAt.toTimeString()}",
            )

            TitleTextField(
                modifier = Modifier.fillMaxWidth(),
                title = learningRecord.title,
                onTitleChanged = onTitleChanged,
            )

            ContentTextField(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(screenHeight * 0.5f),
                content = learningRecord.content,
                onContentChanged = onContentChanged,
            )

            TagList(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                tags = tags,
                onTagPlusClicked = onTagPlusClicked,
                onTagEraseClicked = onTagEraseClicked,
            )
        }

        BottomButtons(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(50.dp),
            onCancelClicked = onCancelClicked,
            onConfirmClicked = onConfirmClicked,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewWriteScreen() {
    NeeGongNaeGongTheme {
        Surface {
            StudyRecordEditScreen(
                learningRecord = LearningRecord.default(),
                tags = PersonalPreviewDataProvider().getTags(),
                onTitleChanged = {},
                onContentChanged = {},
                onTagPlusClicked = {},
                onTagEraseClicked = {},
                onCancelClicked = {},
                onConfirmClicked = {},
            )
        }
    }
}
