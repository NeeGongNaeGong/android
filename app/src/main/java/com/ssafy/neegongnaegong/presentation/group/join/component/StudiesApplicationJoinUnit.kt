package com.ssafy.neegongnaegong.presentation.group.join.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

private const val TAG = "StudiesApplicationJoinU"

@Composable
fun StudiesApplicationJoinUnit(
    modifier: Modifier = Modifier,
    status: StudiesJoinApplicationStatus,
    userId: Long,
    name: String,
    profileImageUrl: String,
    onApproval: (Long) -> Unit = {},
    onReject: (Long) -> Unit = {},
) {
    val backgroundColor: Color
    val visibleButton: Boolean

    when (status) {
        StudiesJoinApplicationStatus.PENDING -> {
            backgroundColor = NeeGongNaeGongTheme.colorScheme.background
            visibleButton = true
        }

        StudiesJoinApplicationStatus.APPROVED -> {
            backgroundColor = NeeGongNaeGongTheme.colorScheme.blue
            visibleButton = false
        }

        StudiesJoinApplicationStatus.REJECTED -> {
            backgroundColor = NeeGongNaeGongTheme.colorScheme.secondaryText
            visibleButton = false
        }
    }

    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .background(color = backgroundColor),
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
                    Log.d(TAG, "StudiesApplicationJoinUnit: 이미지 로드 실패")
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
        // 유저 이름
        Text(
            modifier = Modifier.weight(1f),
            text = name,
            style = NeeGongNaeGongTheme.typography.labelLarge,
            color = NeeGongNaeGongTheme.colorScheme.primaryText,
        )
        if (visibleButton) {
            // 수락 버튼
            Button(
                modifier = Modifier.padding(horizontal = 4.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(10.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = NeeGongNaeGongTheme.colorScheme.blue,
                        contentColor = NeeGongNaeGongTheme.colorScheme.background,
                    ),
                onClick = { onApproval(userId) },
            ) {
                Text(
                    text = "수락",
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
                        containerColor = NeeGongNaeGongTheme.colorScheme.secondaryText,
                        contentColor = NeeGongNaeGongTheme.colorScheme.background,
                    ),
                onClick = { onReject(userId) },
            ) {
                Text(
                    text = "거절",
                    style = NeeGongNaeGongTheme.typography.bodyMedium,
                )
            }
        } else {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = status.message,
                style = NeeGongNaeGongTheme.typography.bodyMedium,
                color = NeeGongNaeGongTheme.colorScheme.background,
            )
        }
    }
}

@Composable
@NeeGongNaeGongPreviews
private fun PreviewPendingStudiesApplicationJoinUnit() {
    NeeGongNaeGongTheme {
        StudiesApplicationJoinUnit(
            name = "심터디",
            status = StudiesJoinApplicationStatus.PENDING,
            userId = 1,
            profileImageUrl = "https://picsum.photos/200",
            onApproval = { },
            onReject = { },
        )
    }
}

@Composable
@NeeGongNaeGongPreviews
private fun PreviewApprovedStudiesApplicationJoinUnit() {
    NeeGongNaeGongTheme {
        StudiesApplicationJoinUnit(
            name = "심터디",
            status = StudiesJoinApplicationStatus.APPROVED,
            userId = 1,
            profileImageUrl = "https://picsum.photos/200",
            onApproval = { },
            onReject = { },
        )
    }
}

@Composable
@NeeGongNaeGongPreviews
private fun PreviewRejectedStudiesApplicationJoinUnit() {
    NeeGongNaeGongTheme {
        StudiesApplicationJoinUnit(
            name = "심터디",
            status = StudiesJoinApplicationStatus.REJECTED,
            userId = 1,
            profileImageUrl = "https://picsum.photos/200",
            onApproval = { },
            onReject = { },
        )
    }
}

enum class StudiesJoinApplicationStatus(
    val message: String,
) {
    PENDING("대기중"),
    APPROVED("승인됨"),
    REJECTED("거절됨"),
}
