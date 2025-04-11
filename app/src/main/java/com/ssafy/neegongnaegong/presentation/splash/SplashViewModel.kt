package com.ssafy.neegongnaegong.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.usecase.auth.ReissueUseCase
import com.ssafy.neegongnaegong.presentation.util.AuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val reissueUseCase: ReissueUseCase,
) : ViewModel() {
    var isLoading = true

    init {
        viewModelScope.launch {
            try {
                reissueUseCase().onStart {
                    isLoading = true
                }.onCompletion {
                    isLoading = false
                }.collect {
                    if (it) AuthManager.valid()
                    else AuthManager.invalid()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                AuthManager.invalid()
            }
        }
    }
}
