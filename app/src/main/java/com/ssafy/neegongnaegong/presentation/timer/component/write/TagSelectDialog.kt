package com.ssafy.neegongnaegong.presentation.timer.component.write

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ssafy.neegongnaegong.domain.model.write.Tag
import com.ssafy.neegongnaegong.presentation.ui.theme.LightColors.Blue
import com.ssafy.neegongnaegong.presentation.ui.theme.LightColors.Peach
import com.ssafy.neegongnaegong.presentation.ui.theme.Typography

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagSelectDialog(
    modifier: Modifier = Modifier,
    selectedTags: List<Tag> = emptyList(),
    unSelectedTags: List<Tag> = emptyList(),
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onTagSelected: (Tag) -> Unit,
    onTagDeselected: (Tag) -> Unit,
) {
    var query by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onCancel() }) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier.padding(top = 20.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "태그 선택",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = query,
                    onValueChange = {
                        query = it
                        onSearchQueryChanged(it)
                    },
                    placeholder = {
                        Text(
                            text = "검색어를 입력해주세요",
                            style = Typography.labelLarge
                        )
                    },
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "검색",
                                tint = Color.Gray
                            )
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.LightGray,
                        disabledIndicatorColor = Color.LightGray,
                    ),
                    textStyle = Typography.labelLarge.copy(fontSize = 18.sp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    (selectedTags + unSelectedTags).forEach { tag ->
                        val isSelected = tag in selectedTags
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                if (isSelected) onTagDeselected(tag)
                                else onTagSelected(tag)
                                onSearchQueryChanged(query)
                            },
                            label = { Text(tag.koName) },
                            leadingIcon = if (isSelected) {
                                { Icon(Icons.Default.Check, contentDescription = null) }
                            } else null
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                HorizontalDivider(thickness = 0.5.dp, color = Color.Gray)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = onCancel,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "취소",
                            style = Typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = Peach
                        )
                    }

                    VerticalDivider(
                        thickness = 0.5.dp,
                        color = Color.Gray,
                        modifier = Modifier
                            .height(50.dp)
                    )

                    TextButton(
                        onClick = onConfirm,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "확인",
                            style = Typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = Blue
                        )
                    }
                }
            }
        }
    }
}
