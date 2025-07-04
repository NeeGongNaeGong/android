package com.ssafy.neegongnaegong.presentation.group.component.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.domain.model.studygroup.MyStudyGroupInfo
import com.ssafy.neegongnaegong.presentation.group.component.drawer.component.ErrorItem
import com.ssafy.neegongnaegong.presentation.group.component.drawer.component.LoadingItem
import com.ssafy.neegongnaegong.presentation.group.component.drawer.component.NoDataItem
import com.ssafy.neegongnaegong.presentation.group.component.drawer.model.Role
import com.ssafy.neegongnaegong.presentation.group.detail.StudiesDetailContract
import com.ssafy.neegongnaegong.presentation.group.detail.StudiesDetailViewModel
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate

@Composable
fun StudiesDrawerContent(
    navBackStackEntry: NavBackStackEntry,
    viewModel: StudiesDetailViewModel = hiltViewModel(navBackStackEntry),
    navigateTodStudiesEdit: (Role) -> Unit,
    navigateToStudiesMembersRole: (Role) -> Unit,
    navigateToStudiesApplications: (Role) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val myStudyList = viewModel.myStudyList.collectAsLazyPagingItems()
    val role = Role.MANAGER
    StudiesDrawer(
        headerImageUrl = uiState.value.studies.studyInfo.profileImg,
        name = uiState.value.studies.studyInfo.name,
        description = uiState.value.studies.studyInfo.description,
        role = role,
        myStudyList = myStudyList,
        onStudyDeleteClick = {
            viewModel.setEvent(
                StudiesDetailContract.Event.OndDeleteStudies(uiState.value.studies.id),
            )
        },
        navigateTodStudiesEdit = { navigateTodStudiesEdit(role) },
        navigateToStudiesMembersRole = { navigateToStudiesMembersRole(role) },
        navigateToStudiesApplications = { navigateToStudiesApplications(role) },
    )
}

@Composable
private fun StudiesDrawer(
    modifier: Modifier = Modifier,
    headerImageUrl: String? = null,
    name: String = "",
    description: String = "",
    role: Role,
    myStudyList: LazyPagingItems<MyStudyGroupInfo>,
    onMyStudyClick: () -> Unit = {},
    onStudyDeleteClick: () -> Unit = {},
    navigateTodStudiesEdit: () -> Unit = {},
    navigateToStudiesMembersRole: () -> Unit = {},
    navigateToStudiesApplications: () -> Unit = {},
) {
    LazyColumn(
        modifier =
            modifier
                .fillMaxHeight()
                .background(NeeGongNaeGongTheme.colorScheme.background),
    ) {
        item {
            GlideImage(
                imageModel = { headerImageUrl ?: R.drawable.img_main_character },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(240.dp),
                loading = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                },
                failure = {
                    GlideImage(
                        imageModel = { R.drawable.img_main_character },
                        modifier = Modifier.fillMaxSize(),
                    )
                },
            )
        }
        item {
            // 그룹 정보 부분
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(color = NeeGongNaeGongTheme.colorScheme.gray2),
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        modifier = Modifier.padding(bottom = 10.dp),
                        text = name,
                        style = NeeGongNaeGongTheme.typography.titleMedium,
                        color = NeeGongNaeGongTheme.colorScheme.primaryText,
                    )
                    Text(
                        text = description,
                        style = NeeGongNaeGongTheme.typography.bodyMedium,
                        color = NeeGongNaeGongTheme.colorScheme.primaryText,
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
        }

        item {
            // 그룹 관리 섹션
            DrawerMenuItem(
                icon = R.drawable.ic_studies_drw_group_management,
                title = stringResource(R.string.studies_drw_group_management),
                onClick = navigateTodStudiesEdit,
            )
        }

        item {
            // 멤버 관리 섹션
            DrawerMenuItem(
                icon = R.drawable.ic_studies_drw_member_management,
                title = stringResource(R.string.studies_drw_member_management),
                onClick = navigateToStudiesMembersRole,
            )
        }

        if (role == Role.LEADER) {
            item {
                // 스터디 삭제 버튼
                DrawerMenuItem(
                    icon = R.drawable.ic_studies_drw_studies_delete,
                    title = stringResource(R.string.studies_drw_studies_delete),
                    onClick = onStudyDeleteClick,
                )
            }
        }

        item {
            // 스터디 가입신청 현황
            DrawerMenuItem(
                icon = R.drawable.ic_studies_drw_studies_applications,
                title = stringResource(R.string.studies_drw_studies_application),
                onClick = navigateToStudiesApplications,
            )
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
        }

        item {
            HorizontalDivider(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                thickness = 1.dp,
            )
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
        }

        item {
            // 내 스터디 섹션
            // TODO : 내 스터디 수 추가
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.studies_drw_my_studies),
                    style = NeeGongNaeGongTheme.typography.titleSmall,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
                Text(
                    text = stringResource(R.string.studies_drw_see_more),
                    style = NeeGongNaeGongTheme.typography.bodySmall,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                    modifier = Modifier.clickable { onMyStudyClick() },
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
        }

        item {
            // 스터디 아이콘들
            LazyRow(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                items(
                    count = myStudyList.itemCount,
                    key = myStudyList.itemKey(MyStudyGroupInfo::id),
                ) { idx ->
                    myStudyList[idx]?.let { item ->
                        GlideImage(
                            imageModel = item::profileImg,
                            loading = { CircularProgressIndicator() },
                            modifier =
                                Modifier
                                    .size(67.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                    },
                            imageOptions =
                                ImageOptions(
                                    contentScale = ContentScale.Crop,
                                    alignment = Alignment.Center,
                                ),
                            requestOptions = { RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL) },
                            failure = {
                                // 이미지 로드 실패 시 플레이스홀더
                                Image(
                                    painter = painterResource(id = R.drawable.img_default_profile),
                                    contentDescription = "Profile Image",
                                    modifier =
                                        Modifier
                                            .size(100.dp)
                                            .clip(CircleShape),
                                    contentScale = ContentScale.Crop,
                                )
                            },
                        )
                    }
                }
                when {
                    myStudyList.loadState.refresh is LoadState.Loading -> item { LoadingItem() }
                    myStudyList.loadState.append is LoadState.Loading -> item { LoadingItem() }
                    myStudyList.loadState.refresh is LoadState.Error ->
                        item {
                            val e = myStudyList.loadState.refresh as LoadState.Error
                            ErrorItem(
                                e.error.localizedMessage.orEmpty(),
                            ) { myStudyList.retry() }
                        }

                    myStudyList.loadState.append is LoadState.Error ->
                        item {
                            val e = myStudyList.loadState.append as LoadState.Error
                            ErrorItem(
                                e.error.localizedMessage.orEmpty(),
                            ) { myStudyList.retry() }
                        }

                    myStudyList.itemCount == 0 ->
                        item {
                            NoDataItem()
                        }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
        }

        item {
            HorizontalDivider(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                thickness = 1.dp,
            )
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun PreviewLightModeStudiesDrawer() {
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
                    ),
                )
            }
        }

    val pagingData = PagingData.from(sampleItems)
    val lazyItems = MutableStateFlow(pagingData).collectAsLazyPagingItems()

    NeeGongNaeGongTheme {
        StudiesDrawer(
            modifier = Modifier.fillMaxWidth(0.75F),
            name = "화이트 스터디",
            description = "하얗습니다.",
            role = Role.MANAGER,
            myStudyList = lazyItems,
        )
    }
}
