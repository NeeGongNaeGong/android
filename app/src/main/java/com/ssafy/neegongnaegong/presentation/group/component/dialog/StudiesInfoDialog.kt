package com.ssafy.neegongnaegong.presentation.group.component.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.skydoves.landscapist.glide.GlideImage
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.TimeUnit

@Composable
fun StudiesInfoDialog(
    modifier: Modifier = Modifier,
    studies: Studies,
    onConfirm: (Long) -> Unit,
    onCancel: () -> Unit,
    onDismiss: () -> Unit,
) {
    StudiesInfoDialog(
        modifier = modifier,
        studyGroupId = studies.id,
        profileImageUrl = studies.studyInfo.profileImg ?: "",
        name = studies.studyInfo.name,
        leader = studies.leader.name,
        category = studies.studyInfo.category?.name ?: "",
        targetStudyTime = studies.studyInfo.targetStudyTime,
        currentMembers = studies.currentMembers,
        maxMembers = studies.studyInfo.maxMembers,
        description = studies.studyInfo.description,
        onApplyClick = onConfirm,
        onCancel = onCancel,
        onDismiss = onDismiss,
    )
}

/**
 * [StudiesWindow] 클릭시 나오는 다이어로그
 *
 * @param profileImageUrl 스터디 이미지
 * @param name 스터디명
 * @param leader 리더명
 * @param category 카테고리명
 * @param currentMembers 현재 인원수
 * @param maxMembers 최대 인원수
 * @param targetStudyTime 목표 공부시간 (초 단위) [TimeUnit] 참고
 * @param description 스터디 설명
 * @param onApplyClick 스터디 가입 신청 클릭
 * @param onCancel 취소
 * @param onDismiss 다이어로그 닫기
 */
@Composable
private fun StudiesInfoDialog(
    modifier: Modifier = Modifier,
    studyGroupId: Long,
    profileImageUrl: String,
    name: String,
    leader: String,
    category: String,
    targetStudyTime: Int,
    currentMembers: Int,
    maxMembers: Int,
    description: String,
    onApplyClick: (Long) -> Unit,
    onCancel: () -> Unit,
    onDismiss: () -> Unit,
) {
    val nameStyle =
        SpanStyle(
            color = NeeGongNaeGongTheme.colorScheme.primaryText,
            fontSize = NeeGongNaeGongTheme.typography.bodyLarge.fontSize,
            fontStyle = NeeGongNaeGongTheme.typography.bodyLarge.fontStyle,
            fontWeight = NeeGongNaeGongTheme.typography.bodyLarge.fontWeight,
            letterSpacing = NeeGongNaeGongTheme.typography.bodyLarge.letterSpacing,
        )
    val labelStyle =
        SpanStyle(
            color = NeeGongNaeGongTheme.colorScheme.secondaryText,
            fontSize = NeeGongNaeGongTheme.typography.bodyMedium.fontSize,
            fontStyle = NeeGongNaeGongTheme.typography.labelMedium.fontStyle,
            fontWeight = NeeGongNaeGongTheme.typography.labelMedium.fontWeight,
            letterSpacing = NeeGongNaeGongTheme.typography.labelMedium.letterSpacing,
        )
    val defaultStyle =
        SpanStyle(
            color = NeeGongNaeGongTheme.colorScheme.primaryText,
            fontSize = NeeGongNaeGongTheme.typography.bodyMedium.fontSize,
            fontStyle = NeeGongNaeGongTheme.typography.bodyMedium.fontStyle,
            fontWeight = NeeGongNaeGongTheme.typography.bodyMedium.fontWeight,
            letterSpacing = NeeGongNaeGongTheme.typography.bodyMedium.letterSpacing,
        )
    Dialog(onDismissRequest = onCancel) {
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
                Text(
                    text = category,
                    style = NeeGongNaeGongTheme.typography.bodyMedium,
                    color = NeeGongNaeGongTheme.colorScheme.peach,
                )
                Text(
                    modifier = Modifier.padding(bottom = 20.dp),
                    text =
                        buildAnnotatedString {
                            withStyle(style = nameStyle) {
                                append(name)
                            }
                        },
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    // 스터디 이미지
                    Box(
                        modifier =
                            Modifier
                                .padding(horizontal = 4.dp, vertical = 4.dp)
                                .size(120.dp)
                                .clip(RoundedCornerShape(12.dp))
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
                                            .background(color = NeeGongNaeGongTheme.colorScheme.gray3),
                                ) {
                                    Image(
                                        modifier =
                                            Modifier
                                                .size(120.dp)
                                                .align(Alignment.Center),
                                        painter = painterResource(R.drawable.img_main_character),
                                        contentDescription = "이미지 로드 실패",
                                    )
                                }
                            },
                        )
                    }
                    Column(
                        modifier =
                            Modifier
                                .height(120.dp)
                                .weight(1f)
                                .padding(start = 12.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text =
                                buildAnnotatedString {
                                    withStyle(style = labelStyle) {
                                        append("리더  ")
                                    }
                                    withStyle(style = defaultStyle) {
                                        append(leader)
                                    }
                                },
                        )
                        Text(
                            text =
                                buildAnnotatedString {
                                    withStyle(style = labelStyle) {
                                        append("목표  ")
                                    }
                                    withStyle(style = defaultStyle) {
                                        append("주 ${targetStudyTime / TimeUnit.HOUR.seconds}시간")
                                    }
                                },
                        )
                        Text(
                            text =
                                buildAnnotatedString {
                                    withStyle(style = labelStyle) {
                                        append("인원  ")
                                    }
                                    withStyle(style = defaultStyle) {
                                        append("$currentMembers / $maxMembers 명")
                                    }
                                },
                        )
                    }
                }
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 120.dp)
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                ) {
                    Text(
                        text = description,
                        color = NeeGongNaeGongTheme.colorScheme.primaryText,
                        style = NeeGongNaeGongTheme.typography.labelMedium,
                    )
                }

                HorizontalDivider(
                    thickness = 0.4.dp,
                    color = NeeGongNaeGongTheme.colorScheme.secondaryText,
                )
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
                        color = NeeGongNaeGongTheme.colorScheme.secondaryText,
                        modifier = Modifier.height(50.dp),
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(
                        onClick = { onApplyClick(studyGroupId) },
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            text = "가입신청",
                            style = NeeGongNaeGongTheme.typography.labelLarge,
                            color = NeeGongNaeGongTheme.colorScheme.blue,
                        )
                    }
                }
            }
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun PreviewStudiesInfoDialog() {
    NeeGongNaeGongTheme {
        StudiesInfoDialog(
            profileImageUrl = "https://duckduckgo.com/?q=vix",
            studyGroupId = 1,
            name = "정보처리기사 실기 스터디",
            leader = "구미 백호랑이",
            category = "취업",
            targetStudyTime = TimeUnit.HOUR.seconds.toInt() * 7,
            currentMembers = 12,
            maxMembers = 30,
            description = "정보처리기사 실기 취득을 목표로 합니다.\n\n1. 매주 5회독\n2. 오답노트 작성\n3. 주말까지 2문제 이상 만들어 공유\n4. 끝까지 열심히 하기",
            onApplyClick = {},
            onCancel = {},
            onDismiss = {},
        )
    }
}
