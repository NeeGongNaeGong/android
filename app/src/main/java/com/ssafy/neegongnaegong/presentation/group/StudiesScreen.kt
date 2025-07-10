package com.ssafy.neegongnaegong.presentation.group

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.domain.model.studygroup.MyStudyGroupInfo
import com.ssafy.neegongnaegong.presentation.component.TopAppBar
import com.ssafy.neegongnaegong.presentation.component.TopAppBarNavigationType
import com.ssafy.neegongnaegong.presentation.group.component.StudiesWindow
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.noRippleClickable
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun StudiesRoute(
    modifier: Modifier = Modifier,
    viewModel: StudiesViewModel = hiltViewModel(),
    navigateToStudiesDetail: (Long) -> Unit,
    navigateToStudiesFind: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val myStudyList = viewModel.myStudyList.collectAsLazyPagingItems()
    LaunchedEffect(Unit) {
        viewModel.setEvent(StudiesContract.Event.OnLoadMyStudies)
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is StudiesContract.Effect.NavigateToStudiesDetail ->
                    navigateToStudiesDetail(effect.studyGroupId)

                is StudiesContract.Effect.NavigateToStudiesSearch ->
                    navigateToStudiesFind()
            }
        }
    }

    StudiesContent(
        modifier = modifier,
        uiState = uiState,
        myStudyList = myStudyList,
        onClickMyStudies = { studyGroupId ->
            viewModel.setEvent(
                StudiesContract.Event.OnClickMyStudies(studyGroupId),
            )
        },
        onClickStudiesFind = {
            viewModel.setEvent(StudiesContract.Event.OnClickSearchStudies)
        },
    )
}

@Composable
private fun StudiesContent(
    modifier: Modifier = Modifier,
    uiState: StudiesContract.State,
    myStudyList: LazyPagingItems<MyStudyGroupInfo>,
    onClickMyStudies: (Long) -> Unit,
    onClickStudiesFind: () -> Unit,
) {
    StudiesScreen(
        modifier = modifier,
        isLoading = uiState.isLoading,
        myStudyList = myStudyList,
        onClickMyStudies = onClickMyStudies,
        onClickStudiesFind = onClickStudiesFind,
    )
}

@Composable
private fun StudiesScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    myStudyList: LazyPagingItems<MyStudyGroupInfo>,
    onClickMyStudies: (Long) -> Unit,
    onClickStudiesFind: () -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "가입한 스터디",
                    style = NeeGongNaeGongTheme.typography.bodyMedium,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
            },
            navigationType = TopAppBarNavigationType.None,
            actionButtons = {
                Box {
                    Icon(
                        modifier =
                            Modifier
                                .noRippleClickable { onClickStudiesFind() }
                                .padding(8.dp),
                        // 클릭 영역을 더 크게 만들기 위한 패딩
                        painter = painterResource(R.drawable.ic_topbar_serach),
                        tint = NeeGongNaeGongTheme.colorScheme.primaryText,
                        contentDescription = "스터디 검색",
                    )
                }
            },
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(
                count = myStudyList.itemCount,
                key = myStudyList.itemKey(MyStudyGroupInfo::id),
            ) { idx ->
                myStudyList[idx]?.let { studies ->
                    StudiesWindow(
                        modifier =
                            Modifier
                                .padding(horizontal = 12.dp)
                                .padding(bottom = 8.dp)
                                .noRippleClickable {
                                    onClickMyStudies(studies.id)
                                },
                        category = studies.category.name,
                        isPublic = studies.isPublic,
                        name = studies.name,
                        targetStudyTime = studies.targetStudyTime,
                        currentMembers = studies.currentMembers,
                        maxMembers = studies.maxMembers,
                        leader = studies.leader.name,
                        createdDate = studies.createdDate.toString(),
                        profileImageUrl = studies.profileImg,
                    )
                }
            }

            if (isLoading) {
                item {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(
                            strokeWidth = 4.dp,
                            color = NeeGongNaeGongTheme.colorScheme.blue,
                        )
                    }
                }
            }
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun PreviewStudiesScreen() {
    val sampleItems =
        mutableListOf<MyStudyGroupInfo>().apply {
            for (i in 0..4) {
                add(
                    MyStudyGroupInfo(
                        id = i.toLong(),
                        leader =
                            MyStudyGroupInfo.LeaderInfo(
                                id = i.toLong(),
                                name = "이름",
                            ),
                        name = "Araceli McLaughlin",
                        maxMembers = 1905,
                        currentMembers = 6511,
                        description = "leo",
                        profileImg = "inimicus",
                        isPublic = false,
                        targetStudyTime = 4273,
                        category =
                            MyStudyGroupInfo.CategoryInfo(
                                id = i.toLong(),
                                name = "Raymond Frank",
                            ),
                        createdDate = LocalDate.now(),
                        tags = listOf(),
                        cursorCreatedAt = LocalDateTime.now(),
                        cursorId = 0L,
                    ),
                )
            }
        }
    val pagingData = PagingData.from(sampleItems)
    val lazyItems = MutableStateFlow(pagingData).collectAsLazyPagingItems()

    NeeGongNaeGongTheme {
        StudiesScreen(
            isLoading = false,
            myStudyList = lazyItems,
            onClickMyStudies = {},
            onClickStudiesFind = {},
        )
    }
}
