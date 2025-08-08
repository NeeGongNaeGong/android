package com.ssafy.neegongnaegong.presentation.group.find.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun StudiesFilterKebabMenu(
    modifier: Modifier = Modifier,
    selectedFilter: StudiesFilterType,
    onFilterSelected: (StudiesFilterType) -> Unit,
) {
    var isMenuExpanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = { isMenuExpanded = true },
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_filter),
                contentDescription = "스터디 검색 필터",
                tint = NeeGongNaeGongTheme.colorScheme.primaryText,
            )
        }
        DropdownMenu(
            modifier = Modifier.background(color = NeeGongNaeGongTheme.colorScheme.gray2),
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false },
        ) {
            StudiesFilterType.entries.forEach { filterType ->
                val isSelected = (filterType == selectedFilter)
                val contentColor =
                    if (isSelected) {
                        NeeGongNaeGongTheme.colorScheme.primaryText
                    } else {
                        NeeGongNaeGongTheme.colorScheme.secondaryText
                    }
                val contentStyle =
                    if (isSelected) {
                        NeeGongNaeGongTheme.typography.bodyMedium.copy(fontSize = 14.sp)
                    } else {
                        NeeGongNaeGongTheme.typography.labelMedium.copy(fontSize = 14.sp)
                    }
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(filterType.iconResId),
                            contentDescription = "필터 아이콘",
                            tint = contentColor,
                        )
                    },
                    text = {
                        Text(
                            modifier = Modifier,
                            text = filterType.display,
                            style = contentStyle,
                            color = contentColor,
                        )
                    },
                    onClick = {
                        onFilterSelected(filterType)
                        isMenuExpanded = false
                    },
                )
            }
        }
    }
}

@Composable
@NeeGongNaeGongPreviews
private fun PreviewsStudiesFilterKebabMenu() {
    NeeGongNaeGongTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopEnd,
        ) {
            StudiesFilterKebabMenu(
                selectedFilter = StudiesFilterType.CREATED_AT,
                onFilterSelected = {},
            )
        }
    }
}

enum class StudiesFilterType(
    @DrawableRes val iconResId: Int,
    val display: String,
    val requestParam: String,
) {
    CREATED_AT(R.drawable.ic_filter_created, "생성순", "createdAt"),
    CURRENT_MEMBERS(R.drawable.ic_filter_member, "인원순", "currentMembers"),
    NAME(R.drawable.ic_filter_name, "이름순", "name"),
}
