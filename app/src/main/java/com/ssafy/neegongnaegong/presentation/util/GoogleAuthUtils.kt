package com.ssafy.neegongnaegong.presentation.util

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.ssafy.neegongnaegong.BuildConfig

object GoogleAuthUtils {
    /**
     * credential manager를 통해 하단 시트 UI를 통해 사용자의 구글 계정을 인증하고 토큰을 받아옵니다.
     *
     * @param context Activity Context만 사용 가능합니다
     * @param onSuccess 성공시 google oauth tokenId와 함께 실행됩니다.
     */
    suspend fun signIn(
        context: Context,
        onSuccess: (String) -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        val credentialManager = CredentialManager.create(context)

        val credentialOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setAutoSelectEnabled(false)
            .setServerClientId(BuildConfig.GOOGLE_CLIENT_ID)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(credentialOption)
            .build()

        runCatching {
            credentialManager.getCredential(context, request)
        }.onSuccess { response ->
            handleSignIn(response, onSuccess)
        }.onFailure { exception ->
            Log.d("GoogleAuthUtils", "signIn: exception occurred - $exception")
            when (exception) {
                is NoCredentialException -> handleGetCredentialException(context, onSuccess, onFailure)
                is GetCredentialCancellationException -> { }
                is GetCredentialException -> openGoogleAccountSettings(context)
                else -> onFailure(exception)
            }
        }
    }

    suspend fun signOut(context: Context) {
        val credentialManager = CredentialManager.create(context)

        credentialManager.clearCredentialState(
            ClearCredentialStateRequest(GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)
        )
    }

    private fun handleSignIn(
        result: GetCredentialResponse,
        onTokenIdReceived: (String) -> Unit
    ) {
        when (result.credential) {
            is CustomCredential -> {
                if (result.credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(result.credential.data)
                    onTokenIdReceived(googleIdTokenCredential.idToken)
                }
            }
        }
    }

    private suspend fun handleGetCredentialException(
        context: Context,
        onSuccess: (String) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        val credentialManager = CredentialManager.create(context)

        val credentialOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(BuildConfig.GOOGLE_CLIENT_ID)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(credentialOption)
            .build()

        runCatching {
            credentialManager.getCredential(context, request)
        }.onSuccess { response ->
            handleSignIn(response, onSuccess)
        }.onFailure { exception ->
            Log.d("GoogleAuthUtils", "signIn: exception occurred - $exception")
            when (exception) {
                is GetCredentialCancellationException -> { }
                is GetCredentialException -> openGoogleAccountSettings(context)
                else -> onFailure(exception)
            }
        }
    }

    private fun openGoogleAccountSettings(context: Context) {
        val addAccountIntent = Intent(Settings.ACTION_ADD_ACCOUNT).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra(Settings.EXTRA_ACCOUNT_TYPES, arrayOf("com.google"))
        }
        context.startActivity(addAccountIntent)
    }
}
