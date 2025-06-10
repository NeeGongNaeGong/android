package com.ssafy.neegongnaegong.presentation.group.list.vote.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupVoteStatusInfo
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun OptionButton(
    modifier: Modifier = Modifier,
    progress: Float,
    alreadyVoted: Boolean,
    state: Boolean,
    isAnonymous: Boolean,
    isSelected: Boolean,
    optionTitle: String,
    votedUsers: List<StudyGroupVoteStatusInfo.VotedMemberInfo>,
    castMode: Boolean,
    onClick: () -> Unit,
    onClickPersonList: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(0.dp)
                .clickable { if (state && castMode) onClick() },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (state && castMode) {
            Box(contentAlignment = Alignment.Center) {
                val iconModifier = Modifier.size(40.dp)
                Icon(
                    modifier = iconModifier,
                    imageVector = if (isSelected) Icons.Filled.Circle else Icons.Outlined.Circle,
                    contentDescription = "선택 버튼 배경",
                    tint = if (isSelected) NeeGongNaeGongTheme.colorScheme.yellow else NeeGongNaeGongTheme.colorScheme.primaryText,
                )
                if (isSelected) {
                    Icon(
                        modifier = Modifier.size(15.dp),
                        imageVector = Icons.Filled.Check,
                        contentDescription = "체크 표시",
                        tint = NeeGongNaeGongTheme.colorScheme.primaryText,
                    )
                }
            }
        }

        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(modifier = Modifier.weight(1F)) {
                    if ((!castMode || !state) && isSelected) {
                        val iconSizeDp: Dp =
                            with(LocalDensity.current) {
                                NeeGongNaeGongTheme.typography
                                    .labelMedium
                                    .fontSize
                                    .toDp()
                            }

                        Icon(
                            modifier = Modifier.size(iconSizeDp),
                            imageVector = Icons.Outlined.Check,
                            contentDescription = "채크 표시",
                            tint = NeeGongNaeGongTheme.colorScheme.primaryText,
                        )
                    }
                    Text(
                        modifier = Modifier.weight(1F),
                        overflow = TextOverflow.Ellipsis,
                        text = optionTitle,
                        style = NeeGongNaeGongTheme.typography.labelMedium,
                        color = NeeGongNaeGongTheme.colorScheme.primaryText,
                    )
                }
                if ((alreadyVoted || !state) && !isAnonymous) {
                    Row(
                        modifier =
                            Modifier
                                .wrapContentSize()
                                .clickable {
                                    onClickPersonList()
                                },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        val iconSizeDp: Dp =
                            with(LocalDensity.current) {
                                NeeGongNaeGongTheme.typography
                                    .labelSmall
                                    .fontSize
                                    .toDp()
                            }
                        Text(
                            text = "${votedUsers.size}명",
                            style = NeeGongNaeGongTheme.typography.labelSmall,
                            color = NeeGongNaeGongTheme.colorScheme.gray4,
                        )
                        Icon(
                            modifier = Modifier.size(iconSizeDp),
                            imageVector = Icons.Filled.ArrowDropDown,
                            tint = NeeGongNaeGongTheme.colorScheme.primaryText,
                            contentDescription = "투표한 사람 목록",
                        )
                    }
                }
            }
            if ((alreadyVoted || !state) && !isAnonymous) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                            .background(NeeGongNaeGongTheme.colorScheme.gray4),
                ) {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth(progress)
                                .height(5.dp)
                                .background(NeeGongNaeGongTheme.colorScheme.yellow),
                    )
                }
            }
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
fun PreviewOptionButton() {
    NeeGongNaeGongTheme {
        OptionButton(
            isSelected = true,
            progress = 0.3F,
            alreadyVoted = true,
            state = false,
            isAnonymous = false,
            votedUsers = listOf(StudyGroupVoteStatusInfo.VotedMemberInfo(0, ",", "")),
            optionTitle = "종료 시간",
            castMode = false,
            onClick = {},
        ) {}
    }
}

@NeeGongNaeGongPreviews
@Composable
fun PreviewOptionButtonEditMode() {
    NeeGongNaeGongTheme {
        OptionButton(
            isSelected = true,
            progress = 0.3F,
            alreadyVoted = true,
            state = true,
            isAnonymous = false,
            votedUsers = listOf(StudyGroupVoteStatusInfo.VotedMemberInfo(0, ",", "")),
            optionTitle = "종료 시간",
            castMode = true,
            onClick = {},
        ) {}
    }
}
