package com.ssafy.neegongnaegong.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.model.update.AppVersionInfo
import com.ssafy.neegongnaegong.domain.usecase.update.CheckForUpdateUseCase
import com.ssafy.neegongnaegong.domain.usecase.update.SkipUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateStatusViewModel
    @Inject
    constructor(
        private val checkForUpdateUseCase: CheckForUpdateUseCase,
        private val skipUpdateUseCase: SkipUpdateUseCase,
    ) : ViewModel() {
        val updateStatus by lazy {
            checkForUpdateUseCase().stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                AppVersionInfo.Unavailable,
            )
        }

        fun skipUpdate(skipVersionCode: Int) {
            viewModelScope.launch {
                skipUpdateUseCase(skipVersionCode)
            }
        }
    }
