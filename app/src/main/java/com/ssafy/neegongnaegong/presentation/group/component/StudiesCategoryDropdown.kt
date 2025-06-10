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
import com.ssafy.neegongnaegong.domain.model.studies.Category
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun StudiesCategoryDropdown(
    modifier: Modifier,
    selectedCategory: Category?,
    categories: List<Category>,
    onCategorySelected: (Category) -> Unit,
) {
    var categoryDropdown by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        OutlinedCard(
            modifier =
                Modifier
                    .width(120.dp)
                    .clickable { categoryDropdown = true },
            shape = RoundedCornerShape(8.dp),
            colors =
                CardColors(
                    containerColor = NeeGongNaeGongTheme.colorScheme.background,
                    contentColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                    disabledContainerColor = Color.Red,
                    disabledContentColor = Color.Blue,
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
                    text = selectedCategory?.name ?: "선택",
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
            expanded = categoryDropdown,
            onDismissRequest = { categoryDropdown = false },
            modifier =
                Modifier
                    .width(120.dp)
                    .heightIn(max = 200.dp)
                    .background(color = NeeGongNaeGongTheme.colorScheme.gray2),
        ) {
            for (category in categories) {
                DropdownMenuItem(
                    text = { Text(category.name) },
                    onClick = {
                        onCategorySelected(category)
                        categoryDropdown = false
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
private fun PreviewStudiesCategoryDropdown() {
    StudiesCategoryDropdown(
        modifier = Modifier,
        selectedCategory = null,
        categories =
            listOf(
                Category(id = 1, name = "AA"),
                Category(id = 2, name = "BB"),
                Category(id = 3, name = "CC"),
            ),
        onCategorySelected = { },
    )
}
