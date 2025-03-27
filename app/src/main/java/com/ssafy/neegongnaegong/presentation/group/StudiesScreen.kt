package com.ssafy.neegongnaegong.presentation.group

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.neegongnaegong.domain.model.Studies
import com.ssafy.neegongnaegong.presentation.group.component.StudiesCard
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun StudiesRoute(
    modifier: Modifier = Modifier,
    viewModel: StudiesViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
) {
    BackHandler {
        popBackStack()
    }

    val uiState = viewModel.uiState.collectAsState().value

    StudiesContent(
        modifier = modifier,
        uiState = uiState,
        effect = viewModel.effect,
    )
}

@Composable
fun StudiesContent(
    modifier: Modifier = Modifier,
    uiState: StudiesContract.State,
    effect: Flow<StudiesContract.Effect>,
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
    )
}

@Composable
fun StudiesScreen(
    modifier: Modifier = Modifier,
    studiesState: StudiesContract.StudiesState,
) {
    Scaffold(
        topBar = {
            // 필요한 TopBar 구성
        },
    ) { paddingValues ->
        when (studiesState) {
            is StudiesContract.StudiesState.Idle -> {
                // Nothing to show yet
            }

            is StudiesContract.StudiesState.Loading -> {
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is StudiesContract.StudiesState.Success -> {
                LazyColumn(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
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
            }

            is StudiesContract.StudiesState.Error -> {
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
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
                description = "개발 취준을 준비하시는 취준생 분들을 위한 스터디 그룹입니다. 매일 함께 공부해요! 우리 같이해요 함께해요! 열심히 해볼까요!\n" +
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
        )

    NeeGongNaeGongTheme {
        StudiesScreen(
            studiesState = StudiesContract.StudiesState.Success(mockGroups),
        )
    }
}
