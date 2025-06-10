package com.ssafy.neegongnaegong.presentation.group.edit

import android.net.Uri
import com.ssafy.neegongnaegong.domain.model.studies.Category
import com.ssafy.neegongnaegong.domain.model.studies.StudyInfo
import com.ssafy.neegongnaegong.domain.model.studies.Tag
import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState
import okhttp3.RequestBody

class StudiesEditContract {
    sealed class Event : UiEvent {
        data class OnLoad(
            val studyGroupId: Long,
        ) : Event()

        data class OnNameChanged(
            val name: String,
        ) : Event()

        data class OnIsPublicChanged(
            val isPublic: Boolean,
        ) : Event()

        data class OnTargetStudyTimeChanged(
            val targetStudyTime: Int,
        ) : Event()

        data class OnMaxMembersChanged(
            val maxMembers: Int,
        ) : Event()

        data class OnSelectedCategory(
            val category: Category,
        ) : Event()

        data class OnTagSelected(
            val tag: Tag,
        ) : Event()

        data class OnTagUnSelected(
            val tag: Tag,
        ) : Event()

        data class OnDescriptionChanged(
            val description: String,
        ) : Event()

        data object OnClearProfileImg : Event()

        data class OnSelectedImage(
            val extension: String?,
        ) : Event()

        data class OnSelectedImageRequest(
            val requestImage: RequestBody?,
        ) : Event()

        data class OnProfileImgChanged(
            val profileImg: String? = null,
        ) : Event()

        data object OnCreateStudiesClicked : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val isSuccess: Boolean = false,
        val isOnUpdate: Boolean = false,
        val studyGroupId: Long,
        val categories: List<Category> = emptyList(),
        val selectedCategory: Category? = null,
        val selectedTags: List<Tag> = emptyList(),
        val tags: List<Tag> = emptyList(),
        val selectedImageUri: Uri? = null,
        val presignedUrl: String? = null,
        val requestImage: RequestBody? = null,
        val validateCreateStudies: Boolean = true,
        val currentMembers: Int = 2,
        val studyInfo: StudyInfo = StudyInfo.empty(),
    ) : UiState

    sealed class Effect : UiEffect {
        data object NavigateToBack : Effect()
    }

    sealed class Error : ErrorContext {
        data object CreateStudiesError : Error()
    }
}
