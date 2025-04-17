package com.ssafy.neegongnaegong.presentation.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.ui.theme.Typography

@Composable
fun MyPageScreen(
    imageUrl: String,
    userName: String,
    notificationCount: Long,
    onPhotoButtonClick: () -> Unit,
    onEditUserNameButtonClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onNoticeClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onSignOutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(0.4f))
        Box(modifier = Modifier, contentAlignment = Alignment.Center) {
            AsyncImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(134.dp),
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            IconButton(
                modifier = Modifier
                    .background(Color.White, CircleShape)
                    .padding(5.dp)
                    .size(20.dp)
                    .align(alignment = Alignment.BottomEnd),
                onClick = onPhotoButtonClick
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = null,
                )
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = userName,
                style = Typography.bodyLarge,
            )
            IconButton(
                onClick = onEditUserNameButtonClick
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Default.Create,
                    contentDescription = null
                )
            }
        }

        Spacer(Modifier.weight(0.2f))
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        onNotificationClick()
                    },
                text = stringResource(R.string.notification),
                style = Typography.bodyLarge,
                textAlign = TextAlign.Start
            )

            if (notificationCount != 0L) {
                Box(
                    modifier = modifier
                        .size(30.dp)
                        .background(
                            color = Color.Red,
                            shape = CircleShape
                        )
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier,
                        text = "$notificationCount",
                        color = Color.White,
                        style = Typography.bodyLarge
                    )
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 20.dp),
            thickness = 1.dp, color = Color(0xFFD9D9D9)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onNoticeClick()
                },
            text = stringResource(R.string.notice),
            style = Typography.bodyLarge,
            textAlign = TextAlign.Start
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 20.dp),
            thickness = 1.dp, color = Color(0xFFD9D9D9)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onPrivacyPolicyClick()
                },
            text = stringResource(R.string.privacy_policy),
            style = Typography.bodyLarge,
            textAlign = TextAlign.Start
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 20.dp),
            thickness = 1.dp, color = Color(0xFFD9D9D9)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onSignOutClick()
                },
            text = stringResource(R.string.sign_out),
            style = Typography.bodyLarge,
            textAlign = TextAlign.Start
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 20.dp),
            thickness = 1.dp, color = Color(0xFFD9D9D9)
        )
        Spacer(Modifier.weight(1f))
    }
}


@Composable
@NeeGongNaeGongPreviews
private fun MyPageScreenPreview() {
    NeeGongNaeGongTheme {
        MyPageScreen(
            imageUrl = "data:image/jpeg;base64,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",
            userName = "열심히하는 김민조",
            onPhotoButtonClick = {},
            onEditUserNameButtonClick = {},
            notificationCount = 1,
            onNotificationClick = {},
            onNoticeClick = {},
            onSignOutClick = {},
            onPrivacyPolicyClick = {}
        )
    }
}