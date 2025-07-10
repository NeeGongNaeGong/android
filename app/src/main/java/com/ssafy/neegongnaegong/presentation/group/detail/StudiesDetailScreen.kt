package com.ssafy.neegongnaegong.presentation.group.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import com.ssafy.neegongnaegong.domain.model.studies.StudiesLatestContent.LatestNotice
import com.ssafy.neegongnaegong.domain.model.studies.StudiesLatestContent.LatestVote
import com.ssafy.neegongnaegong.domain.model.studies.WeeklyRankingsMember
import com.ssafy.neegongnaegong.presentation.common.LocalDrawerState
import com.ssafy.neegongnaegong.presentation.component.TopAppBar
import com.ssafy.neegongnaegong.presentation.component.TopAppBarNavigationType
import com.ssafy.neegongnaegong.presentation.component.studyrecord.StudyRecordList
import com.ssafy.neegongnaegong.presentation.group.component.detail.CustomStudiesFAB
import com.ssafy.neegongnaegong.presentation.group.component.detail.section.NotificationsSection
import com.ssafy.neegongnaegong.presentation.group.component.detail.section.ProfilesSection
import com.ssafy.neegongnaegong.presentation.group.detail.component.StudiesDetailKebabMenu
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.TimeUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun StudiesDetailRoute(
    modifier: Modifier = Modifier,
    navBackStackEntry: NavBackStackEntry,
    viewModel: StudiesDetailViewModel = hiltViewModel(navBackStackEntry),
    studyGroupId: Long,
    navigateToContents: (Int, Long) -> Unit,
    navigateToLatestNoticeDetail: (Long, Long) -> Unit,
    navigateToLatestVoteDetail: (Long, Long) -> Unit,
    navigateToMemberRecord: (Long) -> Unit,
    popBackStack: () -> Unit = {},
) {
    val currentDrawerState = LocalDrawerState.current
    val scope = rememberCoroutineScope()
    BackHandler {
        /* 드로어가 열려 있으면 드로어를 우선 닫음
         * 드로어가 닫혀 있으면 popBackStack 실행
         */
        if (currentDrawerState.isOpen) {
            scope.launch(Dispatchers.Main.immediate) {
                currentDrawerState.close()
            }
        } else {
            popBackStack()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(StudiesDetailContract.Event.OnLoad(studyGroupId))
        viewModel.setEvent(StudiesDetailContract.Event.OnLoadFeeds(studyGroupId))
        viewModel.setEvent(StudiesDetailContract.Event.OnLoadWeeklyRankings(studyGroupId))
        viewModel.setEvent(StudiesDetailContract.Event.OnLoadLatestContents(studyGroupId))
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is StudiesDetailContract.Effect.NavigateToLatestNoticeDetail ->
                    navigateToLatestNoticeDetail(
                        studyGroupId,
                        effect.noticeId,
                    )

                is StudiesDetailContract.Effect.NavigateToLatestVoteDetail ->
                    navigateToLatestVoteDetail(
                        studyGroupId,
                        effect.voteId,
                    )

                is StudiesDetailContract.Effect.NavigateToContents ->
                    navigateToContents(
                        effect.startTabIndex,
                        studyGroupId,
                    )

                is StudiesDetailContract.Effect.NavigateToProfile ->
                    navigateToMemberRecord(effect.memberId)
            }
        }
    }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    StudiesContent(
        modifier = modifier,
        uiState = uiState.value,
        onLoadFeeds = { viewModel.setEvent(StudiesDetailContract.Event.OnLoadFeeds(studyGroupId)) },
        onLoadWeeklyRankings = {
            viewModel.setEvent(
                StudiesDetailContract.Event.OnLoadWeeklyRankings(studyGroupId),
            )
        },
        onContentsClick = { startTabIndex ->
            viewModel.setEvent(
                StudiesDetailContract.Event.OnClickContents(startTabIndex),
            )
        },
        onLatestNoticeClick = { noticeId ->
            viewModel.setEvent(
                StudiesDetailContract.Event.OnClickLatestNotice(noticeId),
            )
        },
        onLatestVoteClick = { voteId ->
            viewModel.setEvent(
                StudiesDetailContract.Event.OnClickLatestVote(voteId),
            )
        },
        onProfileClick = { memberId ->
            viewModel.setEvent(
                StudiesDetailContract.Event.OnClickProfile(memberId),
            )
        },
    )
}

