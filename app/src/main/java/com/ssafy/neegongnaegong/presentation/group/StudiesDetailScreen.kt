package com.ssafy.neegongnaegong.presentation.group

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.component.TopAppBar
import com.ssafy.neegongnaegong.presentation.component.TopAppBarNavigationType
import com.ssafy.neegongnaegong.presentation.group.component.detail.CustomStudiesFAB
import com.ssafy.neegongnaegong.presentation.group.component.detail.MedalType
import com.ssafy.neegongnaegong.presentation.group.component.detail.NotificationData
import com.ssafy.neegongnaegong.presentation.group.component.detail.section.NotificationsSection
import com.ssafy.neegongnaegong.presentation.group.component.detail.section.ProfilesSection
import com.ssafy.neegongnaegong.presentation.group.component.drawer.StudiesDrawer
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.ui.theme.Typography
import kotlinx.coroutines.launch

data class ProfileData(
    val id: Long,
    val medalType: MedalType = MedalType.NONE,
    val imageUrl: String,
    val name: String,
    val progress: Float,
    val ringColors: List<Color> = listOf(Color(0xFFFF9800), Color(0xFFFF5722)),
)

data class StudyNotice(
    val title: String,
    val date: String,
    val content: String,
)

data class StudyMember(
    val id: String,
    val name: String,
    val imageUrl: String,
    val rank: Int,
    val progress: Float,
)

private const val TAG = "StudiesDetailScreen"

@Composable
fun StudiesDetailRoute(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit = {},
) {
    BackHandler {
        popBackStack()
    }

    StudiesContent(
        modifier = modifier,
    )
}

@Composable
fun StudiesContent(modifier: Modifier = Modifier) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            StudiesDrawer(
                modifier = modifier,
                headerImageUrl = null,
                onGroupManagementClick = {},
                onMemberManagementClick = {},
                onScheduleManagementClick = {},
                onStudyCreateClick = {},
                onStudySearchClick = {},
                onMyStudyClick = {},
                onStudyItemClick = {},
            )
        },
    ) {
        StudiesDetailScreen(
            modifier = modifier,
            onOpenDrawer = {
                scope.launch {
                    drawerState.open()
                }
            },
            onProfileClick = {},
            profiles = listOf(),
            popBackStack = {},
        )
    }
}

@Composable
fun StudiesDetailScreen(
    modifier: Modifier = Modifier,
    onOpenDrawer: () -> Unit = {},
    onProfileClick: (Long) -> Unit = {},
    profiles: List<ProfileData> = emptyList(),
    popBackStack: () -> Unit = {},
) {
    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            // 커스텀 앱바
            TopAppBar(
                modifier = Modifier.padding(vertical = 10.dp),
                title = {
                    Text(
                        modifier = Modifier.padding(vertical = 10.dp),
                        text = "수학 스터디",
                        style = Typography.bodyMedium,
                    )
                },
                navigationType = TopAppBarNavigationType.Menu,
                onNavigationClick = {
                    onOpenDrawer()
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
                // 스터디 공지사항 카드
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

                // 하단 여백 추가 (네비게이션 바 높이만큼)
                Spacer(modifier = Modifier.height(16.dp))
            }

            // 플로팅 액션 버튼
            CustomStudiesFAB()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStudiesDetailScreen() {
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
            profiles = previewProfiles,
        )
    }
}
