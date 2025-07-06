package com.ssafy.neegongnaegong.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.domain.model.learning.Tag
import com.ssafy.neegongnaegong.domain.model.preview.personal.PersonalPreviewDataProvider
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagList(
    modifier: Modifier = Modifier,
    tags: List<Tag>,
    onTagPlusClicked: () -> Unit,
    onTagEraseClicked: (Tag) -> Unit,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        tags.forEach { tag ->
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = NeeGongNaeGongTheme.colorScheme.blue,
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = tag.koName,
                        fontSize = 16.sp,
                        color = Color.White,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        modifier =
                            Modifier
                                .size(16.dp)
                                .clickable { onTagEraseClicked(tag) },
                        imageVector = Icons.Default.Close,
                        contentDescription = "삭제",
                        tint = Color.White,
                    )
                }
            }
        }

        Surface(
            modifier =
                Modifier
                    .size(32.dp)
                    .align(Alignment.CenterVertically)
                    .clickable { onTagPlusClicked() },
            shape = RoundedCornerShape(4.dp),
            color = NeeGongNaeGongTheme.colorScheme.blue,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "태그 추가",
                    tint = Color.White,
                )
            }
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
fun TagListPreview() {
    NeeGongNaeGongTheme {
        TagList(
            modifier = Modifier.padding(16.dp),
            tags = PersonalPreviewDataProvider().getTags(),
            onTagPlusClicked = {},
            onTagEraseClicked = {},
        )
    }
}
