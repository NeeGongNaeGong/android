package com.ssafy.neegongnaegong.presentation.group.list.vote

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ssafy.neegongnaegong.domain.model.studygroup.VoteHistoryInfo
import com.ssafy.neegongnaegong.presentation.group.list.component.ErrorItem
import com.ssafy.neegongnaegong.presentation.group.list.component.From
import com.ssafy.neegongnaegong.presentation.group.list.component.LoadingItem
import com.ssafy.neegongnaegong.presentation.group.list.component.NoDataItem
import com.ssafy.neegongnaegong.presentation.group.list.vote.component.VoteCard
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDateTime

@Composable
fun VoteListRoute(
    lazyItems: Flow<PagingData<VoteHistoryInfo>>,
    onClickVoteItem: (Long) -> Unit,
) {
    val pagingItem = lazyItems.collectAsLazyPagingItems()
    VoteListScreen(lazyItems = pagingItem, onClickVoteItem)
}

@Composable
fun VoteListScreen(
    lazyItems: LazyPagingItems<VoteHistoryInfo>,
    onClick: (Long) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 12.dp),
    ) {
        items(lazyItems.itemCount) { idx ->
            lazyItems[idx]?.let { item ->
                VoteCard(
                    modifier = Modifier.fillMaxWidth(),
                    id = item.id,
                    title = item.title,
                    participationMember = item.participationMember,
                    voted = item.voted,
                    endTime = item.endTime,
                    onClick = onClick,
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
                    NoDataItem(From.Vote)
                }
        }
    }
}

@Composable
@NeeGongNaeGongPreviews
fun PreviewVoteListScreen() {
    val sampleItems =
        mutableListOf<VoteHistoryInfo>().apply {
            repeat(4) {
                add(
                    VoteHistoryInfo(
                        id = 0,
                        title = "테스트 공지입니다",
                        endTime = LocalDateTime.now(),
                        participationMember = 1,
                        voted = true,
                    ),
                )
            }
        }

    val pagingData = PagingData.from(sampleItems)
    val lazyItems = MutableStateFlow(pagingData).collectAsLazyPagingItems()

    NeeGongNaeGongTheme {
        VoteListScreen(lazyItems = lazyItems, {})
    }
}
