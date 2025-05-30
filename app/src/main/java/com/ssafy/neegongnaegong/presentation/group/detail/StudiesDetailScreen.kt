package com.ssafy.neegongnaegong.presentation.group.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import com.ssafy.neegongnaegong.domain.model.studies.NotificationData
import com.ssafy.neegongnaegong.domain.model.studies.ProfileData
import com.ssafy.neegongnaegong.presentation.component.TopAppBar
import com.ssafy.neegongnaegong.presentation.component.TopAppBarNavigationType
import com.ssafy.neegongnaegong.presentation.group.component.detail.CustomStudiesFAB
import com.ssafy.neegongnaegong.presentation.group.component.detail.MedalType
import com.ssafy.neegongnaegong.presentation.group.component.detail.section.NotificationsSection
import com.ssafy.neegongnaegong.presentation.group.component.detail.section.ProfilesSection
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.StudiesDrawerController

@Composable
fun StudiesDetailRoute(
    modifier: Modifier = Modifier,
    navBackStackEntry: NavBackStackEntry,
//    viewModel: StudiesDetailViewModel = hiltViewModel(),
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
    }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    StudiesContent(
        modifier = modifier,
        uiState = uiState.value,
    )
}

@Composable
private fun StudiesContent(
    modifier: Modifier = Modifier,
    uiState: StudiesDetailContract.State,
) {
    StudiesDetailScreen(
        modifier = modifier,
        name = uiState.studies.studyInfo.name,
        onProfileClick = {},
        profiles = listOf(),
    )
}

@Composable
private fun StudiesDetailScreen(
    modifier: Modifier = Modifier,
    name: String,
    onProfileClick: (Long) -> Unit = {},
    profiles: List<ProfileData> = emptyList(),
) {
    val scrollState = rememberScrollState()

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
        Column(
            modifier =
                Modifier
                    .verticalScroll(scrollState),
        ) {
            // 프로필 아이콘 행
            ProfilesSection(modifier, profiles, onProfileClick)

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
        }

        // 플로팅 액션 버튼
        CustomStudiesFAB()
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun PreviewStudiesDetailScreen() {
    val previewProfiles =
        listOf(
            ProfileData(
                id = 1,
                medalType = MedalType.GOLD,
                imageUrl = "https://i.pravatar.cc/150?img=1",
                name = "김철수",
                progress = 0.75f,
                ringColors = listOf(Color(0xFFFF9800), Color(0xFFFF5722)),
            ),
            ProfileData(
                id = 2,
                medalType = MedalType.SILVER,
                imageUrl = "https://i.pravatar.cc/150?img=2",
                name = "이영희",
                progress = 0.45f,
                ringColors = listOf(Color(0xFFFF9800), Color(0xFFFF5722)),
            ),
            ProfileData(
                id = 3,
                medalType = MedalType.BRONZE,
                imageUrl = "https://i.pravatar.cc/150?img=3",
                name = "박민수",
                progress = 0.9f,
                ringColors = listOf(Color(0xFFFF9800), Color(0xFFFF5722)),
            ),
            ProfileData(
                id = 4,
                imageUrl = "https://i.pravatar.cc/150?img=4",
                name = "정지원",
                progress = 0.3f,
                ringColors = listOf(Color(0xFFFF9800), Color(0xFFFF5722)),
            ),
            ProfileData(
                id = 5,
                imageUrl = "https://i.pravatar.cc/150?img=5",
                name = "최현우",
                progress = 0.6f,
                ringColors = listOf(Color(0xFFFF9800), Color(0xFFFF5722)),
            ),
        )

    NeeGongNaeGongTheme {
        StudiesDetailScreen(
            onProfileClick = {},
            name = "스터디 이름",
            profiles = previewProfiles,
        )
    }
}