@Composable
private fun StudiesContent(
    modifier: Modifier = Modifier,
    uiState: StudiesDetailContract.State,
    onLoadFeeds: () -> Unit,
    onLoadWeeklyRankings: () -> Unit,
    onContentsClick: (Int) -> Unit,
    onLatestNoticeClick: (Long) -> Unit,
    onLatestVoteClick: (Long) -> Unit,
    onProfileClick: (Long) -> Unit,
) {
    val currentDrawerState = LocalDrawerState.current
    StudiesDetailScreen(
        modifier = modifier,
        currentDrawerState = currentDrawerState,
        name = uiState.studyGroupDetailInfo.name,
        feeds = uiState.feeds,
        feedsHasNext = uiState.feedsHasNext,
        onLoadFeeds = onLoadFeeds,
        weeklyRankings = uiState.weeklyRankings,
        studyGoalTime = uiState.studyGroupDetailInfo.targetStudyTime,
        latestNotice = uiState.latestNotice,
        latestNoticeReadChecked = uiState.latestNoticeReadChecked,
        latestVote = uiState.latestVote,
        latestVoteReadChecked = uiState.latestVoteReadChecked,
        onProfileClick = onProfileClick,
        onLoadWeeklyRankings = onLoadWeeklyRankings,
        onContentsClick = onContentsClick,
        onLatestNoticeClick = onLatestNoticeClick,
        onLatestVoteClick = onLatestVoteClick,
    )
}

@Composable
private fun StudiesDetailScreen(
    modifier: Modifier = Modifier,
    currentDrawerState: DrawerState = DrawerState(DrawerValue.Closed),
    name: String,
    feeds: List<LearningRecord> = emptyList(),
    feedsHasNext: Boolean = false,
    onLoadFeeds: () -> Unit,
    weeklyRankings: List<WeeklyRankingsMember> = emptyList(),
    studyGoalTime: Int = (TimeUnit.HOUR.seconds * 7).toInt(),
    latestNotice: LatestNotice? = null,
    latestNoticeReadChecked: Boolean = false,
    latestVote: LatestVote? = null,
    latestVoteReadChecked: Boolean = false,
    onProfileClick: (Long) -> Unit,
    onLoadWeeklyRankings: () -> Unit,
    onContentsClick: (Int) -> Unit = {},
    onLatestNoticeClick: (Long) -> Unit = {},
    onLatestVoteClick: (Long) -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier.fillMaxSize().background(color = NeeGongNaeGongTheme.colorScheme.gray2),
    ) {
        // 커스텀 앱바
        TopAppBar(
            title = {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = name,
                    style = NeeGongNaeGongTheme.typography.bodyMedium,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
            },
            navigationType = TopAppBarNavigationType.Menu,
            onNavigationClick = {
                scope.launch(Dispatchers.Main.immediate) {
                    currentDrawerState.open()
                }
            },
            actionButtons = {
                StudiesDetailKebabMenu(
                    modifier = Modifier,
                    onNoticeClick = { onContentsClick(0) },
                    onVoteClick = { onContentsClick(1) },
                )
            },
        )

        // 콘텐츠 영역
        Column(modifier = Modifier) {
            // 프로필 아이콘 행
            ProfilesSection(
                modifier = Modifier.background(color = NeeGongNaeGongTheme.colorScheme.background),
                weeklyRankings = weeklyRankings,
                studyGoalTime = studyGoalTime,
                onLoadMore = onLoadWeeklyRankings,
                onProfileClick = onProfileClick,
            )

            // 스터디 공지사항 카드
            NotificationsSection(
                modifier = Modifier.padding(5.dp),
                notice = latestNotice,
                noticeReadCheck = latestNoticeReadChecked,
                vote = latestVote,
                voteReadCheck = latestVoteReadChecked,
                onNoticeClick = onLatestNoticeClick,
                onVotingClick = onLatestVoteClick,
            )
            StudyRecordList(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                learningRecords = feeds,
                hasNext = feedsHasNext,
                onClick = {},
                onLoadMore = onLoadFeeds,
                isStudyFeed = true,
            )
        }

        // 플로팅 액션 버튼
        CustomStudiesFAB()
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun PreviewStudiesDetailScreen() {
    NeeGongNaeGongTheme {
        StudiesDetailScreen(
            name = "스터디 이름",
            onLoadFeeds = {},
            onProfileClick = {},
            onLoadWeeklyRankings = {},
        )
    }
}
