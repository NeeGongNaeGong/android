package com.ssafy.neegongnaegong.presentation.group.detail

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.usecase.studies.DeleteStudiesUseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.GetStudiesDetailUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.group.StudiesContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "StudiesDetailViewModel"

@HiltViewModel
class StudiesDetailViewModel
    @Inject
    constructor(
        private val getStudiesDetailUseCase: GetStudiesDetailUseCase,
        private val deleteStudiesUseCase: DeleteStudiesUseCase,
    ) : BaseViewModel<StudiesDetailContract.Event, StudiesDetailContract.State, StudiesDetailContract.Effect>() {
        override fun handleException(
            e: Throwable,
            errorContext: ErrorContext,
            retry: () -> Unit,
        ) {
            val error = errorContext as? StudiesContract.Error ?: return
            Log.d(TAG, "handleException: $error")
        }

        override fun createInitialState(): StudiesDetailContract.State = StudiesDetailContract.State()

        override fun handleEvent(event: StudiesDetailContract.Event) {
            when (event) {
                is StudiesDetailContract.Event.OnLoad -> onLoad(event.studyGroupId)
                is StudiesDetailContract.Event.OndDeleteStudies -> deleteStudies(event.studyGroupId)
            }
        }

        private fun onLoad(studyGroupId: Long) =
            viewModelScope.launch {
                getStudiesDetailUseCase(studyGroupId)
                    .withLoading {
                        setState { copy(isLoading = it) }
                    }.safeCollect { studies ->
                        setState {
                            copy(
                                studies = studies,
                            )
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
    }
