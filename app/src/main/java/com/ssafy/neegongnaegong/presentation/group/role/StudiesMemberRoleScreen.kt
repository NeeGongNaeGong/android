package com.ssafy.neegongnaegong.presentation.group.role

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
import com.ssafy.neegongnaegong.domain.model.studies.StudiesMember
import com.ssafy.neegongnaegong.domain.model.studygroup.Role
import com.ssafy.neegongnaegong.presentation.component.TopAppBar
import com.ssafy.neegongnaegong.presentation.group.role.component.ExpelMemberDialog
import com.ssafy.neegongnaegong.presentation.group.role.component.RoleChangeDialog
import com.ssafy.neegongnaegong.presentation.group.role.component.StudiesMemberRole
import com.ssafy.neegongnaegong.presentation.group.role.component.StudiesMemberRoleUnit
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun StudiesMemberRoleRoute(
    modifier: Modifier = Modifier,
    viewModel: StudiesMemberRoleViewModel = hiltViewModel(),
    studyGroupId: Long,
    role: Role,
    popBackStack: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.setEvent(StudiesMemberRoleContract.Event.OnLoadStudiesMembers(studyGroupId))
    }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    StudiesMemberRoleContent(
        modifier = modifier,
        effect = viewModel.effect,
        role = role,
        uiState = uiState.value,
        onSelectedMember = { viewModel.setEvent(StudiesMemberRoleContract.Event.OnSelectedMember(it)) },
        // Change Role Dialog
        onChangeRoleDialogShow = { viewModel.setEvent(StudiesMemberRoleContract.Event.OnChangeRoleDialogShow) },
        onChangeRoleDialogCancel = { viewModel.setEvent(StudiesMemberRoleContract.Event.OnChangeRoleDialogCancel) },
        onChangeRoleDialogConfirm = { userId, changeRole ->
            viewModel.setEvent(
                StudiesMemberRoleContract.Event.OnChangeRoleDialogConfirm(
                    userId,
                    changeRole,
                ),
            )
        },
        onChangeRoleDialogDismiss = { viewModel.setEvent(StudiesMemberRoleContract.Event.OnChangeRoleDialogDismiss) },
        // Expel Member Dialog
        onExpelMemberDialogShow = { viewModel.setEvent(StudiesMemberRoleContract.Event.OnExpelMemberDialogShow) },
        onExpelMemberDialogCancel = { viewModel.setEvent(StudiesMemberRoleContract.Event.OnExpelMemberDialogCancel) },
        onExpelMemberDialogConfirm = {
            viewModel.setEvent(
                StudiesMemberRoleContract.Event.OnExpelMemberDialogConfirm(
                    it,
                ),
            )
        },
        onExpelMemberDialogDismiss = { viewModel.setEvent(StudiesMemberRoleContract.Event.OnExpelMemberDialogDismiss) },
        popBackStack = popBackStack,
    )
}

@Composable
private fun StudiesMemberRoleContent(
    modifier: Modifier = Modifier,
    effect: Flow<StudiesMemberRoleContract.Effect>,
    role: Role,
    uiState: StudiesMemberRoleContract.State,
    onSelectedMember: (StudiesMember) -> Unit,
    // Change Role Dialog
    onChangeRoleDialogShow: () -> Unit,
    onChangeRoleDialogCancel: () -> Unit,
    onChangeRoleDialogConfirm: (Long, StudiesMemberRole) -> Unit,
    onChangeRoleDialogDismiss: () -> Unit,
    // Expel Member Dialog
    onExpelMemberDialogShow: () -> Unit,
    onExpelMemberDialogCancel: () -> Unit,
    onExpelMemberDialogConfirm: (Long) -> Unit,
    onExpelMemberDialogDismiss: () -> Unit,
    popBackStack: () -> Unit,
) {
    Log.d("임시", "$effect") // TODO (effect 처리예정)
    if (uiState.isChangeRoleDialogShow) {
        if (uiState.selectedMember != null) {
            RoleChangeDialog(
                modifier = modifier,
                initialMemberRole = uiState.selectedMember.groupRole,
                profileImageUrl = uiState.selectedMember.profileImg,
                userId = uiState.selectedMember.userId,
                userName = uiState.selectedMember.name,
                onCancel = onChangeRoleDialogCancel,
                onDismiss = onChangeRoleDialogDismiss,
                onConfirm = onChangeRoleDialogConfirm,
            )
        }
    }

    if (uiState.isExpelMemberDialogShow) {
        if (uiState.selectedMember != null) {
            ExpelMemberDialog(
                modifier = modifier,
                profileImageUrl = uiState.selectedMember.profileImg,
                userId = uiState.selectedMember.userId,
                userName = uiState.selectedMember.name,
                onCancel = onExpelMemberDialogCancel,
                onDismiss = onExpelMemberDialogDismiss,
                onConfirm = onExpelMemberDialogConfirm,
            )
        }
    }

    StudiesMemberRoleScreen(
        modifier = modifier,
        isLoading = uiState.isLoading,
        role = role,
        studiesMembers = uiState.studiesMembers,
        onSelectedMember = onSelectedMember,
        onChangeRoleDialogShow = onChangeRoleDialogShow,
        onExpelMemberDialogShow = onExpelMemberDialogShow,
        popBackStack = popBackStack,
    )
}

@Composable
private fun StudiesMemberRoleScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    role: Role,
    studiesMembers: List<StudiesMember>,
    onSelectedMember: (StudiesMember) -> Unit,
    onChangeRoleDialogShow: () -> Unit,
    onExpelMemberDialogShow: () -> Unit,
    popBackStack: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        TopAppBar(
            title = {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "멤버 관리",
                    style = NeeGongNaeGongTheme.typography.bodyMedium,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
            },
            onNavigationClick = popBackStack,
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            itemsIndexed(studiesMembers) { index, member ->
                StudiesMemberRoleUnit(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    myRole = role,
                    memberRole = member.groupRole,
                    name = member.name,
                    profileImageUrl = member.profileImg,
                    onChangeRole = {
                        onSelectedMember(member)
                        onChangeRoleDialogShow()
                    },
                    onExpel = {
                        onSelectedMember(member)
                        onExpelMemberDialogShow()
                    },
                )
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
private fun Preview_Leader_StudiesMemberRoleScreen() {
    NeeGongNaeGongTheme {
        StudiesMemberRoleScreen(
            isLoading = false,
            role = Role.TEAM_LEADER,
            studiesMembers = StudiesPreviewDataProvider().getStudiesMembers(),
            onSelectedMember = {},
            onChangeRoleDialogShow = {},
            onExpelMemberDialogShow = {},
            popBackStack = {},
        )
    }
}

@Composable
@NeeGongNaeGongPreviews
private fun Preview_Member_StudiesMemberRoleScreen() {
    NeeGongNaeGongTheme {
        StudiesMemberRoleScreen(
            isLoading = false,
            role = Role.TEAM_MEMBER,
            studiesMembers = StudiesPreviewDataProvider().getStudiesMembers(),
            onSelectedMember = {},
            onChangeRoleDialogShow = {},
            onExpelMemberDialogShow = {},
            popBackStack = {},
        )
    }
}
