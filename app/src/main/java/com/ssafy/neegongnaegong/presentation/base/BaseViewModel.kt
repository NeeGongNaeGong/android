package com.ssafy.neegongnaegong.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.exception.ApiException
import com.ssafy.neegongnaegong.domain.exception.AuthException
import com.ssafy.neegongnaegong.presentation.util.AuthManager
import com.ssafy.neegongnaegong.presentation.util.SnackbarManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
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
     * 일반 메시지를 snackbar로 표시합니다.
     *
     * ViewModel 내에서 간편하게 호출할 수 있도록 [SnackbarManager.showMessage]를 viewModelScope 내에서 실행합니다.
     * 메시지의 타입과 action 버튼을 설정할 수 있습니다.
     *
     * @param message 사용자에게 보여줄 문자열
     * @param type 메시지의 타입. 기본값은 [SnackbarManager.Type.None]입니다.
     * @param action snackbar의 action 버튼(label 및 callback)을 정의한 객체 (선택적)
     */
    protected fun showMessage(
        message: String,
        type: SnackbarManager.Type = SnackbarManager.Type.None,
        action: SnackbarManager.Action? = null
    ) = viewModelScope.launch {
        SnackbarManager.showMessage(message, type, action)
    }

    /**
     * 성공(Success) 메시지를 snackbar로 표시합니다.
     *
     * ViewModel 내에서 간편하게 호출할 수 있도록 [SnackbarManager.showSuccessMessage]를 viewModelScope 내에서 실행합니다.
     * 메시지의 성공 타입과 action 버튼을 설정할 수 있습니다.
     *
     * @param message 사용자에게 보여줄 문자열
     * @param action snackbar의 action 버튼(label 및 callback)을 정의한 객체 (선택적)
     */
    protected fun showSuccessMessage(
        message: String,
        action: SnackbarManager.Action? = null
    ) = viewModelScope.launch {
        SnackbarManager.showSuccessMessage(message, action)
    }

    /**
     * 경고(Warning) 메시지를 snackbar로 표시합니다.
     *
     * ViewModel 내에서 간편하게 호출할 수 있도록 [SnackbarManager.showWarningMessage]를 viewModelScope 내에서 실행합니다.
     * 메시지의 경고 타입과 action 버튼을 설정할 수 있습니다.
     *
     * @param message 사용자에게 보여줄 문자열
     * @param action snackbar의 action 버튼(label 및 callback)을 정의한 객체 (선택적)
     */
    protected fun showWarningMessage(
        message: String,
        action: SnackbarManager.Action? = null
    ) = viewModelScope.launch {
        SnackbarManager.showWarningMessage(message, action)
    }

    /**
     * 에러(Error) 메시지를 snackbar로 표시합니다.
     *
     * ViewModel 내에서 간편하게 호출할 수 있도록 [SnackbarManager.showErrorMessage]를 viewModelScope 내에서 실행합니다.
     * 메시지의 에러 타입과 action 버튼을 설정할 수 있습니다.
     *
     * @param message 사용자에게 보여줄 문자열
     * @param action snackbar의 action 버튼(label 및 callback)을 정의한 객체 (선택적)
     */
    protected fun showErrorMessage(
        message: String,
        action: SnackbarManager.Action? = null
    ) = viewModelScope.launch {
        SnackbarManager.showErrorMessage(message, action)
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
     * 실패시 [_handleException]로 공통 에러 로직 처리 후
     * [handleException]로 커스템 에러 로직 처리
     *
     * @param errorContext ErrorContext
     * @param block collect 시 실행할 블록
     */
    protected suspend fun <T> Flow<T>.safeCollect(
        errorContext: ErrorContext? = null,
        block: suspend (T) -> Unit = {}
    ) {
        runCatching {
            collect { value -> block(value) }
        }.onFailure { exception ->
            exception.printStackTrace()
            _handleException<T>(exception, errorContext) {
                this.safeCollect(errorContext, block)
            }
        }
    }

    /**
     * 안전하게 suspend 블록을 실행하는 함수
     * 성공 시 [onSuccess] 블록을 호출하고,
     * 실패 시 [_handleException]로 공통 에러 로직 처리 후,
     * [retry]를 통해 재시도 로직을 연결할 수 있음
     *
     * @param onSuccess suspend 블록 실행 성공 시 호출될 콜백
     * @param errorContext ErrorContext – 에러 처리 컨텍스트
     * @param retry 실패 시 재시도할 로직
     * @param block 실행할 suspend 블록
     */
    protected open fun <Result> CoroutineScope.safeLaunch(
        onSuccess: (Result) -> Unit = {},
        errorContext: ErrorContext? = null,
        retry: () -> Unit = {},
        block: suspend () -> Result
    ): Job = launch {
        runCatching { block() }
            .onSuccess { result: Result -> onSuccess(result) }
            .onFailure { throwable: Throwable ->
                _handleException<Result>(e = throwable, errorContext = errorContext, retry = retry)
            }
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
     * @param retry 재시도 콜백
     */
    private fun <T> _handleException(
        e: Throwable,
        errorContext: ErrorContext?,
        retry: suspend () -> Unit
    ) {
        when (e) {
            is AuthException.InvalidTokenException -> viewModelScope.launch {
                showErrorMessage(e.message)
                authManager.invalid()
            }

            is ApiException.ServerException -> viewModelScope.launch {
                showErrorMessage(
                    e.message,
                    SnackbarManager.Action.retry { viewModelScope.launch { retry() } }
                )
            }

            is ApiException.NetworkException -> viewModelScope.launch {
                showErrorMessage(e.message)
            }

            else -> errorContext?.let { handleException(e, it) { viewModelScope.launch { retry() } } }
        }
    }

    /**
     * Flow를 ViewModel에서 사용할 수 있는 StateFlow로 변환하는 확장 함수입니다.
     *
     * @param initValue 초기 상태 값입니다. 화면이 처음 구성될 때 사용할 기본 값입니다.
     * @return ViewModel 범위(viewModelScope) 내에서 구독되는 StateFlow를 반환합니다.
     */
    protected fun <T> Flow<T>.toViewModelState(initValue: T): StateFlow<T> = stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = TIME_OUT),
        initialValue = initValue
    )

    /**
     * ErrorContext에 따라 예외를 처리하는 함수
     * 상속 시 각 ViewModel에서 추가적인 에러 처리를 할 수 있음
     *
     * 너무 많은 수정이 필요해서 abstract 대신에 open으로 해놨습니다.
     *
     * @param e Throwable
     * @param errorContext ErrorContext
     * @param retry 재시도 콜백
     */
    open fun handleException(e: Throwable, errorContext: ErrorContext, retry: () -> Unit) {}

    companion object {
        protected const val TIME_OUT: Long = 5_000L
    }
}
