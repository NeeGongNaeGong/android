package com.ssafy.neegongnaegong.presentation.group.list.notice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ssafy.neegongnaegong.domain.model.studygroup.NoticeHistoryInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.Writer
import com.ssafy.neegongnaegong.presentation.group.list.component.ErrorItem
import com.ssafy.neegongnaegong.presentation.group.list.component.From
import com.ssafy.neegongnaegong.presentation.group.list.component.LoadingItem
import com.ssafy.neegongnaegong.presentation.group.list.component.NoDataItem
import com.ssafy.neegongnaegong.presentation.group.list.component.NoticeCard
import com.ssafy.neegongnaegong.presentation.group.list.notice.NoticeContract.Effect.NavigateToBackStack
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDateTime

@Composable
fun NoticeListRoute(
    backStackEntry: NavBackStackEntry,
    viewModel: NoticeViewModel = hiltViewModel(backStackEntry),
    popBackStack: () -> Boolean,
) {
    val lazyItems = viewModel.voteListFlow.collectAsLazyPagingItems()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest {
            when (it) {
                NavigateToBackStack -> {
                    popBackStack()
                }
            }
        }
    }

    NoticeListScreen(lazyItems = lazyItems)
}

@Composable
fun NoticeListScreen(lazyItems: LazyPagingItems<NoticeHistoryInfo>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 12.dp),
    ) {
        items(lazyItems.itemCount) { idx ->
            lazyItems[idx]?.let { item ->
                NoticeCard(
                    id = item.id,
                    createdAt = item.createdAt,
                    title = item.title,
                    writer = item.writer,
                    onClick = {},
                )
            }
        }

        when {
            lazyItems.loadState.refresh is LoadState.Loading -> item { LoadingItem() }
            lazyItems.loadState.append is LoadState.Loading -> item { LoadingItem() }
            lazyItems.loadState.refresh is LoadState.Error ->
                item {
                    val e = lazyItems.loadState.refresh as LoadState.Error
                    ErrorItem(e.error.localizedMessage.orEmpty()) { lazyItems.retry() }
                }

            lazyItems.loadState.append is LoadState.Error ->
                item {
                    val e = lazyItems.loadState.append as LoadState.Error
                    ErrorItem(e.error.localizedMessage.orEmpty()) { lazyItems.retry() }
                }

            lazyItems.itemCount == 0 ->
                item {
                    NoDataItem(From.Notice)
                }
        }
    }
}

@Composable
@NeeGongNaeGongPreviews
fun PreviewNoticeListScreen() {
    val sampleItems =
        mutableListOf<NoticeHistoryInfo>().apply {
            repeat(4) {
                add(
                    NoticeHistoryInfo(
                        id = 0,
                        title = "테스트 공지입니다",
                        createdAt = LocalDateTime.now(),
                        writer = Writer(0, "누구누구", "", ""),
                        contentPreview = "",
                        cursorId = 0,
                        modifiedAt = LocalDateTime.now(),
                    ),
                )
            }
        }

    val pagingData = PagingData.from(sampleItems)
    val lazyItems = MutableStateFlow(pagingData).collectAsLazyPagingItems()

    NeeGongNaeGongTheme {
        NoticeListScreen(lazyItems = lazyItems)
    }
}
