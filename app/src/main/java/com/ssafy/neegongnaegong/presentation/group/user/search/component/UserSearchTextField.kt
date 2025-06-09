package com.ssafy.neegongnaegong.presentation.group.user.search.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun UserSearchTextField(
    modifier: Modifier = Modifier,
    content: String,
    onContentChanged: (String) -> Unit,
) {
    TextField(
        modifier = modifier,
        value = content,
        onValueChange = onContentChanged,
        textStyle =
            NeeGongNaeGongTheme.typography.labelLarge.copy(
                fontSize = 18.sp,
                fontFeatureSettings = "tnum",
            ),
        placeholder = {
            Text(
                modifier = Modifier.padding(bottom = 2.dp),
                text = "유저 닉네임",
                fontSize = 18.sp,
                color = NeeGongNaeGongTheme.colorScheme.gray4,
                letterSpacing = 0.2.sp,
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "검색 아이콘",
                tint = NeeGongNaeGongTheme.colorScheme.gray4,
            )
        },
        trailingIcon = {
            if (content.isNotEmpty()) {
                IconButton(onClick = { onContentChanged("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "지우기",
                        tint = NeeGongNaeGongTheme.colorScheme.gray4,
                    )
                }
            }
        },
        maxLines = 5,
        colors =
            TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = NeeGongNaeGongTheme.colorScheme.gray4,
                unfocusedIndicatorColor = NeeGongNaeGongTheme.colorScheme.gray4,
                disabledIndicatorColor = NeeGongNaeGongTheme.colorScheme.gray3,
                cursorColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                selectionColors =
                    TextSelectionColors(
                        handleColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                        backgroundColor = NeeGongNaeGongTheme.colorScheme.gray3,
                    ),
            ),
    )
}

@NeeGongNaeGongPreviews
@Composable
fun UserSearchTextFieldPreview() {
    NeeGongNaeGongTheme {
        UserSearchTextField(content = "as", onContentChanged = {})
    }
}
