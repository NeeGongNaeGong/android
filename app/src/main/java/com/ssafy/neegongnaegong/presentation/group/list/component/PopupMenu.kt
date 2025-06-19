package com.ssafy.neegongnaegong.presentation.group.list.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun PopupMenu(
    modifier: Modifier = Modifier,
    showPopup: Boolean,
    onClickPopup: () -> Unit,
    onDismissPopup: () -> Unit,
    onClickDeleteMenu: () -> Unit,
    onClickEditMenu: () -> Unit,
) {
    Box(modifier = modifier) {
        IconButton(
            modifier = Modifier.wrapContentSize(),
            onClick = onClickPopup,
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "더보기 아이콘",
                tint = NeeGongNaeGongTheme.colorScheme.primaryText,
            )
        }
        DropdownMenu(
            expanded = showPopup,
            onDismissRequest = onDismissPopup,
            containerColor = NeeGongNaeGongTheme.colorScheme.background,
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally),
                        text = "삭제하기",
                        style = NeeGongNaeGongTheme.typography.labelMedium,
                        color = NeeGongNaeGongTheme.colorScheme.primaryText,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                contentPadding = PaddingValues(0.dp),
                onClick = onClickDeleteMenu,
            )
            DropdownMenuItem(
                text = {
                    Text(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally),
                        text = "수정하기",
                        style = NeeGongNaeGongTheme.typography.labelMedium,
                        color = NeeGongNaeGongTheme.colorScheme.primaryText,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                contentPadding = PaddingValues(0.dp),
                onClick = onClickEditMenu,
            )
        }
    }
}

@Composable
@NeeGongNaeGongPreviews
private fun PreviewPopupMenu() {
    NeeGongNaeGongTheme {
        PopupMenu(
            Modifier.fillMaxSize(),
            true,
            {},
            {},
            {},
            {},
        )
    }
}
