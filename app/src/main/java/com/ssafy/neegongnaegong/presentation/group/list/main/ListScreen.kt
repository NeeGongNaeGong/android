package com.ssafy.neegongnaegong.presentation.group.list.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import com.ssafy.neegongnaegong.domain.model.studygroup.NoticeHistoryInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.VoteHistoryInfo
import com.ssafy.neegongnaegong.presentation.component.LoadingDialog
import com.ssafy.neegongnaegong.presentation.component.TopAppBar
import com.ssafy.neegongnaegong.presentation.group.list.main.ListContract.Effect
import com.ssafy.neegongnaegong.presentation.group.list.main.ListContract.Event
import com.ssafy.neegongnaegong.presentation.group.list.main.ListContract.Index
import com.ssafy.neegongnaegong.presentation.group.list.notice.NoticeListRoute
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteListRoute
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList

@Composable
fun ListRoute(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit,
    startTabIdx: Int,
    viewModel: ListViewModel = hiltViewModel(),
    navigateToNoticeDetail: (Long) -> Unit,
    navigateToVoteDetail: (Long) -> Unit,
    navigateToMakeVote: () -> Unit,
    navigateToMakeNotice: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val pagerState =
        rememberPagerState(pageCount = { Index.entries.size }, initialPage = startTabIdx)
    val effect = viewModel.effect

    // ViewModel에서 stateHandler에서 NavArg 받아서 세팅하는 동안 LoadingDialog 생성
    if (state.groupId == -1L) {
        LoadingDialog()
    } else {
        LaunchedEffect(effect) {
            effect.collect {
                when (it) {
                    Effect.NavigateToBackStack -> popBackStack()

                    is Effect.NavigateToNoticeDetailScreen -> {
                        navigateToNoticeDetail(it.noticeId)
                    }

                    is Effect.NavigateToVoteDetailScreen -> {
                        navigateToVoteDetail(it.voteId)
                    }

                    is Effect.NavigateToMakeNotice -> navigateToMakeNotice()
                    is Effect.NavigateToMakeVote -> navigateToMakeVote()
                }
            }
        }
        Column {
            TopAppBar(
                title = {
                    Text(
                        text = "게시판",
                        style = NeeGongNaeGongTheme.typography.titleSmall,
                        color = NeeGongNaeGongTheme.colorScheme.primaryText,
                    )
                },
                onNavigationClick = popBackStack,
                actionButtons = {
                    IconButton(onClick = {
                        viewModel.setEvent(Event.OnClickAddContent(pagerState.currentPage))
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
                pagerState = pagerState,
                viewModel.noticeListFlow,
                viewModel.voteListFlow,
                { noticeId -> viewModel.setEvent(Event.OnClickNoticeItem(noticeId)) },
                { voteId -> viewModel.setEvent(Event.OnClickVoteItem(voteId)) },
            )
        }
    }
}

@Composable
private fun ListContent(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    noticeListFlow: Flow<PagingData<NoticeHistoryInfo>>,
    voteListFlow: Flow<PagingData<VoteHistoryInfo>>,
    onClickNoticeItem: (Long) -> Unit,
    onClickVoteItem: (Long) -> Unit,
) {
    Column {
        ListTab(modifier, pagerState)
        ListScreen(pagerState, noticeListFlow, voteListFlow, onClickNoticeItem, onClickVoteItem)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListTab(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
) {
    val selectedTab = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()
    SecondaryTabRow(
        modifier = modifier,
        selectedTabIndex = selectedTab,
        indicator = {
            TabRowDefaults.SecondaryIndicator(
                modifier =
                    Modifier
                        .tabIndicatorOffset(selectedTab, false),
                color = NeeGongNaeGongTheme.colorScheme.primaryText,
            )
        },
    ) {
        Index.entries.toImmutableList().forEach { item ->
            Tab(
                modifier = Modifier.background(NeeGongNaeGongTheme.colorScheme.background),
                selectedContentColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                unselectedContentColor = NeeGongNaeGongTheme.colorScheme.secondaryText,
                selected = selectedTab == item.index,
                onClick = {
                    coroutineScope.launch(Dispatchers.Main.immediate) {
                        pagerState.animateScrollToPage(
                            item.index,
                        )
                    }
                },
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
    pagerState: PagerState,
    noticeListFlow: Flow<PagingData<NoticeHistoryInfo>>,
    voteListFlow: Flow<PagingData<VoteHistoryInfo>>,
    onClickNoticeItem: (Long) -> Unit,
    onClickVoteItem: (Long) -> Unit,
) {
    HorizontalPager(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(
                    start = NeeGongNaeGongTheme.paddingScheme.sp3,
                    end = NeeGongNaeGongTheme.paddingScheme.sp3,
                    top = NeeGongNaeGongTheme.paddingScheme.sp3,
                ),
        state = pagerState,
    ) { pageIndex ->
        when (pageIndex) {
            Index.Notice.index -> NoticeListRoute(noticeListFlow, onClickNoticeItem)
            Index.Vote.index -> VoteListRoute(voteListFlow, onClickVoteItem)
        }
    }
}

@Composable
@NeeGongNaeGongPreviews
private fun PreviewListContent() {
    NeeGongNaeGongTheme {
        Column {
            ListTab(pagerState = rememberPagerState(pageCount = { 0 }))
            Text(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally),
                text = "ListScreen 공간",
            )
        }
    }
}
