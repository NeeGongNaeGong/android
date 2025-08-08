package com.ssafy.neegongnaegong.presentation.group.find.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    content: String,
    onContentChanged: (String) -> Unit,
    onSearch: () -> Unit,
) {
    TextField(
        modifier =
            modifier
                .scale(0.9f)
                .clip(RoundedCornerShape(10.dp)),
        value = content,
        onValueChange = onContentChanged,
        textStyle =
            NeeGongNaeGongTheme.typography.labelLarge.copy(
                fontSize = 18.sp,
                color = NeeGongNaeGongTheme.colorScheme.primaryText,
            ),
        placeholder = {
            Text(
                modifier = Modifier.padding(bottom = 2.dp),
                text = "검색",
                fontSize = 18.sp,
                color = NeeGongNaeGongTheme.colorScheme.gray4,
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
        keyboardOptions =
            KeyboardOptions(
                imeAction = ImeAction.Search,
            ),
        keyboardActions =
            KeyboardActions(
                onSearch = { onSearch() },
            ),
        maxLines = 1,
        colors =
            TextFieldDefaults.colors(
                focusedContainerColor = NeeGongNaeGongTheme.colorScheme.gray2,
                unfocusedContainerColor = NeeGongNaeGongTheme.colorScheme.gray2,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = NeeGongNaeGongTheme.colorScheme.mintBlue,
                selectionColors =
                    TextSelectionColors(
                        handleColor = NeeGongNaeGongTheme.colorScheme.mintBlue,
                        backgroundColor = NeeGongNaeGongTheme.colorScheme.gray3,
                    ),
            ),
    )

    HorizontalDivider(
        modifier =
            Modifier
                .padding(bottom = 2.dp, start = 12.dp, end = 12.dp)
                .fillMaxWidth(),
        color = NeeGongNaeGongTheme.colorScheme.gray4,
    )
}

@NeeGongNaeGongPreviews
@Composable
private fun UserSearchTextFieldPreview() {
    NeeGongNaeGongTheme {
        SearchTextField(
            content = "as",
            onContentChanged = {},
            onSearch = {},
        )
    }
}
