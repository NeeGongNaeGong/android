package com.ssafy.neegongnaegong.presentation.group

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.domain.model.studies.StudyInfo
import com.ssafy.neegongnaegong.domain.model.studies.StudyMember
import com.ssafy.neegongnaegong.presentation.component.TopAppBar
import com.ssafy.neegongnaegong.presentation.component.TopAppBarNavigationType
import com.ssafy.neegongnaegong.presentation.group.component.StudiesCard
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.noRippleClickable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun StudiesRoute(
    modifier: Modifier = Modifier,
    viewModel: StudiesViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    navigateToStudiesDetail: (Long) -> Unit,
    navigateToStudiesManagement: () -> Unit,
) {
    BackHandler {
        popBackStack()
    }

    val uiState = viewModel.uiState.collectAsState().value

    StudiesContent(
        modifier = modifier,
        uiState = uiState,
        effect = viewModel.effect,
        navigateToStudiesDetail = navigateToStudiesDetail,
        navigateToStudiesManagement = navigateToStudiesManagement,
    )
}

@Composable
fun StudiesContent(
    modifier: Modifier = Modifier,
    uiState: StudiesContract.State,
    effect: Flow<StudiesContract.Effect>,
    navigateToStudiesDetail: (Long) -> Unit,
    navigateToStudiesManagement: () -> Unit,
) {
    LaunchedEffect(effect) {
        effect.collectLatest { effect ->
            // TODO : effect 처리
            when (effect) {
                is StudiesContract.Effect.ShowStudies -> {
                }

                is StudiesContract.Effect.NavigateToGroupDetail -> {
                }
            }
        }
    }

    StudiesScreen(
        modifier = modifier,
        studiesState = uiState.studiesState,
        navigateToStudiesDetail = navigateToStudiesDetail,
        navigateToStudiesManagement = navigateToStudiesManagement,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudiesScreen(
    modifier: Modifier = Modifier,
    studiesState: StudiesContract.StudiesState,
    navigateToStudiesDetail: (Long) -> Unit,
    navigateToStudiesManagement: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        TopAppBar(
            title = {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "스터디 목록",
                    style = NeeGongNaeGongTheme.typography.bodyMedium,
                )
            },
            navigationType = TopAppBarNavigationType.None,
            actionButtons = {
                Box {
                    Icon(
                        modifier =
                            Modifier
                                .noRippleClickable {
                                    navigateToStudiesDetail(-1)
                                    // 여기에 클릭 시 실행할 코드 작성
                                }.padding(8.dp),
                        // 클릭 영역을 더 크게 만들기 위한 패딩
                        painter = painterResource(R.drawable.ic_topbar_serach),
                        tint = null,
                        contentDescription = "Search",
                    )
                }
            },
        )
        when (studiesState) {
            is StudiesContract.StudiesState.Idle -> {
                // Nothing to show yet
                Box(modifier = Modifier.fillMaxSize()) {
                    FloatingActionButton(
                        onClick = { navigateToStudiesManagement() },
                        modifier =
                            Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp),
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_studies_fab_plus),
                            contentDescription = "새 스터디 만들기",
                        )
                    }
                }
            }

            is StudiesContract.StudiesState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is StudiesContract.StudiesState.Success -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(studiesState.studiesList) { studies ->
                            StudiesCard(
                                modifier = Modifier.padding(vertical = 4.dp),
                                category = studies.studyInfo.category?.name ?: "없음",
                                title = studies.studyInfo.name,
                                goalTime = studies.studyInfo.targetStudyTime.toString(),
                                memberInfo = studies.currentMembers.toString(),
                                leader = studies.leader.name,
                                startInfo = "",
                                description = studies.studyInfo.description,
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(80.dp))
                        }
                    }
                    FloatingActionButton(
                        onClick = { navigateToStudiesManagement() },
                        modifier =
                            Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp),
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_studies_fab_plus),
                            contentDescription = "새 스터디 만들기",
                        )
                    }
                }
            }

            is StudiesContract.StudiesState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = studiesState.message,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun PreviewStudiesScreen() {
    val mockGroups =
        listOf(
            Studies(
                id = 7128,
                leader =
                    StudyMember(
                        id = 2153,
                        name = "Rodney Gallegos",
                    ),
                currentMembers = 2177,
                createdDate = "interdum",
                studyInfo =
                    StudyInfo(
                        name = "Jeffrey Britt",
                        maxMembers = 9071,
                        description = "voluptatibus",
                        profileImg = null,
                        isPublic = false,
                        targetStudyTime = 4805,
                        category = null,
                        tags = listOf(),
                    ),
            ),
        )

    NeeGongNaeGongTheme {
        StudiesScreen(
            studiesState = StudiesContract.StudiesState.Success(mockGroups),
            navigateToStudiesDetail = {},
            navigateToStudiesManagement = {},
        )
    }
}
