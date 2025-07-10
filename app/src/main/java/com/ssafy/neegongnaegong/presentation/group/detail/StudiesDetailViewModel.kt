package com.ssafy.neegongnaegong.presentation.group.detail

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ssafy.neegongnaegong.domain.model.studygroup.MyStudyGroupInfo
import com.ssafy.neegongnaegong.domain.usecase.studies.DeleteStudiesUseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.GetStudiesFeedsUseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.GetStudiesLatestContentsUseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.GetStudiesWeeklyRankingsUseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.PatchStudiesLatestContentsReadStatusUseCase
import com.ssafy.neegongnaegong.domain.usecase.studygroup.GetMyStudyListUseCase
import com.ssafy.neegongnaegong.domain.usecase.studygroup.GetStudyGroupDetailUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.group.find.StudiesFindContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "StudiesDetailViewModel"

@HiltViewModel
class StudiesDetailViewModel
    @Inject
    constructor(
        private val getStudyGroupDetailUseCase: GetStudyGroupDetailUseCase,
        private val getStudiesFeedsUseCase: GetStudiesFeedsUseCase,
        private val getStudiesWeeklyRankingsUseCase: GetStudiesWeeklyRankingsUseCase,
        private val getStudiesLatestContentsUseCase: GetStudiesLatestContentsUseCase,
        private val patchStudiesLatestContentsReadStatusUseCase: PatchStudiesLatestContentsReadStatusUseCase,
        private val deleteStudiesUseCase: DeleteStudiesUseCase,
        private val getMyStudyListUseCase: GetMyStudyListUseCase,
    ) : BaseViewModel<StudiesDetailContract.Event, StudiesDetailContract.State, StudiesDetailContract.Effect>() {
        var myStudyList: Flow<PagingData<MyStudyGroupInfo>> = emptyFlow()

        override fun handleException(
            e: Throwable,
            errorContext: ErrorContext,
            retry: () -> Unit,
        ) {
            val error = errorContext as? StudiesFindContract.Error ?: return
            Log.d(TAG, "handleException: $error")
        }

        override fun createInitialState(): StudiesDetailContract.State = StudiesDetailContract.State()

        override fun handleEvent(event: StudiesDetailContract.Event) {
            when (event) {
                is StudiesDetailContract.Event.OnLoad -> onLoad(event.studyGroupId)
                is StudiesDetailContract.Event.OnLoadFeeds -> onLoadFeeds(event.studyGroupId)
                is StudiesDetailContract.Event.OnLoadWeeklyRankings -> onLoadWeeklyRankings(event.studyGroupId)
                is StudiesDetailContract.Event.OnLoadLatestContents -> onLoadLatestContents(event.studyGroupId)
                is StudiesDetailContract.Event.OndDeleteStudies -> deleteStudies(event.studyGroupId)
                is StudiesDetailContract.Event.OnClickLatestNotice -> {
                    setEffect {
                        StudiesDetailContract.Effect.NavigateToLatestNoticeDetail(
                            event.noticeId,
                        )
                    }
                    readContents(studyGroupId = uiState.value.studyGroupDetailInfo.id, readNotice = true)
                }

                is StudiesDetailContract.Event.OnClickLatestVote -> {
                    setEffect {
                        StudiesDetailContract.Effect.NavigateToLatestVoteDetail(
                            event.voteId,
                        )
                    }
                    readContents(studyGroupId = uiState.value.studyGroupDetailInfo.id, readVote = true)
                }

                is StudiesDetailContract.Event.OnClickContents -> {
                    setEffect {
                        StudiesDetailContract.Effect.NavigateToContents(
                            event.startTabIndex,
                        )
                    }
                }

                is StudiesDetailContract.Event.OnClickProfile -> {
                    setEffect {
                        StudiesDetailContract.Effect.NavigateToProfile(
                            event.memberId,
                        )
                    }
                }
            }
        }

        private fun onLoad(studyGroupId: Long) =
            viewModelScope.launch {
                launch {
                    getStudyGroupDetailUseCase(studyGroupId)
                        .withLoading {
                            setState { copy(isLoading = it) }
                        }.safeCollect { studyGroupDetailInfo ->
                            setState {
                                copy(
                                    studyGroupDetailInfo = studyGroupDetailInfo,
                                )
                            }
                        }
                }
                launch {
                    myStudyList = getMyStudyListUseCase().cachedIn(viewModelScope)
                }
            }

        private fun onLoadFeeds(studyGroupId: Long) {
            viewModelScope.launch {
                getStudiesFeedsUseCase(
                    studyGroupId = studyGroupId,
                    cursorCreatedAt = uiState.value.feedsCursorCreatedAt,
                    cursorId = uiState.value.feedsCursorId,
                ).withLoading {
                    setState { copy(isLoading = it) }
                }.safeCollect { feed ->
                    setState {
                        copy(
                            feeds = feeds + feed.content,
                            feedsHasNext = feed.hasNext,
                            feedsCursorCreatedAt = feed.cursorCreatedAt,
                            feedsCursorId = feed.cursorId,
                        )
                    }
                }
            }
        }

        private fun onLoadWeeklyRankings(studyGroupId: Long) {
            if (uiState.value.weeklyRankingsHasNext.not()) return
            viewModelScope.launch {
                getStudiesWeeklyRankingsUseCase(
                    studyGroupId = studyGroupId,
                    cursorStudyTime = uiState.value.weeklyRankingsCursorStudyTime,
                    cursorUserId = uiState.value.weeklyRankingsCursorUserId,
                    firstPageRequestedAt = uiState.value.weeklyRankingsFirstPageRequestedAt,
                ).withLoading {
                    setState { copy(isLoading = it) }
                }.safeCollect { rankings ->
                    setState {
                        copy(
                            weeklyRankings = weeklyRankings + rankings.content,
                            weeklyRankingsHasNext = rankings.hasNext,
                            weeklyRankingsCursorStudyTime = rankings.cursorTimeSeconds,
                            weeklyRankingsCursorUserId = rankings.cursorUserId,
                            weeklyRankingsFirstPageRequestedAt = rankings.baseTime,
                        )
                    }
                }
            }
        }

        private fun onLoadLatestContents(studyGroupId: Long) {
            viewModelScope.launch {
                getStudiesLatestContentsUseCase(studyGroupId)
                    .withLoading {
                        setState { copy(isLoading = it) }
                    }.safeCollect { result ->
                        setState {
                            copy(
                                latestNotice = result.contents.latestNotice,
                                latestNoticeReadChecked = result.readStatus.lastNoticeChecked,
                                latestVote = result.contents.latestVote,
                                latestVoteReadChecked = result.readStatus.lastVoteChecked,
                            )
                        }
                    }
            }
        }

        private fun deleteStudies(studyGroupId: Long) {
            viewModelScope.launch {
                deleteStudiesUseCase(studyGroupId)
                    .withLoading {
                        setState { copy(isLoading = it) }
                    }.safeCollect {
                        showMessage("스터디가 삭제되었습니다.")
                    }
            }
        }

        private fun readContents(
            studyGroupId: Long,
            readNotice: Boolean? = null,
            readVote: Boolean? = null,
        ) {
            viewModelScope.launch {
                Log.d(TAG, "readContents: [$readNotice, $readVote]")
                patchStudiesLatestContentsReadStatusUseCase(
                    studyGroupId = studyGroupId,
                    readNotice = readNotice,
                    readVote = readVote,
                ).withLoading {
                    Log.d(TAG, "readContents: 진행")
                    setState { copy(isLoading = it) }
                }.safeCollect {
                    Log.d(TAG, "readContents: 성공")
                }
            }
        }
    }
