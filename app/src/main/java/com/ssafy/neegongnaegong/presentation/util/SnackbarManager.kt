package com.ssafy.neegongnaegong.presentation.util

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

object SnackbarManager {
    /**
     * snackbar의 타입
     *
     * snackbar의 배경 색상과 prefix icon을 결정.
     * 각 타입의 preview는 [NeeGongNaeGongSnackbarHost.kt][com.ssafy.neegongnaegong.presentation.component.snackbar.NeeGongNaeGongSnackbarHost] 참고
     */
    enum class Type {
        Success,
        Warning,
        Error,
        None
    }

    /**
     * snackbar로 표시하고 싶은 메시지를 정의
     *
     * @param message 출력할 문자열
     * @param type snackbar type, 배경 색상과 prefix icon을 결정
     * @param action action button의 문자와 callback을 결정
     */
    data class Message(
        val message: String,
        val type: Type,
        val action: Action? = null,
    )

    /**
     * action button을 정의
     *
     * @param label text button의 문자열
     * @param callback callback
     */
    data class Action(
        val label: String,
        val callback: () -> Unit,
    )

    private val _message = MutableSharedFlow<Message>()
    /**
     * 현재 보여질 snackbar 메시지를 외부에 전달하는 Flow
     *
     * `SnackbarHost`에서 수신하여 메시지를 화면에 표시할 때 사용됩니다.
     * `SnackbarManager`에서 메시지를 emit하면 이 Flow를 통해 구독자에게 전달됩니다.
     *
     * [NeeGongNaeGongSnackbarHost][com.ssafy.neegongnaegong.presentation.component.snackbar.NeeGongNaeGongSnackbarHost]에서 구독하여 사용합니다.
     *
     * 예시:
     * ```
     * val message by snackbarManager.message.collectAsStateWithLifecycle()
     * ```
     */
    val message: SharedFlow<Message> = _message

    /**
     * snackbar 메시지를 전달합니다.
     *
     * [message], [type], [action] 정보를 포함한 메시지를 [message] Flow를 통해 emit합니다.
     *
     * @param message 사용자에게 보여줄 문자열
     * @param type 메시지의 유형 (성공, 경고, 에러 등)
     * @param action snackbar의 action 버튼(label 및 callback)을 정의한 객체 (선택적)
     */
    suspend fun showMessage(
        message: String,
        type: Type = Type.None,
        action: Action? = null,
    ) {
        _message.emit(Message(message, type, action))
    }

    /**
     * 성공(Success) 메시지를 snackbar로 표시합니다.
     *
     * [Type.Success]로 설정된 메시지를 [message] Flow를 통해 emit합니다.
     *
     * @param message 사용자에게 보여줄 문자열
     * @param action snackbar의 action 버튼(label 및 callback)을 정의한 객체 (선택적)
     */
    suspend fun showSuccessMessage(
        message: String,
        action: Action? = null,
    ) {
        showMessage(message, Type.Success, action)
    }

    /**
     * 경고(Warning) 메시지를 snackbar로 표시합니다.
     *
     * [Type.Warning]으로 설정된 메시지를 [message] Flow를 통해 emit합니다.
     *
     * @param message 사용자에게 보여줄 문자열
     * @param action snackbar의 action 버튼(label 및 callback)을 정의한 객체 (선택적)
     */
    suspend fun showWarningMessage(
        message: String,
        action: Action? = null,
    ) {
        showMessage(message, Type.Warning, action)
    }

    /**
     * 에러(Error) 메시지를 snackbar로 표시합니다.
     *
     * [Type.Error]로 설정된 메시지를 [message] Flow를 통해 emit합니다.
     *
     * @param message 사용자에게 보여줄 문자열
     * @param action snackbar의 action 버튼(label 및 callback)을 정의한 객체 (선택적)
     */
    suspend fun showErrorMessage(
        message: String,
        action: Action? = null,
    ) {
        showMessage(message, Type.Error, action)
    }
}