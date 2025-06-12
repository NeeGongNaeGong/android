package com.ssafy.neegongnaegong.presentation.group.role.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.skydoves.landscapist.glide.GlideImage
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun RoleChangeDialog(
    modifier: Modifier = Modifier,
    initialMemberRole: StudiesMemberRole,
    profileImageUrl: String,
    userId: Long,
    userName: String,
    onCancel: () -> Unit,
    onDismiss: () -> Unit,
    onConfirm: (Long, StudiesMemberRole) -> Unit,
) {
    val nameStyle =
        SpanStyle(
            color = NeeGongNaeGongTheme.colorScheme.primaryText,
            fontSize = NeeGongNaeGongTheme.typography.bodyMedium.fontSize,
            fontStyle = NeeGongNaeGongTheme.typography.bodyMedium.fontStyle,
            fontWeight = NeeGongNaeGongTheme.typography.bodyMedium.fontWeight,
            letterSpacing = NeeGongNaeGongTheme.typography.bodyMedium.letterSpacing,
        )
    val defaultStyle =
        SpanStyle(
            color = NeeGongNaeGongTheme.colorScheme.primaryText,
            fontSize = NeeGongNaeGongTheme.typography.bodyMedium.fontSize,
            fontStyle = NeeGongNaeGongTheme.typography.labelMedium.fontStyle,
            fontWeight = NeeGongNaeGongTheme.typography.labelMedium.fontWeight,
            letterSpacing = NeeGongNaeGongTheme.typography.labelMedium.letterSpacing,
        )
    val keywordStyle =
        SpanStyle(
            color = NeeGongNaeGongTheme.colorScheme.blue,
            fontSize = NeeGongNaeGongTheme.typography.bodyMedium.fontSize,
            fontStyle = NeeGongNaeGongTheme.typography.bodyMedium.fontStyle,
            fontWeight = NeeGongNaeGongTheme.typography.bodyMedium.fontWeight,
            letterSpacing = NeeGongNaeGongTheme.typography.bodyMedium.letterSpacing,
        )
    val roles: List<StudiesMemberRole> = StudiesMemberRole.entries
    var selectedRole by remember { mutableStateOf(initialMemberRole) }
    Dialog(onDismissRequest = { onCancel.invoke() }) {
        Box(
            modifier =
                modifier
                    .fillMaxWidth()
                    .background(
                        NeeGongNaeGongTheme.colorScheme.background,
                        shape = RoundedCornerShape(12.dp),
                    ),
        ) {
            Column(
                modifier = Modifier.padding(top = 20.dp, start = 10.dp, end = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
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
                Text(
                    text =
                        buildAnnotatedString {
                            withStyle(style = nameStyle) {
                                append(userName)
                            }
                            withStyle(style = defaultStyle) {
                                append("님의 역할을 ")
                            }
                            withStyle(style = keywordStyle) {
                                append("변경")
                            }
                            withStyle(style = defaultStyle) {
                                append("합니다.")
                            }
                        },
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "기존 : ${initialMemberRole.label}",
                    style = NeeGongNaeGongTheme.typography.labelMedium,
                    color = NeeGongNaeGongTheme.colorScheme.secondaryText,
                )

                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn {
                    items(roles) { role ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedRole = role }
                                    .padding(vertical = 4.dp),
                        ) {
                            RadioButton(
                                selected = (role == selectedRole),
                                onClick = { selectedRole = role },
                                colors =
                                    RadioButtonDefaults.colors(
                                        selectedColor = NeeGongNaeGongTheme.colorScheme.blue,
                                    ),
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = role.label,
                                style = NeeGongNaeGongTheme.typography.bodyMedium,
                                color = NeeGongNaeGongTheme.colorScheme.primaryText,
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider(thickness = 0.4.dp, color = Color.Gray)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            text = "취소",
                            style = NeeGongNaeGongTheme.typography.labelLarge,
                            color = NeeGongNaeGongTheme.colorScheme.secondaryText,
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    VerticalDivider(
                        thickness = 0.4.dp,
                        color = Color.Gray,
                        modifier = Modifier.height(50.dp),
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(
                        onClick = { onConfirm(userId, selectedRole) },
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            text = "변경",
                            style = NeeGongNaeGongTheme.typography.labelLarge,
                            color = NeeGongNaeGongTheme.colorScheme.blue,
                        )
                    }
                }
            }
        }
    }
}

@Composable
@NeeGongNaeGongPreviews
private fun PreviewRoleChangeDialog() {
    NeeGongNaeGongTheme {
        RoleChangeDialog(
            profileImageUrl = "https://example.com/profile.jpg",
            userId = -1,
            userName = "심터디",
            initialMemberRole = StudiesMemberRole.TEAM_MEMBER,
            onCancel = {},
            onDismiss = {},
            onConfirm = { _, _ -> },
        )
    }
}
