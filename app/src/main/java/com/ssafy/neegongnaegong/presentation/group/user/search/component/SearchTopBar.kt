package com.ssafy.neegongnaegong.presentation.group.user.search.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun SearchTopBar(popBackStack: () -> Unit) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .size(24.dp)
                    .clickable(onClick = popBackStack)
                    .align(Alignment.CenterStart),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "뒤로 가기",
                tint = NeeGongNaeGongTheme.colorScheme.gray4,
            )
        }

        Box(
            modifier =
                Modifier.align(Alignment.Center),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "검색",
                style =
                    NeeGongNaeGongTheme.typography.titleLarge.copy(
                        fontSize = 20.sp,
                        fontFeatureSettings = "tnum",
                        color = NeeGongNaeGongTheme.colorScheme.primaryText,
                    ),
            )
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
fun SearchTopBarPreview() {
    NeeGongNaeGongTheme {
        SearchTopBar {}
    }
}
