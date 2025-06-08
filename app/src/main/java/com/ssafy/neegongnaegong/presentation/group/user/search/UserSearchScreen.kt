package com.ssafy.neegongnaegong.presentation.group.user.search

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun UserSearchRoute(
    modifier: Modifier = Modifier,
    viewModel: UserSearchViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BackHandler { popBackStack() }

    UserSearchContent(
        modifier = modifier,
        effect = viewModel.effect,
        uiState = uiState,
        onTypeSearch = { viewModel.setEvent(UserSearchContract.Event.OnTypingSearch(it)) },
        onClickSearch = { viewModel.setEvent(UserSearchContract.Event.OnClickSearch) },
    )
}

@Composable
fun UserSearchContent(
    modifier: Modifier = Modifier,
    effect: Flow<UserSearchContract.Effect>,
    uiState: UserSearchContract.State,
    onTypeSearch: (String) -> Unit,
    onClickSearch: () -> Unit,
) {
    LaunchedEffect(effect) {}

    UserSearchScreen(
        modifier = modifier,
        onTypeSearch = onTypeSearch,
        onClickSearch = onClickSearch,
    )
}

@Composable
fun UserSearchScreen(
    modifier: Modifier = Modifier,
    onTypeSearch: (String) -> Unit,
    onClickSearch: () -> Unit,
) {

}
