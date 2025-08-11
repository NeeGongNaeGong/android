package com.ssafy.neegongnaegong.presentation.group.find

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.domain.model.preview.studies.StudiesPreviewDataProvider
import com.ssafy.neegongnaegong.domain.model.studies.Category
import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.presentation.component.TopAppBar
import com.ssafy.neegongnaegong.presentation.component.TopAppBarNavigationType
import com.ssafy.neegongnaegong.presentation.group.component.StudiesWindow
import com.ssafy.neegongnaegong.presentation.group.component.dialog.StudiesInfoDialog
import com.ssafy.neegongnaegong.presentation.group.find.component.CategorySelectDialog
import com.ssafy.neegongnaegong.presentation.group.find.component.SearchTextField
import com.ssafy.neegongnaegong.presentation.group.find.component.StudiesSortKebabMenu
import com.ssafy.neegongnaegong.presentation.group.find.component.StudiesSortType
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.noRippleClickable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun StudiesFindRoute(
    modifier: Modifier = Modifier,
    viewModel: StudiesFindViewModel = hiltViewModel(),
    navigateToStudiesManagement: () -> Unit,
    popBackStack: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.setEvent(StudiesFindContract.Event.OnLoad)
    }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    StudiesFindContent(
        modifier = modifier,
        uiState = uiState,
        effect = viewModel.effect,
        onLoadStudies = { viewModel.setEvent(StudiesFindContract.Event.OnLoad) },
        navigateToStudiesManagement = navigateToStudiesManagement,
        onSelectedStudies = { studies ->
            viewModel.setEvent(StudiesFindContract.Event.OnSelectedStudies(studies))
        },
        onSearchKeywordChanged = {
            viewModel.setEvent(StudiesFindContract.Event.OnTypingSearch(it))
        },
        onSearch = {
            viewModel.setEvent(StudiesFindContract.Event.OnSearch)
        },
        onSelectedSortType = {
            viewModel.setEvent(StudiesFindContract.Event.OnSelectedFilterType(it))
        },
        onStudiesInfoDialogShow = {
            viewModel.setEvent(StudiesFindContract.Event.OnStudiesInfoDialogShow)
        },
        onStudiesInfoDialogConfirm = {
            viewModel.setEvent(StudiesFindContract.Event.OnStudiesInfoDialogConfirm(it))
        },
        onStudiesInfoDialogCancel = {
            viewModel.setEvent(StudiesFindContract.Event.OnStudiesInfoDialogCancel)
        },
        onStudiesInfoDialogDismiss = {
            viewModel.setEvent(StudiesFindContract.Event.OnStudiesInfoDialogDismiss)
        },
        onCategoryFilterDialogShow = {
            viewModel.setEvent(StudiesFindContract.Event.OnCategoryFilterDialogShow)
        },
        onCategoryFilterDialogConfirm = {
            viewModel.setEvent(StudiesFindContract.Event.OnCategoryFilterDialogConfirm(it))
        },
        onCategoryFilterDialogCancel = {
            viewModel.setEvent(StudiesFindContract.Event.OnCategoryFilterDialogCancel)
        },
        popBackStack = popBackStack,
    )
}

@Composable
private fun StudiesFindContent(
    modifier: Modifier = Modifier,
    uiState: StudiesFindContract.State,
    effect: Flow<StudiesFindContract.Effect>,
    onLoadStudies: () -> Unit,
    navigateToStudiesManagement: () -> Unit,
    onSelectedStudies: (Studies) -> Unit,
    onSearchKeywordChanged: (String) -> Unit,
    onSearch: () -> Unit,
    onSelectedSortType: (StudiesSortType) -> Unit,
    onStudiesInfoDialogShow: () -> Unit,
    onStudiesInfoDialogConfirm: (Long) -> Unit,
    onStudiesInfoDialogCancel: () -> Unit,
    onStudiesInfoDialogDismiss: () -> Unit,
    onCategoryFilterDialogShow: () -> Unit,
    onCategoryFilterDialogConfirm: (Set<Category>) -> Unit,
    onCategoryFilterDialogCancel: () -> Unit,
    popBackStack: () -> Unit,
) {
    if (uiState.isStudiesInfoDialogShow) {
        if (uiState.selectedStudies == null) return
        StudiesInfoDialog(
            modifier = modifier,
            studies = uiState.selectedStudies,
            onConfirm = onStudiesInfoDialogConfirm,
            onCancel = onStudiesInfoDialogCancel,
            onDismiss = onStudiesInfoDialogDismiss,
        )
    }

    if (uiState.isCategoryFilterDialogShow) {
        CategorySelectDialog(
            modifier = modifier,
            categories = uiState.categories,
            initialSelectedCategories = uiState.selectedCategoryFilter,
            onConfirm = onCategoryFilterDialogConfirm,
            onCancel = onCategoryFilterDialogCancel,
        )
    }

    LaunchedEffect(effect) {
        effect.collectLatest { effect ->
            // TODO : effect 처리
            when (effect) {
                is StudiesFindContract.Effect.ShowStudies -> {
                }

                is StudiesFindContract.Effect.NavigateToGroupDetail -> {
                }
            }
        }
    }

    StudiesFindScreen(
        modifier = modifier,
        studiesList = uiState.studiesList,
        hasNext = uiState.hasNext,
        searchKeyword = uiState.searchKeyword,
        selectedSortType = uiState.selectedSortType,
        filterCount = uiState.selectedCategoryFilter.size,
        onLoadStudies = onLoadStudies,
        onSelectedStudies = onSelectedStudies,
        onSearchKeywordChanged = onSearchKeywordChanged,
        onSearch = onSearch,
        onSelectedSortType = onSelectedSortType,
        onStudiesInfoDialogShow = onStudiesInfoDialogShow,
        onCategoryFilterDialogShow = onCategoryFilterDialogShow,
        navigateToStudiesManagement = navigateToStudiesManagement,
        popBackStack = popBackStack,
    )
}

