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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.presentation.component.TopAppBar
import com.ssafy.neegongnaegong.presentation.component.TopAppBarNavigationType
import com.ssafy.neegongnaegong.presentation.group.component.StudiesCard
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.ui.theme.Typography
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

                is StudiesContract.Effect.ShowToast -> {
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
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier,
        ) {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(vertical = 10.dp),
                        text = "스터디 목록",
                        style = Typography.bodyMedium,
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
                                    category = studies.category,
                                    title = studies.title,
                                    goalTime = studies.goalTime,
                                    memberInfo = studies.memberInfo,
                                    leader = studies.leader,
                                    startInfo = studies.startInfo,
                                    description = studies.description,
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
}

@Preview(showBackground = true)
@Composable
private fun PreviewStudiesScreen() {
    val mockGroups =
        listOf(
            Studies(
                id = 1L,
                category = "대학생",
                title = "개발, 코딩(프론트, 백엔드 등) 취준방",
                goalTime = "목표 3시간",
                memberInfo = "인원 3/20명",
                leader = "그룹장 박준식",
                startInfo = "시작일 2일 전",
                description =
                    "개발 취준을 준비하시는 취준생 분들을 위한 스터디 그룹입니다. 매일 함께 공부해요! 우리 같이해요 함께해요! 열심히 해볼까요!\n" +
                        "개발 취준을 준비하시는 취준생 분들을 위한 스터디 그룹입니다. 매일 함께 공부해요! 우리 같이해요 함께해요! 열심히 해볼까요!\n",
            ),
            Studies(
                id = 2L,
                category = "대학생",
                title = "토익 900점 목표 스터디",
                goalTime = "목표 2시간",
                memberInfo = "인원 5/15명",
                leader = "그룹장 김영희",
                startInfo = "시작일 1주일 전",
                description = "토익 900점을 목표로 하는 스터디 그룹입니다.",
            ),
            Studies(
                id = 2L,
                category = "대학생",
                title = "토익 900점 목표 스터디",
                goalTime = "목표 2시간",
                memberInfo = "인원 5/15명",
                leader = "그룹장 김영희",
                startInfo = "시작일 1주일 전",
                description = "토익 900점을 목표로 하는 스터디 그룹입니다.",
            ),
            Studies(
                id = 2L,
                category = "대학생",
                title = "토익 900점 목표 스터디",
                goalTime = "목표 2시간",
                memberInfo = "인원 5/15명",
                leader = "그룹장 김영희",
                startInfo = "시작일 1주일 전",
                description = "토익 900점을 목표로 하는 스터디 그룹입니다.",
            ),
            Studies(
                id = 2L,
                category = "대학생",
                title = "토익 900점 목표 스터디",
                goalTime = "목표 2시간",
                memberInfo = "인원 5/15명",
                leader = "그룹장 김영희",
                startInfo = "시작일 1주일 전",
                description = "토익 900점을 목표로 하는 스터디 그룹입니다.",
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
