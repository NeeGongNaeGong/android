package com.ssafy.neegongnaegong.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.noRippleClickable

/**
 * 커스텀 `TopAppBar` 컴포넌트
 *
 * @param modifier
 * @param title : 타이틀 Composable 함수
 * @param navigationType : 내비게이션 타입 `default`는 `Back`
 * @param actionButtons : 끝 버튼 ex) 검색 버튼
 * @param onNavigationClick : icon 클릭 동작 ex) 뒤로 가기
 */
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    navigationType: TopAppBarNavigationType = TopAppBarNavigationType.Back,
    actionButtons: @Composable () -> Unit = {},
    onNavigationClick: () -> Unit = {},
    iconColor: Color = NeeGongNaeGongTheme.colorScheme.primaryText,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .pointerInput(Unit) {}
                .then(modifier),
        contentAlignment = Alignment.Center,
    ) {
        if (navigationType != TopAppBarNavigationType.None) {
            Icon(
                modifier =
                    Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 10.dp)
                        .noRippleClickable {
                            onNavigationClick()
                        },
                painter =
                    painterResource(
                        id =
                            when (navigationType) {
                                TopAppBarNavigationType.Back -> R.drawable.ic_topbar_back
                                else -> R.drawable.ic_topbar_menu_burger
                            },
                    ),
                contentDescription = "",
                tint = iconColor,
            )
        }
        Row(Modifier.align(Alignment.Center)) {
            title()
        }
        Row(
            Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp),
        ) {
            actionButtons()
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewTopAppBar() {
    NeeGongNaeGongTheme {
        TopAppBar(
            title = {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "제목을 넣어주세요",
                    style = MaterialTheme.typography.titleMedium,
                )
            },
            actionButtons = {},
        )
    }
}

enum class TopAppBarNavigationType { Back, Menu, None }
