package com.ssafy.neegongnaegong.presentation.group.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import com.ssafy.neegongnaegong.domain.model.studies.NotificationData
import com.ssafy.neegongnaegong.domain.model.studies.WeeklyRankingsMember
import com.ssafy.neegongnaegong.presentation.component.TopAppBar
import com.ssafy.neegongnaegong.presentation.component.TopAppBarNavigationType
import com.ssafy.neegongnaegong.presentation.component.studyrecord.StudyRecordList
import com.ssafy.neegongnaegong.presentation.group.component.detail.CustomStudiesFAB
import com.ssafy.neegongnaegong.presentation.group.component.detail.section.NotificationsSection
import com.ssafy.neegongnaegong.presentation.group.component.detail.section.ProfilesSection
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.StudiesDrawerController
import com.ssafy.neegongnaegong.presentation.util.TimeUnit

@Composable
fun StudiesDetailRoute(
    modifier: Modifier = Modifier,
    navBackStackEntry: NavBackStackEntry,
    studyGroupId: Long,
    popBackStack: () -> Unit = {},
) {
    val viewModel: StudiesDetailViewModel = hiltViewModel(navBackStackEntry)
    BackHandler {
        /* 드로어가 열려 있으면 드로어를 우선 닫음
         * 드로어가 닫혀 있으면 popBackStack 실행
         */
        if (StudiesDrawerController.isOpen.value) {
            StudiesDrawerController.close()
        } else {
            popBackStack()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(StudiesDetailContract.Event.OnLoad(studyGroupId))
        viewModel.setEvent(StudiesDetailContract.Event.OnLoadFeeds(studyGroupId))
        viewModel.setEvent(StudiesDetailContract.Event.OnLoadWeeklyRankings(studyGroupId))
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
    )
}

@Composable
private fun StudiesContent(
    modifier: Modifier = Modifier,
    uiState: StudiesDetailContract.State,
    onLoadFeeds: () -> Unit,
    onLoadWeeklyRankings: () -> Unit,
) {
    StudiesDetailScreen(
        modifier = modifier,
        name = uiState.studies.studyInfo.name,
        feeds = uiState.feeds,
        feedsHasNext = uiState.feedsHasNext,
        onLoadFeeds = onLoadFeeds,
        weeklyRankings = uiState.weeklyRankings,
        studyGoalTime = uiState.studies.studyInfo.targetStudyTime,
        onProfileClick = {},
        onLoadWeeklyRankings = onLoadWeeklyRankings,
    )
}

@Composable
private fun StudiesDetailScreen(
    modifier: Modifier = Modifier,
    name: String,
    feeds: List<LearningRecord> = emptyList(),
    feedsHasNext: Boolean = false,
    onLoadFeeds: () -> Unit,
    weeklyRankings: List<WeeklyRankingsMember> = emptyList(),
    studyGoalTime: Int = (TimeUnit.HOUR.seconds * 7).toInt(),
    onProfileClick: (Long) -> Unit = {},
    onLoadWeeklyRankings: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
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
                StudiesDrawerController.open()
            },
        )

        // 콘텐츠 영역
        Column(modifier = Modifier) {
            // 프로필 아이콘 행
            ProfilesSection(
                modifier = modifier,
                weeklyRankings = weeklyRankings,
                studyGoalTime = studyGoalTime,
                onLoadMore = onLoadWeeklyRankings,
                onProfileClick = onProfileClick,
            )
            Spacer(modifier = Modifier.height(12.dp))
            // 스터디 공지사항 카드 TODO : 실제 데이터 삽입 필요
            NotificationsSection(
                modifier = Modifier.padding(5.dp),
                announcements =
                    NotificationData(
                        id = 1,
                        title = "5월 모임 공지",
                        dateTime = "2025.04.11 09:30:00",
                    ),
                voting =
                    NotificationData(
                        id = 1,
                        title = "점메추 투표",
                        dateTime = "2025.04.01 09:30:00",
                    ),
                onAnnouncementClick = {},
                onVotingClick = {},
            )
            Spacer(modifier = Modifier.height(16.dp))
            StudyRecordList(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                learningRecords = feeds,
                hasNext = feedsHasNext,
                onClick = {},
                onLoadMore = onLoadFeeds,
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
