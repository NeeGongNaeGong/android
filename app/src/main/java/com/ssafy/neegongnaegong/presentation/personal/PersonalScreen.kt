package com.ssafy.neegongnaegong.presentation.personal

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.neegongnaegong.domain.model.personal.StudyRecord
import com.ssafy.neegongnaegong.domain.model.preview.personal.PersonalPreviewDataProvider
import com.ssafy.neegongnaegong.domain.model.write.Tag
import com.ssafy.neegongnaegong.presentation.component.picker.date.rememberDatePickerState
import com.ssafy.neegongnaegong.presentation.timer.component.write.TagSelectDialog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Composable
fun PersonalRoute(
    modifier: Modifier = Modifier,
    viewModel: PersonalViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    navigateToEditScreen: (Long) -> Unit
) {

    BackHandler { popBackStack() }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    PersonalContent(
        modifier = modifier,
        effect = viewModel.effect,
        uiState = uiState.value,
        onTagScreenSelected = { viewModel.setEvent(PersonalContract.Event.OnTagScreenSelected) },
        onDateScreenSelected = { viewModel.setEvent(PersonalContract.Event.OnDateScreenSelected) },
        studyRecords = uiState.value.studyRecords,
        onTagPlusClicked = { viewModel.setEvent(PersonalContract.Event.OnTagPlusClicked) },
        onTagEraseClicked = { viewModel.setEvent(PersonalContract.Event.OnTagEraseClicked(it)) },
        onDialogClosed = { viewModel.setEvent(PersonalContract.Event.OnDialogClose) },
        onDialogConfirmed = { viewModel.setEvent(PersonalContract.Event.OnDialogConfirmClicked) },
        onSearchQueryChanged = { viewModel.setEvent(PersonalContract.Event.OnSearchTextChanged(it)) },
        onTagSelected = { viewModel.setEvent(PersonalContract.Event.OnTagSelected(it)) },
        onTagDeselected = { viewModel.setEvent(PersonalContract.Event.OnTagDeselected(it)) },
        onDateSelected = { viewModel.setEvent(PersonalContract.Event.OnDateSelected(it)) },
        navigateToEditScreen = navigateToEditScreen
    )

}

@Composable
fun PersonalContent(
    modifier: Modifier = Modifier,
    effect: Flow<PersonalContract.Effect>,
    uiState: PersonalContract.State,
    // dropdown
    onTagScreenSelected: () -> Unit,
    onDateScreenSelected: () -> Unit,
    // study
    studyRecords: List<StudyRecord>,
    // tag
    onTagPlusClicked: () -> Unit,
    onTagEraseClicked: (Tag) -> Unit,
    onDialogClosed: () -> Unit,
    onDialogConfirmed: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onTagSelected: (Tag) -> Unit,
    onTagDeselected: (Tag) -> Unit,
    onDateSelected: (String) -> Unit,
    navigateToEditScreen: (Long) -> Unit,
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
                is PersonalContract.Effect.ShowErrorToast -> {}

                is PersonalContract.Effect.ShowTagLimitExceededToast -> {
                    Toast.makeText(context, "태그는 최대 5개만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    PersonalScreen(
        modifier = modifier,
        studyRecords = uiState.studyRecords,
        // tag
        tags = uiState.tags,
        onTagPlusClicked = onTagPlusClicked,
        onTagEraseClicked = onTagEraseClicked,
        selectedRecordsByTag = uiState.selectedRecordsByTag,
        // calendar
        onDateSelected = onDateSelected,
        selectedRecordsByDate = uiState.selectedRecordsByDate,
        selectedDate = uiState.selectedDate,
        // navigate
        navigateToEditScreen = navigateToEditScreen
    )
}

@Composable
fun PersonalScreen(
    modifier: Modifier = Modifier,
    studyRecords: List<StudyRecord>,
    tags: List<Tag>,
    selectedRecordsByTag: List<StudyRecord>,
    onTagPlusClicked: () -> Unit,
    onTagEraseClicked: (Tag) -> Unit,
    onDateSelected: (String) -> Unit,
    selectedRecordsByDate: List<StudyRecord>,
    selectedDate: String,
    navigateToEditScreen: (Long) -> Unit
) {
    val tabTitles = listOf("태그별", "날짜별")
    val pagerState = rememberPagerState(pageCount = { tabTitles.size })
    val coroutineScope = rememberCoroutineScope()
    val datePickerState = rememberDatePickerState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color(0xFFFAFAFA)
        ) {
            tabTitles.forEachIndexed { index, title ->
                val isSelected = pagerState.currentPage == index
                Tab(
                    selected = isSelected,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 18.sp,
                                color = if (isSelected) Color.Black else Color.Gray
                            )
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalPager(
            pageSize = PageSize.Fill,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> PersonalByTagScreen(
                    tags = tags,
                    studyRecords = if (tags.isEmpty()) studyRecords else selectedRecordsByTag,
                    onTagPlusClicked = onTagPlusClicked,
                    onTagEraseClicked = onTagEraseClicked,
                    navigateToEditScreen = navigateToEditScreen
                )

                1 -> PersonalByDateScreen(
                    datePickerState = datePickerState,
                    onDateSelected = onDateSelected,
                    selectedRecordsByDate = selectedRecordsByDate,
                    selectedDate = selectedDate,
                    navigateToEditScreen = navigateToEditScreen
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PersonalScreenPreview() {
    PersonalScreen(
        studyRecords = PersonalPreviewDataProvider().getStudyRecords(),
        tags = PersonalPreviewDataProvider().getTags(),
        onTagPlusClicked = {},
        onTagEraseClicked = {},
        onDateSelected = {},
        selectedRecordsByDate = PersonalPreviewDataProvider().getStudyRecords(),
        navigateToEditScreen = {},
        selectedDate = "2024-01-01",
        selectedRecordsByTag = PersonalPreviewDataProvider().getStudyRecords()
    )
}

