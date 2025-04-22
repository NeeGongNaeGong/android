package com.ssafy.neegongnaegong.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.usecase.auth.ReissueUseCase
import com.ssafy.neegongnaegong.presentation.util.AuthDestinationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val reissueUseCase: ReissueUseCase,
) : ViewModel() {
    /**
     * Contract 따로 빼기도 좀 뭐하고, Splash는 MVI로 봐야 하나 싶어서 일단 여기에 놨습니다
     */
    data class State(val isLoading: Boolean, val isLoginSuccess: Boolean)
    /**
     * loading이 되었는지와 login이 성공했는지를 관리하는 상태
     */
    val state = MutableStateFlow(State(isLoading = true, isLoginSuccess = false))
    init {
        //TODO 여기서 로그인이 이미 되어 있는지 등을 확인하는 작업 후 isLoginSuccess 값을 바꿔주면서, isLoading 값을 false로 바꾸기
        viewModelScope.launch {
            try {
                reissueUseCase().onStart {
                    state.update {
                        it.copy(
                            isLoading =  true,
                        )
                    }
                }.collect {
                    if (it) AuthDestinationManager.valid()
                    else AuthDestinationManager.invalid()
                    state.update { legacyState->
                        // 결과에 따라 상태를 업데이트 하는 예시
                        legacyState.copy(
                            isLoading =  false,
                            isLoginSuccess = true
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                AuthDestinationManager.invalid()
//                isLoading = false
            }
        }

    }
}
