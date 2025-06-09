package com.ssafy.neegongnaegong.presentation.group.user.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.ssafy.neegongnaegong.presentation.group.user.search.component.SearchTopBar
import com.ssafy.neegongnaegong.presentation.group.user.search.component.UserSearchTextField
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun UserSearchRoute(
    modifier: Modifier = Modifier,
    viewModel: UserSearchViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val userSearchList = viewModel.searchUserList.collectAsLazyPagingItems()

    if(userSearchList.loadState.refresh is androidx.paging.LoadState.Loading){
        if(userSearchList.loadState.append is androidx.paging.LoadState.Loading){

        }
    }

    BackHandler { popBackStack() }

    UserSearchContent(
        modifier = modifier,
        effect = viewModel.effect,
        uiState = uiState,
        onTypeSearch = { viewModel.setEvent(UserSearchContract.Event.OnTypingSearch(it)) },
        onClickSearch = { viewModel.setEvent(UserSearchContract.Event.OnClickSearch) },
        popBackStack = popBackStack,
    )
}

@Composable
fun UserSearchContent(
    modifier: Modifier = Modifier,
    effect: Flow<UserSearchContract.Effect>,
    uiState: UserSearchContract.State,
    onTypeSearch: (String) -> Unit,
    onClickSearch: () -> Unit,
    popBackStack: () -> Unit,
) {
    LaunchedEffect(effect) {}

    UserSearchScreen(
        modifier = modifier,
        query = uiState.searchKeyword,
        onTypeSearch = onTypeSearch,
        onClickSearch = onClickSearch,
        popBackStack = popBackStack,
    )
}

@Composable
fun UserSearchScreen(
    modifier: Modifier = Modifier,
    query: String,
    onTypeSearch: (String) -> Unit,
    onClickSearch: () -> Unit,
    popBackStack: () -> Unit = {},
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        SearchTopBar { popBackStack() }

        UserSearchTextField(
            modifier = Modifier.fillMaxWidth(),
            content = query,
            onContentChanged = onTypeSearch,
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
fun UserSearchScreenPreview() {
    NeeGongNaeGongTheme {
        UserSearchScreen(
            query = "Preview Query",
            onTypeSearch = {},
            onClickSearch = {},
        )
    }
}
