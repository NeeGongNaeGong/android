package com.ssafy.neegongnaegong.presentation.group.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.ui.theme.Typography

@Composable
fun StudiesCard(
    modifier: Modifier = Modifier,
    category: String = "",
    title: String = "",
    goalTime: String = "",
    memberInfo: String = "",
    leader: String = "",
    startInfo: String = "",
    description: String = "",
    initialExpanded: Boolean = false,
) {
    var expanded by remember { mutableStateOf(initialExpanded) }

    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
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
                            .padding(start = 16.dp, top = 12.dp, bottom = 12.dp, end = 8.dp),
                ) {
                    // 상단 카테고리 (예: "대학생")
                    Text(
                        text = category,
                        style =
                            NeeGongNaeGongTheme.typography.bodyMedium.copy(
                                color = Color(0xFFE53935),
                            ),
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // 그룹 제목
                    Text(
                        text = title,
                        style = NeeGongNaeGongTheme.typography.titleMedium.copy(fontSize = 16.sp), // 제목 크기 약간 축소
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // 목표 시간 | 인원수
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = goalTime,
                            style = NeeGongNaeGongTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = memberInfo,
                            style = NeeGongNaeGongTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    // 그룹장 | 시작일"
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = leader,
                            style = NeeGongNaeGongTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = startInfo,
                            style = NeeGongNaeGongTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                        )
                    }
                }

                // 오른쪽 이미지 영역
                Box(
                    modifier =
                        Modifier
                            .padding(end = 16.dp)
                            .size(width = 80.dp, height = 80.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFD9D9D9)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Study Group Character",
                        modifier = Modifier.size(50.dp),
                        tint = Color(0xFF666666),
                    )
                }
            }

            // 구분선 (전체 너비)
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

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
                    text = description,
                    style = NeeGongNaeGongTheme.typography.bodyMedium.copy(fontSize = 10.sp),
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(vertical = 8.dp),
                    maxLines = if (expanded) Int.MAX_VALUE else 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (expanded) "접기" else "펼치기",
                )
            }
        }
    }
}

@Composable
@Preview(widthDp = 400, heightDp = 140)
private fun PreviewStudiesCard() {
    NeeGongNaeGongTheme {
        StudiesCard(
            category = "대학생",
            title = "개발, 코딩(프론트, 백엔드 등) 취준방",
            goalTime = "목표 3시간",
            memberInfo = "인원 3/20명",
            leader = "그룹장 박준식",
            startInfo = "시작일 2일 전",
            description = "개발 취준을 준비하시는 취준생 분들을 위한 스터디 그룹입니다. 매일 함께 공부해요! 질문과 답변을 자유롭게 나누며 함께 성장해 나가요.",
        )
    }
}

@Composable
@Preview(widthDp = 400, heightDp = 200)
private fun PreviewStudiesCardExpanded() {
    NeeGongNaeGongTheme {
        StudiesCard(
            category = "대학생",
            title = "개발, 코딩(프론트, 백엔드 등) 취준방",
            goalTime = "목표 3시간",
            memberInfo = "인원 3/20명",
            leader = "그룹장 박준식",
            startInfo = "시작일 2일 전",
            description = "개발 취준을 준비하시는 취준생 분들을 위한 스터디 그룹입니다. 매일 함께 공부해요! 질문과 답변을 자유롭게 나누며 함께 성장해 나가요.",
            initialExpanded = true,
        )
    }
}
