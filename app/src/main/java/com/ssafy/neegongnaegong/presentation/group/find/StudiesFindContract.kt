package com.ssafy.neegongnaegong.presentation.group.find

import com.ssafy.neegongnaegong.domain.model.studies.Category
import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState
import com.ssafy.neegongnaegong.presentation.group.find.component.StudiesSortType

class StudiesFindContract {
    sealed class Event : UiEvent {
        data object OnLoad : Event()

        data class StudiesClicked(
            val studiesId: Long,
        ) : Event()

        data class OnStudiesApplyClicked(
            val studiesId: Long,
        ) : Event()

        data class OnSelectedStudies(
            val studies: Studies,
        ) : Event()

        // 스터디 정보 다이어로그
        data object OnStudiesInfoDialogShow : Event()

        data class OnStudiesInfoDialogConfirm(
            val studyGroupId: Long,
        ) : Event()

        data object OnStudiesInfoDialogCancel : Event()

        data object OnStudiesInfoDialogDismiss : Event()

        // 키워드 검색
        data class OnTypingSearch(
            val keyword: String,
        ) : Event()

        data object OnSearch : Event()

        data class OnSelectedFilterType(
            val selectedFilterType: StudiesSortType,
        ) : Event()

        // 카테고리 필터 다이어로그
        data object OnCategoryFilterDialogShow : Event()

        data class OnCategoryFilterDialogConfirm(
            val selectedCategorise: Set<Category>,
        ) : Event()

        data object OnCategoryFilterDialogCancel : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val studiesList: List<Studies> = emptyList(),
        val hasNext: Boolean = true,
        val cursorValue: String? = null,
        val cursorId: Long? = null,
        // 선택한 스터디 및 다이어로그 상태
        val selectedStudies: Studies? = null,
        val isStudiesInfoDialogShow: Boolean = false,
        // 스터디 검색
        val searchKeyword: String = "",
        // 스터디 검색 정렬
        val selectedSortType: StudiesSortType = StudiesSortType.CREATED_AT,
        // 카테고리
        val isCategoryFilterDialogShow: Boolean = false,
        val categories: List<Category> = emptyList(),
        val selectedCategoryFilter: Set<Category> = emptySet(),
    ) : UiState

    sealed class Effect : UiEffect {
        data object ShowStudies : Effect()

        data class NavigateToGroupDetail(
            val studiesId: Long,
        ) : Effect()
    }

    sealed class Error : ErrorContext {
        data object GetStudiesListError : Error()

        data class ApplyStudiesError(
            val studyGroupId: Long,
        ) : Error()
    }
}
