package com.ssafy.neegongnaegong.presentation.group.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CardColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun StudiesMaxMemberDropdown(
    modifier: Modifier = Modifier,
    currentMembers: Int,
    maxMembers: Int,
    onMaxMembersChanged: (Int) -> Unit,
) {
    var maxMembersDropdown by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        OutlinedCard(
            modifier =
                Modifier
                    .width(120.dp)
                    .clickable { maxMembersDropdown = true },
            shape = RoundedCornerShape(8.dp),
            colors =
                CardColors(
                    containerColor = NeeGongNaeGongTheme.colorScheme.background,
                    contentColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent,
                ),
        ) {
            Row(
                modifier =
                    Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "$maxMembers 명",
                    style = NeeGongNaeGongTheme.typography.bodyLarge,
                )

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "선택하기",
                    tint = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
            }
        }

        DropdownMenu(
            expanded = maxMembersDropdown,
            onDismissRequest = { maxMembersDropdown = false },
            modifier =
                Modifier
                    .width(120.dp)
                    .heightIn(max = 200.dp)
                    .background(color = NeeGongNaeGongTheme.colorScheme.gray2),
        ) {
            for (memberCount in currentMembers..30) {
                DropdownMenuItem(
                    text = { Text("$memberCount 명") },
                    onClick = {
                        onMaxMembersChanged(memberCount)
                        maxMembersDropdown = false
                    },
                    colors =
                        MenuDefaults.itemColors(
                            textColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                            disabledTextColor = NeeGongNaeGongTheme.colorScheme.secondaryText,
                        ),
                )
            }
        }
    }
}

@Composable
@NeeGongNaeGongPreviews
private fun PreviewStudiesMaxMemberDropdown() {
    NeeGongNaeGongTheme {
        StudiesMaxMemberDropdown(
            modifier = Modifier,
            currentMembers = 10,
            maxMembers = 20,
            onMaxMembersChanged = { },
        )
    }
}
