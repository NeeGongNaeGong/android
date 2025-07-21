package com.ssafy.neegongnaegong.presentation.personal.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.component.TopAppBar
import com.ssafy.neegongnaegong.presentation.component.TopAppBarNavigationType
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.noRippleClickable

@Composable
fun PersonalTopAppBar(
    isSelectedMode: Boolean,
    deleteSelectedRecordIds: Set<Long>,
    onModeChange: () -> Unit = {},
    onCancel: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    val keywordStyle =
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

    TopAppBar(
        modifier = Modifier.defaultMinSize(minHeight = 32.dp),
        navigationType = TopAppBarNavigationType.None,
        title = {
            Text(
                text =
                    if (isSelectedMode) {
                        buildAnnotatedString {
                            withStyle(style = keywordStyle) {
                                append("${deleteSelectedRecordIds.size}")
                            }
                            withStyle(style = defaultStyle) {
                                append("개 선택")
                            }
                        }
                    } else {
                        buildAnnotatedString {
                            withStyle(style = keywordStyle) {
                                append("내 기록")
                            }
                        }
                    },
            )
        },
        actionButtons = {
            if (isSelectedMode) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        modifier =
                            Modifier.noRippleClickable {
                                onCancel()
                                onModeChange()
                            },
                        text = "취소",
                        style = NeeGongNaeGongTheme.typography.bodySmall.copy(fontSize = 16.sp),
                        color = NeeGongNaeGongTheme.colorScheme.primaryText,
                    )
                    Text(
                        modifier =
                            Modifier.noRippleClickable {
                                onDelete()
                            },
                        text = "삭제",
                        style = NeeGongNaeGongTheme.typography.bodySmall.copy(fontSize = 16.sp),
                        color = NeeGongNaeGongTheme.colorScheme.primaryText,
                    )
                }
            } else {
                Icon(
                    modifier =
                        Modifier
                            .size(28.dp)
                            .padding(end = 4.dp)
                            .noRippleClickable { onModeChange() },
                    painter = painterResource(R.drawable.ic_common_trash),
                    contentDescription = "기록 삭제",
                    tint = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
            }
        },
    )
}

@Composable
@NeeGongNaeGongPreviews
private fun PreviewPersonalTopAppBarFalse() {
    NeeGongNaeGongTheme {
        PersonalTopAppBar(
            isSelectedMode = false,
            deleteSelectedRecordIds = emptySet(),
        )
    }
}

@Composable
@NeeGongNaeGongPreviews
private fun PreviewPersonalTopAppBarTrue() {
    NeeGongNaeGongTheme {
        PersonalTopAppBar(
            isSelectedMode = true,
            deleteSelectedRecordIds = emptySet(),
        )
    }
}
