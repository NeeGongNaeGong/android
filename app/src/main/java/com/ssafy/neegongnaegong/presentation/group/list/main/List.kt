package com.ssafy.neegongnaegong.presentation.group.list.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ssafy.neegongnaegong.presentation.component.TopAppBar
import com.ssafy.neegongnaegong.presentation.group.list.VoteListRoute
import com.ssafy.neegongnaegong.presentation.navigation.AppNavigation
import com.ssafy.neegongnaegong.presentation.navigation.Index
import com.ssafy.neegongnaegong.presentation.navigation.ListItem
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun ListRoute(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit,
    viewModel: ListViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Column {
        TopAppBar(
            title = {
                Text(
                    style = NeeGongNaeGongTheme.typography.titleSmall,
                    text = "~~공부방",
                )
            },
            onNavigationClick = { popBackStack() },
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
        ListScreen(modifier = modifier, state.index) { tab ->
            viewModel.setEvent(
                ListContract.Event.OnClickTab(
                    tab,
                ),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    selectedTab: Index,
    onTabClick: (Index) -> Unit,
) {
    val listController = rememberNavController()

//    이하 현재 어디 있는지 알기 위한 코드
//    val backStackEntry by listController.currentBackStackEntryAsState()
//    val currentDestination = backStackEntry?.destination
//    val tabs = ListItem.map { it.route }
//    val selectedIndex = tabs.indexOfFirst { tab ->
//        currentDestination?.hierarchy?.any {
//            it.hasRoute(tab::class)
//        } == true
//    }

    Column {
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
            ListItem.forEach { item ->
                Tab(
                    modifier = Modifier.background(NeeGongNaeGongTheme.colorScheme.background),
                    selectedContentColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                    unselectedContentColor = NeeGongNaeGongTheme.colorScheme.secondaryText,
                    selected = selectedTab.index == item.index,
                    onClick = { onTabClick(item) },
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
        NavHost(
            navController = listController,
            startDestination = AppNavigation.Screen.Studies.List.NoticeList,
        ) {
            composable<AppNavigation.Screen.Studies.List.NoticeList> {

            }
            composable<AppNavigation.Screen.Studies.List.NoticeDetail> {

            }
            composable<AppNavigation.Screen.Studies.List.VoteList> {
                VoteListRoute()
            }
            composable<AppNavigation.Screen.Studies.List.VoteDetail> {

            }
        }
        LaunchedEffect(selectedTab) {
            val targetRoute = selectedTab.route
            listController.navigate(targetRoute) {
                popUpTo(targetRoute) { inclusive = true }
            }
        }
    }
}

@Composable
fun ListContent(selectedTab: Index) {
    when (selectedTab) {
        Index.Notice -> {
            TODO("공지 화면 만들고 그곳으로 이동하도록 하기")
        }

        Index.Vote -> {
            VoteListRoute(viewModel = viewmo)
        }
    }
}

@Composable
@NeeGongNaeGongPreviews
fun PreviewListContent() {
    NeeGongNaeGongTheme {
        ListScreen(selectedTab = Index.Vote, onTabClick = {})
    }
}
