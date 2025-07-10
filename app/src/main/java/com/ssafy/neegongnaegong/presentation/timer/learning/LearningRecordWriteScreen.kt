package com.ssafy.neegongnaegong.presentation.timer.learning

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import com.ssafy.neegongnaegong.domain.model.learning.Tag
import com.ssafy.neegongnaegong.domain.model.preview.personal.PersonalPreviewDataProvider
import com.ssafy.neegongnaegong.presentation.component.LoadingDialog
import com.ssafy.neegongnaegong.presentation.component.TagList
import com.ssafy.neegongnaegong.presentation.timer.component.write.BottomButtons
import com.ssafy.neegongnaegong.presentation.timer.component.write.ContentTextField
import com.ssafy.neegongnaegong.presentation.timer.component.write.DateTimeHeader
import com.ssafy.neegongnaegong.presentation.timer.component.write.LearningWriteCancelDialog
import com.ssafy.neegongnaegong.presentation.timer.component.write.TagSelectDialog
import com.ssafy.neegongnaegong.presentation.timer.component.write.TitleTextField
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.toDateString
import com.ssafy.neegongnaegong.presentation.util.toTimeString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LearningRecordWriteRoute(
    modifier: Modifier = Modifier,
    viewModel: LearningRecordWriteViewModel = hiltViewModel(),
    onCloseActivity: () -> Unit,
    learningRecord: LearningRecord,
) {
    LaunchedEffect(Unit) {
        viewModel.setLearningRecord(learningRecord)
    }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    BackHandler { viewModel.setEvent(LearningRecordWriteContract.Event.OnLearningWriteDialogShow) }

    LearningRecordWriteContent(
        modifier = modifier,
        effect = viewModel.effect,
        uiState = uiState.value,
        onCancelClicked = { viewModel.setEvent(LearningRecordWriteContract.Event.OnLearningWriteDialogShow) },
        onConfirmClicked = { viewModel.setEvent(LearningRecordWriteContract.Event.OnConfirmClicked) },
        onTitleChanged = {
            print("확인 $it")
            viewModel.setEvent(LearningRecordWriteContract.Event.OnTitleChanged(it))
        },
        onContentChanged = {
            viewModel.setEvent(
                LearningRecordWriteContract.Event.OnContentChanged(
                    it,
                ),
            )
        },
        onTagPlusClicked = { viewModel.setEvent(LearningRecordWriteContract.Event.OnTagPlusClicked) },
        onTagEraseClicked = {
            viewModel.setEvent(
                LearningRecordWriteContract.Event.OnTagEraseClicked(
                    it,
                ),
            )
        },
        onDialogClosed = { viewModel.setEvent(LearningRecordWriteContract.Event.OnDialogClose) },
        onDialogConfirmed = { viewModel.setEvent(LearningRecordWriteContract.Event.OnDialogConfirmClicked) },
        onSearchQueryChanged = {
            viewModel.setEvent(
                LearningRecordWriteContract.Event.OnSearchTextChanged(
                    it,
                ),
            )
        },
        onTagSelected = { viewModel.setEvent(LearningRecordWriteContract.Event.OnTagSelected(it)) },
        onTagDeselected = { viewModel.setEvent(LearningRecordWriteContract.Event.OnTagDeselected(it)) },
        onLearningWriteCancelDialogClosed = { viewModel.setEvent(LearningRecordWriteContract.Event.OnLearningWriteDialogCancelClicked) },
        onLearningWriteCancelDialogConfirmed = onCloseActivity,
        onCloseActivity = onCloseActivity,
    )
}

@Composable
fun LearningRecordWriteContent(
    modifier: Modifier = Modifier,
    effect: Flow<LearningRecordWriteContract.Effect>,
    uiState: LearningRecordWriteContract.State,
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
    // LearningWriteCancelDialog
    onLearningWriteCancelDialogClosed: () -> Unit,
    onLearningWriteCancelDialogConfirmed: () -> Unit,
    // activity
    onCloseActivity: () -> Unit,
) {
    LaunchedEffect(effect) {
        effect.collectLatest { effect ->
            when (effect) {
                is LearningRecordWriteContract.Effect.NavigateToHome -> {
                    onCloseActivity()
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

    if (uiState.isLoading) LoadingDialog()

    if (uiState.isLearningWriteCancelDialogShow) {
        LearningWriteCancelDialog(
            onCancel = onLearningWriteCancelDialogClosed,
            onConfirm = onLearningWriteCancelDialogConfirmed,
            onDismiss = onLearningWriteCancelDialogClosed,
        )
    }
}

// 처음에 태그 선택할때 (ex.CS,알고리즘.. ) user -> 관련스터디 -> 카테고리
@Composable
fun LearningRecordWriteScreen(
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
    val scroll = rememberScrollState()
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = NeeGongNaeGongTheme.colorScheme.background)
                .imePadding().padding(start = 8.dp, end = 8.dp).verticalScroll(scroll),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
        ) {
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

@NeeGongNaeGongPreviews
@Composable
private fun PreviewWriteScreen() {
    NeeGongNaeGongTheme {
        LearningRecordWriteScreen(
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
