package com.ssafy.neegongnaegong.presentation.group.list.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ssafy.neegongnaegong.presentation.component.LoadingDialog
import com.ssafy.neegongnaegong.presentation.component.TopAppBar
import com.ssafy.neegongnaegong.presentation.navigation.AppNavigation
import com.ssafy.neegongnaegong.presentation.navigation.Index
import com.ssafy.neegongnaegong.presentation.navigation.studyGroupListNavGraph
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.coroutines.flow.collectLatest
import okhttp3.internal.toImmutableList

@Composable
fun ListRoute(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit,
    title: String,
    viewModel: ListViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val effect = viewModel.effect

    // ViewModel에서 stateHandler에서 NavArg 받아서 세팅하는 동안 LoadingDialog 생성
    if (state.groupId == -1L) {
        LoadingDialog()
    } else {
        LaunchedEffect(effect) {
            effect.collectLatest {
                when (it) {
                    ListContract.Effect.NavigateToBackStack -> popBackStack()

                    ListContract.Effect.NavigateToNoticeScreen -> {
                        navController.navigate(
                            AppNavigation.Screen.Studies.List.Tab.Notice,
                        ) {
                            popUpTo<AppNavigation.Screen.Studies.List.Tab.Notice> {
                                inclusive = true
                            }
                        }
                    }

                    ListContract.Effect.NavigateToVoteScreen -> {
                        navController.navigate(
                            AppNavigation.Screen.Studies.List.Tab.Vote,
                        ) {
                            popUpTo<AppNavigation.Screen.Studies.List.Tab.Vote> {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }

        val backStackEntry by navController.currentBackStackEntryAsState()
        LaunchedEffect(backStackEntry) {
            /*
             * 결론적으로 현재 Navigation의 경로로 존재하는 Tab(공지 아니면, 투표)이
             * 현재 viewModel에서 관리하고 있는 값이랑 같지 않을 때만 Tab이 선택된 곳을 갱신함(어떤 탭이 선택됐는지를 갱신한다는 뜻)
             *
             * 이렇게 backStackEntry로 알 수 있으면서 viewModel에서 state를 통해 Index를 관리하는 이유는
             * SecondaryTabRow에서 선택된 탭을 고르기 위해서 사용하는 Index를 State로 관리해서 Index가 변할 때마다 recomposition 시켜주기 위함
             * 그리고 최초 보여줄 탭을 어디로 할 것인지(스터디 그룹에서 화면상 공지로 들어올 수도 있고, 투표로 들어올 수도 있기 때문에)를 Navigation Arg로
             * 받기 때문에, 이를 ViewModel에서 init으로 초기화하면서 설정
             *
             * 그럼 되려 state로 관리하는데, backStackEntry가 필요하냐고 묻는다면
             * 뒤로 갈 때는 별도로 state 변경 관련 작업을 하지 않기 때문
             * state 관련 작업이 일어나지 않기 때문에, backStackEntry의 변경점이 있을 때마다 확인해서 바뀐 경우 state의 index 값을 갱신하는 것
             */
            if (backStackEntry?.destination?.hierarchy?.all {
                    it.hasRoute(state.index.route::class)
                } == false
            ) {
                viewModel.setEvent(
                    ListContract.Event.OnSyncTab(
                        if (state.index.route == Index.Notice.route) Index.Vote else Index.Notice,
                    ),
                )
            }
        }
    }

    Column {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    style = NeeGongNaeGongTheme.typography.titleSmall,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
            },
            onNavigationClick = popBackStack,
            actionButtons = {
                IconButton(onClick = {
                    viewModel.setEvent(ListContract.Event.OnClickAddContent)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Create,
                        contentDescription = "추가 아이콘",
                    )
                }
            },
        )
        ListContent(
            modifier = modifier,
            navController = navController,
            selectedTab = state.index,
            groupId = state.groupId,
            onTabChanged = { tab ->
                viewModel.setEvent(
                    ListContract.Event.OnTabChanged(tab),
                )
            },
        )
    }
}

@Composable
private fun ListContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    selectedTab: Index,
    groupId: Long,
    onTabChanged: (Index) -> Unit,
) {
    /**
     초기에 NavHost의 startDestination을 위한 변수,
     remember를 통해서 state가 변하더라도 NavHost가 recomposition되는 일이 없도록 함
     */
    val initialRoute = remember { selectedTab.route }

    Column {
        ListTab(modifier, selectedTab, onTabChanged)
        ListScreen(navController, groupId, initialRoute)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListTab(
    modifier: Modifier = Modifier,
    selectedTab: Index,
    onTabChanged: (Index) -> Unit,
) {
    SecondaryTabRow(
        modifier = modifier,
        selectedTabIndex = selectedTab.index,
        indicator = {
            TabRowDefaults.SecondaryIndicator(
                modifier =
                    Modifier
                        .tabIndicatorOffset(selectedTab.index, false),
                color = NeeGongNaeGongTheme.colorScheme.primaryText,
            )
        },
    ) {
        Index.entries.toImmutableList().forEach { item ->
            Tab(
                modifier = Modifier.background(NeeGongNaeGongTheme.colorScheme.background),
                selectedContentColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                unselectedContentColor = NeeGongNaeGongTheme.colorScheme.secondaryText,
                selected = selectedTab.index == item.index,
                onClick = { if (selectedTab != item) onTabChanged(item) },
                text = {
                    Text(
                        text = item.title,
                        style = NeeGongNaeGongTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
            )
        }
    }
}

@Composable
private fun ListScreen(
    navController: NavHostController,
    groupId: Long,
    initialRoute: AppNavigation.Screen.Studies.List.Tab,
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = initialRoute,
    ) {
        studyGroupListNavGraph(navController, groupId)
    }
}

@Composable
@NeeGongNaeGongPreviews
private fun PreviewListContent() {
    NeeGongNaeGongTheme {
        Column {
            ListTab(
                selectedTab = Index.Vote,
                onTabChanged = {},
            )
            Text(modifier = Modifier.fillMaxSize().align(Alignment.CenterHorizontally), text = "ListScreen 공간")
        }
    }
}
