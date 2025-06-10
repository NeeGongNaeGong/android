package com.ssafy.neegongnaegong.presentation.group.user.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.group.user.search.model.UserUiModel
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun UserItem(
    user: UserUiModel,
    onReportClick: (UserUiModel) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.weight(1f), // 남은 공간 차지
            verticalAlignment = Alignment.CenterVertically,
        ) {
            GlideImage(
                imageModel = { user.profileImg },
                modifier =
                    Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray),
                imageOptions =
                    ImageOptions(
                        contentScale = ContentScale.Crop,
                    ),
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = user.nickname,
                    style =
                        NeeGongNaeGongTheme.typography.bodySmall.copy(
                            fontSize = 16.sp,
                            color = NeeGongNaeGongTheme.colorScheme.primaryText,
                        ),
                )
                // 추후 이메일도 표시 하려면 이렇게
//                Text(
//                    text = user.email,
//                    style = NeeGongNaeGongTheme.typography.bodySmall.copy(
//                        fontSize = 16.sp,
//                        color = NeeGongNaeGongTheme.colorScheme.secondaryText,
//                    ),
//                )
            }
        }

        // 오른쪽 끝에 고정된 신고 아이콘
        Icon(
            modifier =
                Modifier
                    .padding(end = 16.dp)
                    .clickable(onClick = { onReportClick(user) }),
            painter = painterResource(id = R.drawable.ic_siren),
            contentDescription = "신고 아이콘",
            tint = Color.Red,
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
fun UserItemPreview() {
    NeeGongNaeGongTheme {
        UserItem(
            user =
                UserUiModel(
                    id = 1,
                    nickname = "닉네임",
                    email = "이메일",
                    profileImg = "",
                ),
            onReportClick = {},
        )
    }
}
