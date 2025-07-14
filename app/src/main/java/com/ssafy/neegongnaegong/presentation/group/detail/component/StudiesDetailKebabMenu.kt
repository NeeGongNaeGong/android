package com.ssafy.neegongnaegong.presentation.group.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun StudiesDetailKebabMenu(
    modifier: Modifier = Modifier,
    onNoticeClick: () -> Unit,
    onVoteClick: () -> Unit,
) {
    var isMenuExpanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        IconButton(onClick = { isMenuExpanded = true }) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.ic_topbar_menu_kebab),
                contentDescription = "더보기 메뉴",
                tint = NeeGongNaeGongTheme.colorScheme.primaryText,
            )
        }
        DropdownMenu(
            modifier = Modifier.background(color = NeeGongNaeGongTheme.colorScheme.gray2),
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false },
        ) {
            val textColor = NeeGongNaeGongTheme.colorScheme.primaryText
            val disabledTextColor = NeeGongNaeGongTheme.colorScheme.secondaryText
            DropdownMenuItem(
                leadingIcon =
                    {
                        Text(
                            modifier = Modifier,
                            text = "\uD83D\uDCE2",
                            style = NeeGongNaeGongTheme.typography.bodySmall,
                        )
                    },
                text = { Text(text = "공지사항", style = NeeGongNaeGongTheme.typography.bodySmall) },
                colors =
                    MenuDefaults.itemColors(
                        textColor = textColor,
                        disabledTextColor = disabledTextColor,
                    ),
                onClick = {
                    onNoticeClick()
                    isMenuExpanded = false
                },
            )

            DropdownMenuItem(
                leadingIcon =
                    {
                        Text(
                            modifier = Modifier,
                            text = "\uD83D\uDDF3\uFE0F",
                            style = NeeGongNaeGongTheme.typography.bodySmall,
                        )
                    },
                text = { Text(text = "투표", style = NeeGongNaeGongTheme.typography.bodySmall) },
                colors =
                    MenuDefaults.itemColors(
                        textColor = textColor,
                        disabledTextColor = disabledTextColor,
                    ),
                onClick = {
                    onVoteClick()
                    isMenuExpanded = false
                },
            )
        }
    }
}

@Composable
@NeeGongNaeGongPreviews
private fun PreviewsStudiesDetailKebabMenu() {
    NeeGongNaeGongTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopEnd,
        ) {
            StudiesDetailKebabMenu(
                onNoticeClick = {},
                onVoteClick = {},
            )
        }
    }
}
