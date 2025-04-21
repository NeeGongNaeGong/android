package com.ssafy.neegongnaegong.presentation.group.component.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.ui.theme.Typography

@Composable
fun StudiesDrawer(
    modifier: Modifier = Modifier,
    headerImageUrl: String? = null,
    onGroupManagementClick: () -> Unit = {},
    onMemberManagementClick: () -> Unit = {},
    onScheduleManagementClick: () -> Unit = {},
    onStudyCreateClick: () -> Unit = {},
    onStudySearchClick: () -> Unit = {},
    onMyStudyClick: () -> Unit = {},
    onStudyItemClick: (Long) -> Unit = {},
) {
    Column(
        modifier =
            modifier
                .fillMaxHeight()
                .fillMaxWidth(0.75f)
                .background(MaterialTheme.colorScheme.surface),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(horizontal = 0.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                    ),
        ) {
            GlideImage(
                imageModel = { headerImageUrl ?: R.drawable.img_main_character },
                modifier = Modifier.fillMaxSize(),
                loading = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                },
                failure = {
                    GlideImage(
                        imageModel = { R.drawable.img_main_character },
                        modifier = Modifier.fillMaxSize(),
                    )
                },
            )

            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f)),
            )
        }
        // 그룹 정보 부분
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surfaceVariant),
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = "취업 스터디",
                    style = Typography.bodyLarge,
                )
                Text(
                    text = "취업을 향해서...",
                    style = Typography.labelMedium,
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 그룹 관리 섹션
        DrawerMenuItem(
            icon = R.drawable.ic_studies_drw_group_management,
            title = stringResource(R.string.studies_drw_group_management),
            onClick = onGroupManagementClick,
        )

        // 멤버 관리 섹션
        DrawerMenuItem(
            icon = R.drawable.ic_studies_drw_member_management,
            title = stringResource(R.string.studies_drw_member_management),
            onClick = onMemberManagementClick,
        )

        // 일정 관리 섹션
        DrawerMenuItem(
            icon = R.drawable.ic_studies_drw_schedule_management,
            title = stringResource(R.string.studies_drw_schedule_management),
            onClick = onScheduleManagementClick,
        )

        Spacer(modifier = Modifier.height(10.dp))

        HorizontalDivider(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            thickness = 1.dp,
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 내 스터디 섹션
        // TODO : 내 스터디 수 추가
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.studies_drw_my_studies),
                style = Typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = stringResource(R.string.studies_drw_see_more),
                style = Typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.clickable { onMyStudyClick() },
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 스터디 아이콘들
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            // TODO(스터디) : 예시 스터디 아이콘들 - 실제 데이터로 교체 필요
            CircleIcon(R.drawable.ic_app_logo_332_81) { onStudyItemClick(1) }
            CircleIcon(R.drawable.ic_app_logo_332_81) { onStudyItemClick(2) }
            CircleIcon(R.drawable.ic_app_logo_332_81) { onStudyItemClick(3) }
        }

        Spacer(modifier = Modifier.height(10.dp))

        HorizontalDivider(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            thickness = 1.dp,
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 스터디 생성 버튼
        DrawerMenuItem(
            icon = R.drawable.ic_studies_drw_studies_create,
            title = stringResource(R.string.studies_drw_studies_create),
            onClick = onStudyCreateClick,
        )

        // 스터디 검색 버튼
        DrawerMenuItem(
            icon = R.drawable.ic_studies_drw_studies_search,
            title = stringResource(R.string.studies_drw_studies_search),
            onClick = onStudySearchClick,
        )
    }
}

@Composable
fun CircleIcon(
    iconRes: Int,
    onClick: () -> Unit = {},
) {
    Box(
        modifier =
            Modifier
                .size(48.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = CircleShape,
                ).clickable {
                    onClick()
                },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = Color.Unspecified,
        )
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun PreviewStudiesDrawer() {
    NeeGongNaeGongTheme {
        StudiesDrawer()
    }
}
