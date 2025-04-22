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
    var isLoginSuccess = false

    init {
        //TODO 여기서 로그인이 이미 되어 있는지 등을 확인하는 작업 후 isLoginSuccess 값을 바꿔주면서, isLoading 값을 false로 바꾸기
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
