package com.ssafy.neegongnaegong.presentation.group.user.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ssafy.neegongnaegong.presentation.group.user.search.component.ReportDialog
import com.ssafy.neegongnaegong.presentation.group.user.search.component.UserItem
import com.ssafy.neegongnaegong.presentation.group.user.search.component.UserSearchTextField
import com.ssafy.neegongnaegong.presentation.group.user.search.model.UserReportData
import com.ssafy.neegongnaegong.presentation.group.user.search.model.UserUiModel
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun UserSearchRoute(
    modifier: Modifier = Modifier,
    viewModel: UserSearchViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val userSearchList = viewModel.searchUserList.collectAsLazyPagingItems()

    BackHandler { viewModel.setEvent(UserSearchContract.Event.OnBackClick) }

    UserSearchContent(
        modifier = modifier,
        effect = viewModel.effect,
        uiState = uiState,
        onTypeSearch = { viewModel.setEvent(UserSearchContract.Event.OnTypingSearch(keyword = it)) },
        userSearchList = userSearchList,
        onReportClick = { viewModel.setEvent(UserSearchContract.Event.OnReportClick(user = it)) },
        onDismissDialog = { viewModel.setEvent(UserSearchContract.Event.OnReportDialogDismiss) },
        onConfirmDialog = {
            viewModel.setEvent(
                UserSearchContract.Event.OnReportDialogConfirm(
                    userReportData = it,
                ),
            )
        },
        popBackStack = popBackStack,
    )
}

@Composable
fun UserSearchContent(
    modifier: Modifier = Modifier,
    effect: Flow<UserSearchContract.Effect>,
    uiState: UserSearchContract.State,
    onTypeSearch: (String) -> Unit,
    userSearchList: LazyPagingItems<UserUiModel>,
    // dialog
    onReportClick: (UserUiModel) -> Unit,
    onDismissDialog: () -> Unit,
    onConfirmDialog: (UserReportData) -> Unit,
    popBackStack: () -> Unit,
) {
    LaunchedEffect(effect) {
        effect.collect { effect ->
            when (effect) {
                is UserSearchContract.Effect.NavigateBack -> {
                    popBackStack()
                }
            }
        }
    }

    if (uiState.isReportDialogOpen) {
        ReportDialog(
            user = uiState.reportUser,
            onDismiss = onDismissDialog,
            onReport = onConfirmDialog,
        )
    }

    UserSearchScreen(
        modifier = modifier,
        query = uiState.searchKeyword,
        onTypeSearch = onTypeSearch,
        userSearchList = userSearchList,
        onReportClick = onReportClick,
        popBackStack = popBackStack,
    )
}

@Composable
fun UserSearchScreen(
    modifier: Modifier = Modifier,
    query: String,
    onTypeSearch: (String) -> Unit,
    userSearchList: LazyPagingItems<UserUiModel>,
    onReportClick: (UserUiModel) -> Unit,
    popBackStack: () -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(4.dp))

            IconButton(
                modifier =
                    Modifier
                        .padding(start = 12.dp)
                        .size(24.dp),
                onClick = popBackStack,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "뒤로가기",
                    tint = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
            }

            UserSearchTextField(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                content = query,
                onContentChanged = onTypeSearch,
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(
                count = userSearchList.itemCount,
                key = { index: Int -> userSearchList[index]?.id ?: index },
            ) { index ->
                val user = userSearchList[index]
                if (user != null) {
                    UserItem(
                        user = user,
                        onReportClick = onReportClick,
                    )
                }
            }
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
fun UserSearchScreenPreview() {
    val fakeModel =
        UserUiModel(1, "John Doe", "john.doe@example.com", "https://example.com/avatar.jpg")
    val fakeList = List(10) { fakeModel }
    val fakeFlow = flowOf(PagingData.from(fakeList)).collectAsLazyPagingItems()

    NeeGongNaeGongTheme {
        UserSearchScreen(
            query = "Preview Query",
            onTypeSearch = {},
            userSearchList = fakeFlow,
            onReportClick = {},
            popBackStack = {},
        )
    }
}
