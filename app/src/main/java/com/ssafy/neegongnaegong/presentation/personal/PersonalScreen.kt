package com.ssafy.neegongnaegong.presentation.personal

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import com.ssafy.neegongnaegong.domain.model.learning.Tag
import com.ssafy.neegongnaegong.domain.model.preview.personal.PersonalPreviewDataProvider
import com.ssafy.neegongnaegong.presentation.component.LoadingDialog
import com.ssafy.neegongnaegong.presentation.component.picker.date.rememberDatePickerState
import com.ssafy.neegongnaegong.presentation.personal.component.DeleteSelectedLearningRecordsDialog
import com.ssafy.neegongnaegong.presentation.personal.component.PersonalTopAppBar
import com.ssafy.neegongnaegong.presentation.timer.component.write.TagSelectDialog
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun PersonalRoute(
    modifier: Modifier = Modifier,
    viewModel: PersonalViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    navigateToEditScreen: (Long) -> Unit,
    navController: NavController,
) {
    BackHandler { popBackStack() }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    val currentBackStackEntry = navController.currentBackStackEntryAsState().value

    LaunchedEffect(Unit) {
        viewModel.setEvent(PersonalContract.Event.OnRecordRefresh)
    }

    LaunchedEffect(currentBackStackEntry) {
        val shouldRefresh =
            currentBackStackEntry
                ?.savedStateHandle
                ?.get<Boolean>("refreshNeeded") == true

        if (shouldRefresh) {
            viewModel.setEvent(PersonalContract.Event.OnRecordRefresh)
            currentBackStackEntry?.let {
                currentBackStackEntry.savedStateHandle["refreshNeeded"] = false
            }
        }
    }

    PersonalContent(
        modifier = modifier,
        effect = viewModel.effect,
        uiState = uiState.value,
        onTagScreenSelected = { viewModel.setEvent(PersonalContract.Event.OnTagScreenSelected) },
        onDateScreenSelected = { viewModel.setEvent(PersonalContract.Event.OnDateScreenSelected) },
        onTagPlusClicked = { viewModel.setEvent(PersonalContract.Event.OnTagPlusClicked) },
        onTagEraseClicked = { viewModel.setEvent(PersonalContract.Event.OnTagEraseClicked(it)) },
        onDialogClosed = { viewModel.setEvent(PersonalContract.Event.OnDialogClose) },
        onDialogConfirmed = { viewModel.setEvent(PersonalContract.Event.OnDialogConfirmClicked) },
        onSearchQueryChanged = { viewModel.setEvent(PersonalContract.Event.OnSearchTextChanged(it)) },
        onTagSelected = { viewModel.setEvent(PersonalContract.Event.OnTagSelected(it)) },
        onTagDeselected = { viewModel.setEvent(PersonalContract.Event.OnTagDeselected(it)) },
        onDateSelected = { viewModel.setEvent(PersonalContract.Event.OnDateSelected(it)) },
        navigateToEditScreen = navigateToEditScreen,
        onLoadMore = { viewModel.setEvent(PersonalContract.Event.OnRecordLoadMore) },
        onDeleteSelect = { viewModel.setEvent(PersonalContract.Event.OnDeleteSelect(it)) },
        onSelectModeChange = { viewModel.setEvent(PersonalContract.Event.OnSelectModeChange) },
        onSelectCancel = { viewModel.setEvent(PersonalContract.Event.OnSelectCancel) },
        onSelectDelete = { viewModel.setEvent(PersonalContract.Event.OnSelectDelete) },
        onSelectDialogConfirm = { viewModel.setEvent(PersonalContract.Event.OnSelectDialogConfirm) },
        onSelectDialogCancel = { viewModel.setEvent(PersonalContract.Event.OnSelectDialogCancel) },
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
    // paging
    onLoadMore: () -> Unit,
    // delete selected
    onDeleteSelect: (Long) -> Unit,
    onSelectModeChange: () -> Unit,
    onSelectCancel: () -> Unit,
    onSelectDelete: () -> Unit,
    onSelectDialogConfirm: () -> Unit,
    onSelectDialogCancel: () -> Unit,
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

    if (uiState.isDeleteDialogShow) {
        DeleteSelectedLearningRecordsDialog(
            selectedCount = uiState.deleteSelectedRecordIds.size,
            onConfirm = onSelectDialogConfirm,
            onCancel = onSelectDialogCancel,
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
        // pager
        onTagScreenSelected = onTagScreenSelected,
        onDateScreenSelected = onDateScreenSelected,
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
        navigateToEditScreen = navigateToEditScreen,
        // paging
        onLoadMore = onLoadMore,
        hasTagDataNext = uiState.hasTagDataNext,
        hasDateDataNext = uiState.hasDateDataNext,
        studiedDates = uiState.learningDates,
        // delete selected
        isSelectedMode = uiState.isSelectedMode,
        deleteSelectedRecordIds = uiState.deleteSelectedRecordIds,
        onDeleteSelect = onDeleteSelect,
        onSelectModeChange = onSelectModeChange,
        onSelectCancel = onSelectCancel,
        onSelectDelete = onSelectDelete,
    )

    if (uiState.isLoading) {
        LoadingDialog()
    }
}

@Composable
fun PersonalScreen(
    modifier: Modifier = Modifier,
    // pager
    onTagScreenSelected: () -> Unit,
    onDateScreenSelected: () -> Unit,
    tags: List<Tag>,
    selectedRecordsByTag: List<LearningRecord>,
    onTagPlusClicked: () -> Unit,
    onTagEraseClicked: (Tag) -> Unit,
    onDateSelected: (String) -> Unit,
    selectedRecordsByDate: List<LearningRecord>,
    selectedDate: String,
    navigateToEditScreen: (Long) -> Unit,
    // Paging3
    onLoadMore: () -> Unit,
    hasTagDataNext: Boolean,
    hasDateDataNext: Boolean,
    // study point
    studiedDates: ImmutableSet<LocalDate>,
    // delete selected
    isSelectedMode: Boolean = false,
    deleteSelectedRecordIds: Set<Long> = setOf(),
    onDeleteSelect: (Long) -> Unit = {},
    onSelectModeChange: () -> Unit = {},
    onSelectCancel: () -> Unit = {},
    onSelectDelete: () -> Unit = {},
) {
    val tabTitles = listOf("태그별", "날짜별")
    val pagerState = rememberPagerState(pageCount = { tabTitles.size })
    val coroutineScope = rememberCoroutineScope()
    val datePickerState = rememberDatePickerState()

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 8.dp),
    ) {
        PersonalTopAppBar(
            isSelectedMode = isSelectedMode,
            deleteSelectedRecordIds = deleteSelectedRecordIds,
            onModeChange = { onSelectModeChange() },
            onCancel = { onSelectCancel() },
            onDelete = { onSelectDelete() },
        )

        TabRow(
            modifier = Modifier.background(NeeGongNaeGongTheme.colorScheme.background),
            selectedTabIndex = pagerState.currentPage,
            containerColor = NeeGongNaeGongTheme.colorScheme.background,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    height = 3.dp,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
            },
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
                            style =
                                MaterialTheme.typography.labelLarge.copy(
                                    fontSize = 18.sp,
                                    color = if (isSelected) NeeGongNaeGongTheme.colorScheme.primaryText else Color.Gray,
                                ),
                        )
                    },
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        var previousPage by rememberSaveable { mutableIntStateOf(-1) }

        LaunchedEffect(pagerState.currentPage) {
            if (previousPage != pagerState.currentPage) {
                previousPage = pagerState.currentPage
                when (pagerState.currentPage) {
                    0 -> onTagScreenSelected()
                    1 -> onDateScreenSelected()
                }
            }
        }

        HorizontalPager(
            pageSize = PageSize.Fill,
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            when (page) {
                0 -> {
                    PersonalByTagScreen(
                        modifier = modifier.padding(horizontal = 8.dp),
                        tags = tags,
                        learningRecords = selectedRecordsByTag,
                        onTagPlusClicked = onTagPlusClicked,
                        onTagEraseClicked = onTagEraseClicked,
                        navigateToEditScreen = navigateToEditScreen,
                        onLoadMore = onLoadMore,
                        hasTagDataNext = hasTagDataNext,
                        isSelectedMode = isSelectedMode,
                        deleteSelectedRecordIds = deleteSelectedRecordIds,
                        onDeleteSelect = onDeleteSelect,
                    )
                }

                1 -> {
                    PersonalByDateScreen(
                        modifier = modifier.padding(horizontal = 8.dp),
                        datePickerState = datePickerState,
                        onDateSelected = onDateSelected,
                        selectedRecordsByDate = selectedRecordsByDate,
                        selectedDate = selectedDate,
                        navigateToEditScreen = navigateToEditScreen,
                        onLoadMore = onLoadMore,
                        hasDateDataNext = hasDateDataNext,
                        studiedDates = studiedDates,
                        isSelectedMode = isSelectedMode,
                        deleteSelectedRecordIds = deleteSelectedRecordIds,
                        onDeleteSelect = onDeleteSelect,
                    )
                }
            }
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
fun PersonalScreenPreview() {
    NeeGongNaeGongTheme {
        PersonalScreen(
            tags = PersonalPreviewDataProvider().getTags(),
            onTagScreenSelected = {},
            onDateScreenSelected = {},
            onTagPlusClicked = {},
            onTagEraseClicked = {},
            onDateSelected = {},
            selectedRecordsByDate = PersonalPreviewDataProvider().getStudyRecords(),
            navigateToEditScreen = {},
            selectedDate = "2024-01-01",
            selectedRecordsByTag = PersonalPreviewDataProvider().getStudyRecords(),
            onLoadMore = {},
            hasTagDataNext = false,
            hasDateDataNext = false,
            studiedDates = persistentSetOf(),
        )
    }
}
