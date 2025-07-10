package com.ssafy.neegongnaegong.presentation.group

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ssafy.neegongnaegong.domain.model.studygroup.MyStudyGroupInfo
import com.ssafy.neegongnaegong.domain.usecase.studygroup.GetMyStudyListUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudiesViewModel
    @Inject
    constructor(
        private val getMyStudyListUseCase: GetMyStudyListUseCase,
    ) : BaseViewModel<StudiesContract.Event, StudiesContract.State, StudiesContract.Effect>() {
        var myStudyList: Flow<PagingData<MyStudyGroupInfo>> = emptyFlow()

        override fun createInitialState(): StudiesContract.State = StudiesContract.State()

        override fun handleEvent(event: StudiesContract.Event) {
            when (event) {
                is StudiesContract.Event.OnLoadMyStudies -> onLoadMyStudyList()
                is StudiesContract.Event.OnClickMyStudies ->
                    setEffect {
                        StudiesContract.Effect.NavigateToStudiesDetail(
                            event.studyGroupId,
                        )
                    }

                is StudiesContract.Event.OnClickSearchStudies -> setEffect { StudiesContract.Effect.NavigateToStudiesSearch }
            }
        }

        private fun onLoadMyStudyList() {
            viewModelScope.launch {
                myStudyList = getMyStudyListUseCase().cachedIn(viewModelScope)
            }
        }
    }
