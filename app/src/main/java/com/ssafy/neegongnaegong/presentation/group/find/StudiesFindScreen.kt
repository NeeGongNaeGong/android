package com.ssafy.neegongnaegong.presentation.group.find

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.domain.model.preview.studies.StudiesPreviewDataProvider
import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.presentation.component.TopAppBar
import com.ssafy.neegongnaegong.presentation.component.TopAppBarNavigationType
import com.ssafy.neegongnaegong.presentation.group.component.StudiesWindow
import com.ssafy.neegongnaegong.presentation.group.component.dialog.StudiesInfoDialog
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
        viewModel.setEvent(StudiesFindContract.Event.OnLoadStudies)
    }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    StudiesFindContent(
        modifier = modifier,
        uiState = uiState,
        effect = viewModel.effect,
        onLoadStudies = { viewModel.setEvent(StudiesFindContract.Event.OnLoadStudies) },
        navigateToStudiesManagement = navigateToStudiesManagement,
        onSelectedStudies = { studies ->
            viewModel.setEvent(StudiesFindContract.Event.OnSelectedStudies(studies))
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
    onStudiesInfoDialogShow: () -> Unit,
    onStudiesInfoDialogConfirm: (Long) -> Unit,
    onStudiesInfoDialogCancel: () -> Unit,
    onStudiesInfoDialogDismiss: () -> Unit,
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
        isLoading = uiState.isLoading,
        onLoadStudies = onLoadStudies,
        onSelectedStudies = onSelectedStudies,
        onStudiesInfoDialogShow = onStudiesInfoDialogShow,
        navigateToStudiesManagement = navigateToStudiesManagement,
        popBackStack = popBackStack,
    )
}

@Composable
private fun StudiesFindScreen(
    modifier: Modifier = Modifier,
    studiesList: List<Studies>,
    isLoading: Boolean,
    onLoadStudies: () -> Unit,
    onSelectedStudies: (Studies) -> Unit,
    onStudiesInfoDialogShow: () -> Unit,
    navigateToStudiesManagement: () -> Unit,
    popBackStack: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        TopAppBar(
            title = {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "스터디 검색",
                    style = NeeGongNaeGongTheme.typography.bodyMedium,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
            },
            navigationType = TopAppBarNavigationType.Back,
            onNavigationClick = popBackStack,
            actionButtons = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Box {
                        Icon(
                            modifier =
                                Modifier
                                    .noRippleClickable { navigateToStudiesManagement() }
                                    .padding(8.dp),
                            // 클릭 영역을 더 크게 만들기 위한 패딩
                            painter = painterResource(R.drawable.ic_topbar_studies_create),
                            tint = NeeGongNaeGongTheme.colorScheme.primaryText,
                            contentDescription = "스터디 생성",
                        )
                    }
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
                                    onSelectedStudies(studies)
                                    onStudiesInfoDialogShow()
                                },
                        category = studies.studyInfo.category?.name ?: "없음",
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

                if (isLoading) {
                    item {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
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
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun PreviewStudiesFindScreen() {
    NeeGongNaeGongTheme {
        StudiesFindScreen(
            studiesList = StudiesPreviewDataProvider().getStudies(),
            isLoading = false,
            onLoadStudies = {},
            onSelectedStudies = {},
            onStudiesInfoDialogShow = {},
            navigateToStudiesManagement = {},
            popBackStack = {},
        )
    }
}
