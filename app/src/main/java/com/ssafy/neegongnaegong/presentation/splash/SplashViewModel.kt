package com.ssafy.neegongnaegong.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.usecase.auth.ReissueUseCase
import com.ssafy.neegongnaegong.presentation.util.AuthDestinationManager
import dagger.hilt.android.lifecycle.HiltViewModel
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
                }.collect {
                    if (it) AuthDestinationManager.valid()
                    else AuthDestinationManager.invalid()
                    isLoading = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                AuthDestinationManager.invalid()
                isLoading = false
            }
        }
    }
}
