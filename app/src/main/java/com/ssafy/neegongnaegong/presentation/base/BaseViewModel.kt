package com.ssafy.neegongnaegong.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.exception.ApiException
import com.ssafy.neegongnaegong.domain.exception.AuthException
import com.ssafy.neegongnaegong.presentation.util.AuthManager
import com.ssafy.neegongnaegong.presentation.util.SnackbarManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModel<Event : UiEvent, State : UiState, Effect : UiEffect> : ViewModel() {

    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    private val currentState: State
        get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    private val event = _event.asSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    @Inject
    lateinit var authManager: AuthManager

    @Inject
    lateinit var snackbarManager: SnackbarManager

    init {
        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collect {
                handleEvent(it)
            }
        }
    }

    abstract fun handleEvent(event: Event)

    fun setEvent(event: Event) {
        val newEvent = event
        viewModelScope.launch { _event.emit(newEvent) }
    }

    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    /**
     * Flow에 맞춰 Loading 상태를 전달하는 함수
     *
     * @param setLoading onStart에 true를 전달하고, onCompletion에 false를 전달
     */
    protected fun <T> Flow<T>.withLoading(setLoading: (Boolean) -> Unit = {}): Flow<T> {
        return this.onStart { setLoading(true) }.onCompletion { setLoading(false) }
    }

    /**
     * 안전하게 Flow를 collect하는 함수
     *
     * @param errorContext ErrorContext
     * @param block collect 시 실행할 블록
     */
    protected suspend fun <T> Flow<T>.safeCollect(
        errorContext: ErrorContext = ErrorContext.NONE,
        block: suspend (T) -> Unit = {}
    ) {
        runCatching {
            collect { value -> block(value) }
        }.onFailure { exception ->
            exception.printStackTrace()
            _handleException(exception, errorContext)
        }
    }

    protected fun showMessage(message: String) = viewModelScope.launch {
        snackbarManager.showMessage(message)
    }

    protected fun showSuccessMessage(message: String) = viewModelScope.launch {
        snackbarManager.showSuccessMessage(message)
    }

    protected fun showWarningMessage(message: String) = viewModelScope.launch {
        snackbarManager.showWarningMessage(message)
    }

    protected fun showErrorMessage(message: String) = viewModelScope.launch {
        snackbarManager.showErrorMessage(message)
    }

    /**
     * safeCollect 중 발생한 예외를 처리하는 함수
     * 공통 에러를 처리하고, 추가적인 에러 처리는 handleErrorContext에 전달
     *
     * AuthException.InvalidTokenException: login 화면으로 이동
     * ApiException.ServerException: 서버 에러 snack bar 출력
     * ApiException.NetworkException: 네트워크 에러 snack bar 출력
     *
     * @param e Throwable
     * @param errorContext ErrorContext
     */
    private fun _handleException(e: Throwable, errorContext: ErrorContext) {
        when (e) {
            is AuthException.InvalidTokenException -> viewModelScope.launch {
                showErrorMessage(e.message)
                authManager.invalid()
            }

            is ApiException.ServerException -> viewModelScope.launch {
                showErrorMessage(e.message)
            }

            is ApiException.NetworkException -> viewModelScope.launch {
                showErrorMessage(e.message)
            }
        }
        handleException(e, errorContext)
    }

    /**
     * ErrorContext에 따라 예외를 처리하는 함수
     * 상속 시 각 ViewModel에서 추가적인 에러 처리를 할 수 있음
     *
     * @param e Throwable
     * @param errorContext ErrorContext
     */
    open fun handleException(e: Throwable, errorContext: ErrorContext) {}
}
