package com.ssafy.neegongnaegong.presentation.group.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.TimeUnit

@Composable
fun StudiesCard(
    modifier: Modifier = Modifier,
    category: String = "",
    name: String = "",
    targetStudyTime: Int,
    currentMembers: Int,
    maxMembers: Int,
    leader: String = "",
    createdDate: String = "",
    description: String = "",
    profileImageUrl: String?,
    initialExpanded: Boolean = false,
) {
    var expanded by remember { mutableStateOf(initialExpanded) }

    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = NeeGongNaeGongTheme.colorScheme.gray1),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .animateContentSize(
                        animationSpec =
                            spring(
                                dampingRatio = Spring.DampingRatioLowBouncy,
                                stiffness = Spring.StiffnessMediumLow,
                            ),
                    ),
        ) {
            // 상단 정보와 이미지를 가로로 배치
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // 왼쪽 정보 영역
                Column(
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(start = 16.dp, top = 20.dp, bottom = 20.dp, end = 16.dp),
                ) {
                    // 상단 카테고리 (예: "대학생")
                    Text(
                        text = category,
                        style =
                            NeeGongNaeGongTheme.typography.bodyMedium,
                        color = NeeGongNaeGongTheme.colorScheme.peach,
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // 그룹 제목
                    Text(
                        text = name,
                        style = NeeGongNaeGongTheme.typography.titleMedium.copy(fontSize = 16.sp), // 제목 크기 약간 축소
                        color = NeeGongNaeGongTheme.colorScheme.primaryText,
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        // 목표 시간 | 인원수
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Text(
                                text = "목표 : 주 ${targetStudyTime / TimeUnit.HOUR.seconds}시간",
                                style = NeeGongNaeGongTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                color = NeeGongNaeGongTheme.colorScheme.primaryText,
                            )
                            Text(
                                text = "인원 : $currentMembers / $maxMembers 명",
                                style = NeeGongNaeGongTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                color = NeeGongNaeGongTheme.colorScheme.primaryText,
                            )
                        }
                        // 그룹장 | 생성일"
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Text(
                                text = "그룹장 : $leader",
                                style = NeeGongNaeGongTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                color = NeeGongNaeGongTheme.colorScheme.primaryText,
                            )
                            Text(
                                text = "생성일 : $createdDate",
                                style = NeeGongNaeGongTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                color = NeeGongNaeGongTheme.colorScheme.primaryText,
                            )
                        }
                    }
                }

                // 오른쪽 이미지 영역
                Box(
                    modifier =
                        Modifier
                            .padding(end = 16.dp)
                            .size(width = 80.dp, height = 80.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(NeeGongNaeGongTheme.colorScheme.gray3),
                    contentAlignment = Alignment.Center,
                ) {
                    GlideImage(
                        imageModel = { profileImageUrl },
                        modifier = Modifier.fillMaxSize(),
                        loading = {
                            Box(modifier = Modifier.matchParentSize()) {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center),
                                )
                            }
                        },
                        failure = {
                            Box(
                                modifier =
                                    Modifier
                                        .fillMaxSize()
                                        .background(MaterialTheme.colorScheme.errorContainer),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Error,
                                    contentDescription = "이미지 로드 실패",
                                    modifier = Modifier.align(Alignment.Center),
                                    tint = MaterialTheme.colorScheme.error,
                                )
                            }
                        },
                    )
                }
            }

            // 구분선 (전체 너비)
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = NeeGongNaeGongTheme.colorScheme.secondaryText,
            )

            // 클릭 가능한 설명 줄과 화살표 (항상 표시)
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) { expanded = !expanded }
                        .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(vertical = 8.dp),
                    text = description,
                    style = NeeGongNaeGongTheme.typography.bodyMedium.copy(fontSize = 10.sp),
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                    maxLines = if (expanded) Int.MAX_VALUE else 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (expanded) "접기" else "펼치기",
                    tint = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
            }
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun PreviewStudiesCardExpanded() {
    NeeGongNaeGongTheme {
        StudiesCard(
            category = "대학생",
            name = "개발, 코딩(프론트, 백엔드 등) 취준방",
            targetStudyTime = (TimeUnit.HOUR.seconds * 7).toInt(),
            currentMembers = 3,
            maxMembers = 20,
            leader = "박준식",
            createdDate = "2025-05-05",
            description = "개발 취준을 준비하시는 취준생 분들을 위한 스터디 그룹입니다. 매일 함께 공부해요! 질문과 답변을 자유롭게 나누며 함께 성장해 나가요.",
            profileImageUrl = null,
            initialExpanded = true,
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun PreviewStudiesCard() {
    NeeGongNaeGongTheme {
        StudiesCard(
            category = "대학생",
            name = "개발, 코딩(프론트, 백엔드 등) 취준방",
            targetStudyTime = (TimeUnit.HOUR.seconds * 7).toInt(),
            currentMembers = 3,
            maxMembers = 20,
            leader = "박준식",
            createdDate = "2025-05-05",
            description = "개발 취준을 준비하시는 취준생 분들을 위한 스터디 그룹입니다. 매일 함께 공부해요! 질문과 답변을 자유롭게 나누며 함께 성장해 나가요.",
            profileImageUrl = null,
        )
    }
}
