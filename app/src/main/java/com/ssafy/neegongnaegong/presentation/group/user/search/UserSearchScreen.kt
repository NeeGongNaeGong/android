package com.ssafy.neegongnaegong.presentation.group.user.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.ssafy.neegongnaegong.presentation.group.user.search.component.SearchTopBar
import com.ssafy.neegongnaegong.presentation.group.user.search.component.UserItem
import com.ssafy.neegongnaegong.presentation.group.user.search.component.UserSearchTextField
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

    if (userSearchList.loadState.refresh is androidx.paging.LoadState.Loading) {
        if (userSearchList.loadState.append is androidx.paging.LoadState.Loading) {
        }
    }

    BackHandler { popBackStack() }

    UserSearchContent(
        modifier = modifier,
        effect = viewModel.effect,
        uiState = uiState,
        onTypeSearch = { viewModel.setEvent(UserSearchContract.Event.OnTypingSearch(it)) },
        userSearchList = userSearchList,
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
    popBackStack: () -> Unit,
) {
    LaunchedEffect(effect) {}

    UserSearchScreen(
        modifier = modifier,
        query = uiState.searchKeyword,
        onTypeSearch = onTypeSearch,
        userSearchList = userSearchList,
        popBackStack = popBackStack,
    )
}

@Composable
fun UserSearchScreen(
    modifier: Modifier = Modifier,
    query: String,
    onTypeSearch: (String) -> Unit,
    userSearchList: LazyPagingItems<UserUiModel>,
    popBackStack: () -> Unit = {},
) {
    Column(
        modifier =
            modifier
                .fillMaxSize(),
//                .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        UserSearchTextField(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
            content = query,
            onContentChanged = onTypeSearch,
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(userSearchList.itemCount) { index ->
                val user = userSearchList[index]
                if (user != null) {
                    UserItem(user)
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
            popBackStack = {},
        )
    }
}
