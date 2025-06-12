package com.ssafy.neegongnaegong.presentation.group.role.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun StudiesMemberRoleUnit(
    modifier: Modifier = Modifier,
    role: StudiesMemberRole,
    name: String,
    profileImageUrl: String,
    onChangeRole: () -> Unit = {},
    onExpel: () -> Unit = {},
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .background(color = NeeGongNaeGongTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // 유저 프로필
        Box(
            modifier =
                Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .size(50.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(NeeGongNaeGongTheme.colorScheme.gray3),
        ) {
            GlideImage(
                imageModel = { profileImageUrl },
                loading = {
                    Box(modifier = Modifier.matchParentSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = NeeGongNaeGongTheme.colorScheme.blue,
                        )
                    }
                },
                failure = {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .background(color = NeeGongNaeGongTheme.colorScheme.blue),
                    ) {
                        Image(
                            modifier =
                                Modifier
                                    .size(80.dp)
                                    .align(Alignment.Center),
                            painter = painterResource(R.drawable.img_default_profile),
                            contentDescription = "이미지 로드 실패",
                        )
                    }
                },
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            // 유저 이름
            Text(
                text = name,
                style = NeeGongNaeGongTheme.typography.labelLarge,
                color = NeeGongNaeGongTheme.colorScheme.primaryText,
            )
            // 역할
            Text(
                text = role.label,
                style = NeeGongNaeGongTheme.typography.labelMedium,
                color = NeeGongNaeGongTheme.colorScheme.secondaryText,
            )
        }
        Button(
            modifier = Modifier.padding(horizontal = 4.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
            shape = RoundedCornerShape(10.dp),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = NeeGongNaeGongTheme.colorScheme.blue,
                    contentColor = NeeGongNaeGongTheme.colorScheme.background,
                ),
            onClick = onChangeRole,
        ) {
            Text(
                text = "직책",
                style = NeeGongNaeGongTheme.typography.bodyMedium,
            )
        }
        // 거절 버튼
        Button(
            modifier = Modifier.padding(horizontal = 4.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
            shape = RoundedCornerShape(10.dp),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = NeeGongNaeGongTheme.colorScheme.peach,
                    contentColor = NeeGongNaeGongTheme.colorScheme.background,
                ),
            onClick = onExpel,
        ) {
            Text(
                text = "강퇴",
                style = NeeGongNaeGongTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
@NeeGongNaeGongPreviews
private fun PreviewStudiesMemberRoleUnit() {
    NeeGongNaeGongTheme {
        StudiesMemberRoleUnit(
            role = StudiesMemberRole.TEAM_MEMBER,
            name = "심터디",
            profileImageUrl = "https://example.com/profile.jpg",
        )
    }
}

enum class StudiesMemberRole(
    val label: String,
) {
    TEAM_LEADER("스터디장"),
    TEAM_MANAGER("매니저"),
    TEAM_MEMBER("팀원"),
}
