package com.ssafy.neegongnaegong.presentation.group.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.neegongnaegong.presentation.component.TopAppBar
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import okhttp3.internal.immutableListOf

@Composable
fun ListRoute(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit,
    viewModel: ListViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Column {
        TopAppBar(
            title = {
                Text(
                    style = NeeGongNaeGongTheme.typography.titleSmall,
                    text = "~~공부방",
                )
            },
            onNavigationClick = { popBackStack() },
            actionButtons = {
                IconButton(onClick = {
                    TODO("공지화면이냐, 투표화면이냐에 따라서 연필 아이콘을 눌렀을 때 각각의 탭에 맞는 생성 화면이 나타나도록")
                }) {
                    Icon(
                        imageVector = Icons.Filled.Create,
                        contentDescription = "추가 아이콘",
                    )
                }
            },
        )
        ListContent(modifier = modifier, state.index)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    index: Int,
) {
    val tabList = immutableListOf("공지", "투표")
    SecondaryTabRow(
        modifier = modifier,
        selectedTabIndex = index,
        indicator = {
            TabRowDefaults.SecondaryIndicator(
                modifier =
                    Modifier
                        .tabIndicatorOffset(index, false),
                color = NeeGongNaeGongTheme.colorScheme.primaryText,
            )
        },
    ) {
        tabList.forEachIndexed { tabListIndex, item ->
            Tab(
                modifier = Modifier.background(NeeGongNaeGongTheme.colorScheme.background),
                selectedContentColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                unselectedContentColor = NeeGongNaeGongTheme.colorScheme.secondaryText,
                selected = index == tabListIndex,
                onClick = {},
                text = {
                    Text(
                        text = item,
                        style = NeeGongNaeGongTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
            )
        }
    }
}

@Composable
@NeeGongNaeGongPreviews
fun ListContentPreview() {
    NeeGongNaeGongTheme {
        ListContent(index = 0)
    }
}
