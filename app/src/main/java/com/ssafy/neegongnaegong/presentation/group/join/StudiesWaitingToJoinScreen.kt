package com.ssafy.neegongnaegong.presentation.group.join

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.neegongnaegong.domain.model.preview.studies.StudiesPreviewDataProvider
import com.ssafy.neegongnaegong.domain.model.studies.StudiesApplicationsMember
import com.ssafy.neegongnaegong.presentation.component.TopAppBar
import com.ssafy.neegongnaegong.presentation.group.component.drawer.model.Role
import com.ssafy.neegongnaegong.presentation.group.join.component.StudiesApplicationJoinUnit
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun StudiesWaitingToJoinRoute(
    modifier: Modifier = Modifier,
    viewModel: StudiesWaitingToJoinViewModel = hiltViewModel(),
    studyGroupId: Long,
    role: Role,
    popBackStack: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.setEvent(StudiesWaitingToJoinContract.Event.OnLoadStudiesApplications(studyGroupId))
    }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    StudiesWaitingToJoinContent(
        modifier = modifier,
        effect = viewModel.effect,
        uiState = uiState.value,
        role = role,
        onLoadApplicationsList = {
            viewModel.setEvent(
                StudiesWaitingToJoinContract.Event.OnLoadStudiesApplications(studyGroupId),
            )
        },
        onApproval = {
            viewModel.setEvent(
                StudiesWaitingToJoinContract.Event.OnApproval(it),
            )
        },
        onReject = {
            viewModel.setEvent(
                StudiesWaitingToJoinContract.Event.OnReject(it),
            )
        },
        popBackStack = popBackStack,
    )
}

@Composable
private fun StudiesWaitingToJoinContent(
    modifier: Modifier = Modifier,
    effect: Flow<StudiesWaitingToJoinContract.Effect>,
    uiState: StudiesWaitingToJoinContract.State,
    role: Role,
    onLoadApplicationsList: () -> Unit,
    onApproval: (Long) -> Unit,
    onReject: (Long) -> Unit,
    popBackStack: () -> Unit,
) {
    Log.d("임시", "$effect") // TODO (effect 처리예정)

    StudiesWaitingToJoinScreen(
        modifier = modifier,
        role = role,
        isLoading = uiState.isLoading,
        applicationsList = uiState.applicationsList,
        onLoadApplicationsList = onLoadApplicationsList,
        onApproval = onApproval,
        onReject = onReject,
        popBackStack = popBackStack,
    )
}

@Composable
private fun StudiesWaitingToJoinScreen(
    modifier: Modifier = Modifier,
    role: Role,
    isLoading: Boolean,
    applicationsList: List<StudiesApplicationsMember>,
    onLoadApplicationsList: () -> Unit,
    onApproval: (Long) -> Unit,
    onReject: (Long) -> Unit,
    popBackStack: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        TopAppBar(
            title = {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "가입신청 현황",
                    style = NeeGongNaeGongTheme.typography.bodyMedium,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
            },
            onNavigationClick = popBackStack,
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            itemsIndexed(applicationsList) { index, application ->
                StudiesApplicationJoinUnit(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    role = role,
                    status = application.status,
                    userId = application.userId,
                    name = application.name,
                    profileImageUrl = application.profileImageUrl,
                    onApproval = onApproval,
                    onReject = onReject,
                )

                if (index == applicationsList.lastIndex) {
                    LaunchedEffect(Unit) {
                        onLoadApplicationsList()
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

@Composable
@NeeGongNaeGongPreviews
private fun Preview_Manager_StudiesWaitingToJoinScreen() {
    NeeGongNaeGongTheme {
        StudiesWaitingToJoinScreen(
            applicationsList = StudiesPreviewDataProvider().getStudiesApplications(),
            isLoading = false,
            role = Role.MANAGER,
            onLoadApplicationsList = {},
            onApproval = {},
            onReject = {},
            popBackStack = {},
        )
    }
}

@Composable
@NeeGongNaeGongPreviews
private fun Preview_Member_StudiesWaitingToJoinScreen() {
    NeeGongNaeGongTheme {
        StudiesWaitingToJoinScreen(
            applicationsList = StudiesPreviewDataProvider().getStudiesApplications(),
            isLoading = false,
            role = Role.MEMBER,
            onLoadApplicationsList = {},
            onApproval = {},
            onReject = {},
            popBackStack = {},
        )
    }
}
