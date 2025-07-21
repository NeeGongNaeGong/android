package com.ssafy.neegongnaegong.presentation.component.studyrecord

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import com.ssafy.neegongnaegong.domain.model.preview.personal.PersonalPreviewDataProvider
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.toHourMinuteString
import com.ssafy.neegongnaegong.presentation.util.toTimeString

@Composable
fun StudyRecordItem(
    record: LearningRecord,
    isStudyFeed: Boolean = false,
    isSelectedMode: Boolean = false,
    isDeleteSelected: Boolean = false,
    onClick: (Long) -> Unit = {},
    onDeleteSelect: (Long) -> Unit = {},
) {
    if (isStudyFeed) {
        StudyFeedItem(record, onClick)
    } else {
        PersonalRecordItem(
            record = record,
            isSelectedMode = isSelectedMode,
            isDeleteSelected = isDeleteSelected,
            onClick = onClick,
            onDeleteSelect = onDeleteSelect,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PersonalRecordItem(
    record: LearningRecord,
    isSelectedMode: Boolean = false,
    isDeleteSelected: Boolean = false,
    onClick: (Long) -> Unit = {},
    onDeleteSelect: (Long) -> Unit = {},
) {
    val currentAlpha = if (isDeleteSelected) 0.5f else 1.0f
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(8.dp))
                .background(
                    NeeGongNaeGongTheme.colorScheme.recordBackground,
                    RoundedCornerShape(8.dp),
                ).clickable(onClick = {
                    if (isSelectedMode) {
                        onDeleteSelect(record.id)
                    } else {
                        onClick(record.id)
                    }
                })
                .padding(16.dp),
    ) {
        Column {
            // 제목 + 시간
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (isSelectedMode) {
                    Checkbox(
                        modifier = Modifier.requiredSize(16.dp).padding(end = 8.dp),
                        checked = isDeleteSelected,
                        onCheckedChange = {
                            onDeleteSelect(record.id)
                        },
                        colors =
                            CheckboxDefaults.colors(
                                checkedColor = NeeGongNaeGongTheme.colorScheme.blue,
                                uncheckedColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                                checkmarkColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                            ),
                    )
                }
                Text(
                    modifier = Modifier.weight(1f).alpha(currentAlpha),
                    text = record.title.ifBlank { "제목을 설정해주세요." },
                    style = NeeGongNaeGongTheme.typography.titleMedium.copy(fontSize = 20.sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )

                Spacer(modifier = Modifier.width(8.dp))

                val start = record.startAt.toTimeString()
                val end = record.endAt.toHourMinuteString()

                Text(
                    modifier = Modifier.alpha(currentAlpha),
                    text = "$start ~ $end",
                    style =
                        NeeGongNaeGongTheme.typography.bodySmall.copy(
                            fontSize = 10.sp,
                            color = Color.Gray,
                        ),
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 내용
            Text(
                modifier = Modifier.alpha(currentAlpha),
                text = record.content.ifBlank { "내용을 설정해주세요" },
                style = NeeGongNaeGongTheme.typography.bodySmall.copy(fontSize = 12.sp),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                color = NeeGongNaeGongTheme.colorScheme.secondaryText,
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 태그
            FlowRow(
                modifier = Modifier.alpha(currentAlpha),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                record.tags.forEach { tag ->
                    Text(
                        text = "#${tag.koName}",
                        fontSize = 12.sp,
                        color = NeeGongNaeGongTheme.colorScheme.primaryText,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun StudyFeedItem(
    record: LearningRecord,
    onClick: (Long) -> Unit = {},
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
//                .shadow(4.dp, RoundedCornerShape(8.dp))
                .background(
                    NeeGongNaeGongTheme.colorScheme.gray2,
                    RoundedCornerShape(4.dp),
                ).clickable(onClick = { onClick(record.id) })
                .padding(vertical = 8.dp, horizontal = 16.dp),
    ) {
        Column {
            // 제목 + 시간
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = record.title.ifBlank { "제목을 설정해주세요." },
                    style = NeeGongNaeGongTheme.typography.titleMedium.copy(fontSize = 20.sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )

                Spacer(modifier = Modifier.width(8.dp))

                Row(
                    modifier = Modifier,
                ) {
                    Box(
                        modifier =
                            Modifier
                                .padding(end = 4.dp)
                                .size(28.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(NeeGongNaeGongTheme.colorScheme.gray3),
                    ) {
                        GlideImage(
                            imageModel = { record.author.profileImg },
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
                }

                Text(
                    text = record.author.nickname,
                    style = NeeGongNaeGongTheme.typography.labelMedium,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 내용
            Text(
                modifier = Modifier.defaultMinSize(minHeight = 60.dp),
                text = record.content.ifBlank { "내용을 설정해주세요" },
                style = NeeGongNaeGongTheme.typography.bodySmall.copy(fontSize = 12.sp),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                color = NeeGongNaeGongTheme.colorScheme.secondaryText,
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                // 태그
                FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    record.tags.forEach { tag ->
                        Text(
                            text = "#${tag.koName}",
                            fontSize = 12.sp,
                            color = NeeGongNaeGongTheme.colorScheme.primaryText,
                        )
                    }
                }
                val start = record.startAt.toTimeString()
                val end = record.endAt.toHourMinuteString()
                Text(
                    text = "$start ~ $end",
                    style =
                        NeeGongNaeGongTheme.typography.bodySmall.copy(
                            fontSize = 10.sp,
                            color = Color.Gray,
                        ),
                )
            }
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun PreviewPersonalRecordItem() {
    NeeGongNaeGongTheme {
        PersonalRecordItem(record = PersonalPreviewDataProvider().getStudyRecord())
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun PreviewStudyFeedItem() {
    NeeGongNaeGongTheme {
        StudyFeedItem(record = PersonalPreviewDataProvider().getStudyRecord())
    }
}
