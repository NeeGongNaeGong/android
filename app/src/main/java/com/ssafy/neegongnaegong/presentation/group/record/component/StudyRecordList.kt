package com.ssafy.neegongnaegong.presentation.group.record.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyContentInfo
import com.ssafy.neegongnaegong.presentation.component.studyrecord.StudyRecordItem
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDateTime

@Composable
fun StudyRecordListBySlice(
    modifier: Modifier = Modifier,
    lazyItems: LazyPagingItems<StudyContentInfo>,
    onClick: (Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 12.dp)
    ) {
        items(lazyItems.itemCount) { idx ->
            lazyItems[idx]?.let { item ->
                StudyRecordItem(
                    record = item.toStudyRecord(),
                    onClick = onClick
                )
            }
        }

        when {
            lazyItems.loadState.refresh is LoadState.Loading -> item { LoadingItem() }
            lazyItems.loadState.append is LoadState.Loading -> item { LoadingItem() }
            lazyItems.loadState.refresh is LoadState.Error -> item {
                val e = lazyItems.loadState.refresh as LoadState.Error
                ErrorItem(e.error.localizedMessage.orEmpty()) { lazyItems.retry() }
            }
            lazyItems.loadState.append is LoadState.Error -> item {
                val e = lazyItems.loadState.append as LoadState.Error
                ErrorItem(e.error.localizedMessage.orEmpty()) { lazyItems.retry() }
            }
            lazyItems.itemCount == 0 -> item {
                NoDataItem()
            }
        }
    }
}

@Composable
fun LoadingItem() {
    Box(Modifier.fillMaxSize(), Alignment.Center) { CircularProgressIndicator() }
}

@Composable
fun ErrorItem(message: String, onRetry: () -> Unit) {
    Box(Modifier.fillMaxSize(), Alignment.Center) {
        Text("Error: $message", Modifier.clickable { onRetry() })
    }
}

@Composable
fun NoDataItem() {
    Box(Modifier.fillMaxSize(), Alignment.Center) {
        Text("아직 기록이 없습니다!")
    }
}

@NeeGongNaeGongPreviews
@Composable
fun StudyRecordListBySlicePreview() {
    val sampleItems = mutableListOf<StudyContentInfo>().apply {
        for (i in 0..3) {
            add(
                StudyContentInfo(
                    title = "Kotlin Basics",
                    learningRecordId = 1,
                    startAt = LocalDateTime.now(),
                    endAt = LocalDateTime.now(),
                    content = "Kotlin Basics",
                    tags = listOf(),
                    learningRecordCreatedAt = LocalDateTime.now(),
                    learningRecordModifiedAt = LocalDateTime.now(), /* 기타 필드 */
                )
            )
        }
    }

    val pagingData = PagingData.from(sampleItems)
    val lazyItems = MutableStateFlow(pagingData).collectAsLazyPagingItems()

    StudyRecordListBySlice(
        lazyItems = lazyItems,
        onClick = { id -> println("Clicked item with id: $id") }
    )
}
