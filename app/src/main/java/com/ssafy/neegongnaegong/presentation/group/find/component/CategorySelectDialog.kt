package com.ssafy.neegongnaegong.presentation.group.find.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ssafy.neegongnaegong.domain.model.studies.Category
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategorySelectDialog(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    initialSelectedCategories: Set<Category>,
    onConfirm: (Set<Category>) -> Unit,
    onCancel: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val groupedCategories =
        remember(categories) {
            categories.groupBy { getKoreanConsonant(it.name.first()) }
        }

    var selectCategories by remember { mutableStateOf(initialSelectedCategories) }
    Dialog(onDismissRequest = { onCancel.invoke() }) {
        Box(
            modifier =
                modifier
                    .fillMaxWidth()
                    .background(
                        NeeGongNaeGongTheme.colorScheme.background,
                        shape = RoundedCornerShape(12.dp),
                    ),
        ) {
            Column(
                modifier = Modifier.padding(top = 20.dp, start = 10.dp, end = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "카테고리 선택",
                    style = NeeGongNaeGongTheme.typography.bodyLarge,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
                LazyColumn(
                    modifier =
                        Modifier
                            .heightIn(max = screenHeight * 0.5f)
                            .padding(horizontal = 4.dp),
                ) {
                    groupedCategories.forEach { (consonant, items) ->
                        // 초성 헤더 (스크롤 시 상단에 고정됨)
                        stickyHeader {
                            Text(
                                text = consonant.toString(),
                                style = NeeGongNaeGongTheme.typography.bodyMedium,
                                color = NeeGongNaeGongTheme.colorScheme.secondaryText,
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                            )
                        }

                        // 해당 초성에 속한 카테고리 목록
                        items(items, key = { it.id }) { category ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            val newSet = selectCategories.toMutableSet()
                                            if (category in newSet) {
                                                newSet.remove(category)
                                            } else {
                                                newSet.add(category)
                                            }
                                            selectCategories = newSet
                                        }
                                        .padding(vertical = 4.dp, horizontal = 8.dp),
                            ) {
                                Checkbox(
                                    checked = category in selectCategories,
                                    onCheckedChange = { isChecked ->
                                        val newSet = selectCategories.toMutableSet()
                                        if (isChecked) {
                                            newSet.add(category)
                                        } else {
                                            newSet.remove(category)
                                        }
                                        selectCategories = newSet
                                    },
                                    colors =
                                        CheckboxDefaults.colors(
                                            checkedColor = NeeGongNaeGongTheme.colorScheme.blue,
                                            uncheckedColor = NeeGongNaeGongTheme.colorScheme.secondaryText,
                                            checkmarkColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                                        ),
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    modifier = Modifier,
                                    text = category.name,
                                    style = NeeGongNaeGongTheme.typography.bodyMedium,
                                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider(thickness = 0.4.dp, color = Color.Gray)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    TextButton(
                        onClick = { selectCategories = emptySet() },
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            text = "초기화",
                            style = NeeGongNaeGongTheme.typography.labelLarge,
                            color = NeeGongNaeGongTheme.colorScheme.peach,
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    VerticalDivider(
                        thickness = 0.4.dp,
                        color = Color.Gray,
                        modifier = Modifier.height(50.dp),
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(
                        onClick = { onConfirm(selectCategories) },
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            text = "적용",
                            style = NeeGongNaeGongTheme.typography.labelLarge,
                            color = NeeGongNaeGongTheme.colorScheme.blue,
                        )
                    }
                }
            }
        }
    }
}

// 한글 초성 추출 헬퍼 함수
private val KOREAN_CONSONANTS =
    charArrayOf(
        'ㄱ',
        'ㄲ',
        'ㄴ',
        'ㄷ',
        'ㄸ',
        'ㄹ',
        'ㅁ',
        'ㅂ',
        'ㅃ',
        'ㅅ',
        'ㅆ',
        'ㅇ',
        'ㅈ',
        'ㅉ',
        'ㅊ',
        'ㅋ',
        'ㅌ',
        'ㅍ',
        'ㅎ',
    )

private fun getKoreanConsonant(char: Char): Char {
    if (char !in '가'..'힣') return char // 한글이 아니면 원래 문자 반환
    return KOREAN_CONSONANTS[(char.code - 0xAC00) / (21 * 28)]
}

@Composable
@NeeGongNaeGongPreviews
private fun PreviewCategorySelectDialog() {
    NeeGongNaeGongTheme {
        CategorySelectDialog(
            categories =
                listOf(
                    Category(1, "가가"),
                    Category(2, "나나"),
                    Category(3, "라라"),
                    Category(4, "라마"),
                ),
            initialSelectedCategories = setOf(Category(1, "가가")),
            onCancel = {},
            onConfirm = {},
        )
    }
}