@Composable
private fun StudiesFindScreen(
    modifier: Modifier = Modifier,
    studiesList: List<Studies>,
    hasNext: Boolean,
    searchKeyword: String,
    selectedSortType: StudiesSortType,
    filterCount: Int,
    onLoadStudies: () -> Unit,
    onSelectedStudies: (Studies) -> Unit,
    onSearchKeywordChanged: (String) -> Unit,
    onSearch: () -> Unit,
    onSelectedSortType: (StudiesSortType) -> Unit,
    onStudiesInfoDialogShow: () -> Unit,
    onCategoryFilterDialogShow: () -> Unit,
    navigateToStudiesManagement: () -> Unit,
    popBackStack: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Log.d("임시", "StudiesFindScreen: $navigateToStudiesManagement")
        TopAppBar(
            title = {
                SearchTextField(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 48.dp)
                            .padding(end = 80.dp),
                    content = searchKeyword,
                    onContentChanged = onSearchKeywordChanged,
                    onSearch = onSearch,
                )
            },
            navigationType = TopAppBarNavigationType.Back,
            onNavigationClick = popBackStack,
            actionButtons = {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Box(modifier = Modifier) {
                        IconButton(
                            modifier = Modifier.size(24.dp),
                            onClick = onCategoryFilterDialogShow,
                        ) {
                            Icon(
                                modifier = Modifier,
                                // 클릭 영역을 더 크게 만들기 위한 패딩
                                painter = painterResource(R.drawable.ic_filter),
                                tint = NeeGongNaeGongTheme.colorScheme.primaryText,
                                contentDescription = "스터디 필터",
                            )
                        }
                        if (filterCount == 0) return@Box
                        Box(
                            modifier =
                                Modifier
                                    .align(Alignment.TopEnd)
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(NeeGongNaeGongTheme.colorScheme.peach),
                            contentAlignment = Alignment.Center,
                        ) {
                            val fontSizeSp =
                                with(LocalDensity.current) {
                                    10.dp.toSp()
                                }
                            Text(
                                text = if (filterCount < 10) "$filterCount" else "9+",
                                color = NeeGongNaeGongTheme.colorScheme.primaryText,
                                fontSize = fontSizeSp,
                                style = NeeGongNaeGongTheme.typography.labelSmall,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                    StudiesSortKebabMenu(
                        modifier = Modifier,
                        selectedFilter = selectedSortType,
                        onFilterSelected = onSelectedSortType,
                    )
                }
            },
        )

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                itemsIndexed(studiesList) { index, studies ->
                    StudiesWindow(
                        modifier =
                            Modifier
                                .padding(horizontal = 12.dp)
                                .padding(bottom = 8.dp)
                                .noRippleClickable {
                                    if (studies.studyInfo.isPublic.not()) return@noRippleClickable
                                    onSelectedStudies(studies)
                                    onStudiesInfoDialogShow()
                                },
                        category = studies.studyInfo.category?.name ?: "없음",
                        isPublic = studies.studyInfo.isPublic,
                        name = studies.studyInfo.name,
                        targetStudyTime = studies.studyInfo.targetStudyTime,
                        currentMembers = studies.currentMembers,
                        maxMembers = studies.studyInfo.maxMembers,
                        leader = studies.leader.name,
                        createdDate = studies.createdDate,
                        profileImageUrl = studies.studyInfo.profileImg,
                    )

                    if (index == studiesList.lastIndex) {
                        LaunchedEffect(Unit) {
                            onLoadStudies()
                        }
                    }
                }
            }
            if (hasNext) {
                Box(
                    modifier =
                        Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .fillMaxHeight(fraction = 0.15f)
                            .background(
                                brush =
                                    Brush.verticalGradient(
                                        colors =
                                            listOf(
                                                Color.Transparent, // 상단: 완전 투명
                                                NeeGongNaeGongTheme.colorScheme.primaryText.copy(
                                                    alpha = 0.1f,
                                                ), // 하단: 10% 반투명
                                            ),
                                    ),
                            ),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        strokeWidth = 4.dp,
                        color = NeeGongNaeGongTheme.colorScheme.blue,
                    )
                }
            }
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun PreviewStudiesFindScreen() {
    NeeGongNaeGongTheme {
        StudiesFindScreen(
            studiesList = StudiesPreviewDataProvider().getStudies(),
            hasNext = true,
            searchKeyword = "",
            selectedSortType = StudiesSortType.CREATED_AT,
            filterCount = 22,
            onLoadStudies = {},
            onSelectedStudies = {},
            onSearchKeywordChanged = {},
            onSearch = {},
            onSelectedSortType = {},
            onStudiesInfoDialogShow = {},
            onCategoryFilterDialogShow = {},
            navigateToStudiesManagement = {},
            popBackStack = {},
        )
    }
}
