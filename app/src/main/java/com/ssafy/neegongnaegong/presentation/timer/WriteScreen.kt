package com.ssafy.neegongnaegong.presentation.timer

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.neegongnaegong.domain.model.write.Tag
import com.ssafy.neegongnaegong.presentation.timer.component.write.BottomButtons
import com.ssafy.neegongnaegong.presentation.timer.component.write.ContentTextField
import com.ssafy.neegongnaegong.presentation.timer.component.write.DateTimeHeader
import com.ssafy.neegongnaegong.presentation.timer.component.write.TagList
import com.ssafy.neegongnaegong.presentation.timer.component.write.TagSelectDialog
import com.ssafy.neegongnaegong.presentation.timer.component.write.TitleTextField
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest


// 컴포넌트 분리
// 다이얼로그
// 시간 전달
// 화면 이동
// Route에 뷰모델 담는 이유
// 디테일한 색을 다크,화이트로 바꾸는 방식 찾기

@Composable
fun WriteScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: WriteViewModel = hiltViewModel(),
    popBackStack: () -> Unit = {},
) {

    BackHandler { popBackStack() }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    WriteContent(
        modifier = modifier,
        effect = viewModel.effect,
        uiState = uiState.value,
        onCancelClicked = { viewModel.setEvent(WriteContract.Event.OnCancelClicked) },
        onConfirmClicked = { viewModel.setEvent(WriteContract.Event.OnConfirmClicked) },
        onTitleChanged = { viewModel.setEvent(WriteContract.Event.OnTitleChanged(it)) },
        onContentChanged = { viewModel.setEvent(WriteContract.Event.OnContentChanged(it)) },
        onTagPlusClicked = { viewModel.setEvent(WriteContract.Event.OnTagPlusClicked) },
        onTagEraseClicked = { viewModel.setEvent(WriteContract.Event.OnTagEraseClicked(it)) },
        onDialogClosed = { viewModel.setEvent(WriteContract.Event.OnDialogClose) },
        onDialogConfirmed = { viewModel.setEvent(WriteContract.Event.OnDialogConfirmClicked) },
        onSearchQueryChanged = { viewModel.setEvent(WriteContract.Event.OnSearchTextChanged(it)) },
        onTagSelected = { viewModel.setEvent(WriteContract.Event.OnTagSelected(it)) },
        onTagDeselected = { viewModel.setEvent(WriteContract.Event.OnTagDeselected(it)) },
    )

}


@Composable
fun WriteContent(
    modifier: Modifier = Modifier,
    effect: Flow<WriteContract.Effect>,
    uiState: WriteContract.State,
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
                is WriteContract.Effect.NavigateToHome -> {

                }
            }
        }
    }

    WriteScreen(
        modifier = modifier,
        title = uiState.title,
        content = uiState.content,
        startTime = uiState.startTime,
        endTime = uiState.endTime,
        tags = uiState.tags,
        onTitleChanged = {
            onTitleChanged(it)
        },
        onContentChanged = onContentChanged,
        onTagPlusClicked = onTagPlusClicked,
        onTagEraseClicked = onTagEraseClicked,
        onCancelClicked = onCancelClicked,
        onConfirmClicked = onConfirmClicked,
    )


}

// 처음에 태그 선택할때 (ex.CS,알고리즘.. ) user -> 관련스터디 -> 카테고리

@Composable
fun WriteScreen(
    modifier: Modifier = Modifier,
    title: String = "",
    content: String = "",
    startTime: Long = 0,
    endTime: Long = 0,
    tags: List<Tag> = emptyList(),
    onTitleChanged: (String) -> Unit = {},
    onContentChanged: (String) -> Unit = {},
    onTagPlusClicked: () -> Unit = {},
    onTagEraseClicked: (Tag) -> Unit = {},
    onCancelClicked: () -> Unit = {},
    onConfirmClicked: () -> Unit = {},
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 8.dp, end = 8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {

            DateTimeHeader(dateText = "2025년 02월 03일", timeText = "오후 3시 ~ 오후 4시 30분")

            TitleTextField(
                modifier = Modifier.fillMaxWidth(),
                title = title,
                onTitleChanged = onTitleChanged
            )

            ContentTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.5f),
                content = content,
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


@Preview
@Composable
private fun PreviewWriteScreen() {
    NeeGongNaeGongTheme {
        Surface {

        }
    }
}