package com.ssafy.neegongnaegong.data.remote.utils

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import com.ssafy.neegongnaegong.BuildConfig
import com.ssafy.neegongnaegong.data.remote.TokenManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject

class KeyStoreTokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) : TokenManager {
    private val keystore: KeyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
    private val aliasPrefix = BuildConfig.KEYSTORE_ALIAS_PREFIX

    init {
        if (!isKeyExist()) {
            generateKey()
        }
    }

    private fun generateKey() {
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        val spec = KeyGenParameterSpec.Builder(aliasPrefix, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()

        keyGenerator.init(spec)
        keyGenerator.generateKey()
    }

    private fun isKeyExist(): Boolean {
        return try {
            keystore.getKey(aliasPrefix, null) != null
        } catch (e: Exception) {
            false
        }
    }

    override fun saveToken(tokenType: String, token: String) {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, getKey(tokenType))

        val iv = cipher.iv
        val encryption = cipher.doFinal(token.toByteArray())

        val ivBase64 = Base64.encodeToString(iv, Base64.DEFAULT)
        val encryptionBase64 = Base64.encodeToString(encryption, Base64.DEFAULT)

        val sharedPreferences = context.getSharedPreferences(BuildConfig.TOKEN_PREFERENCES_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("${tokenType}_iv", ivBase64)
            putString("${tokenType}_encryptedToken", encryptionBase64)
            apply()
        }
    }

    override fun getToken(tokenType: String): String? {
        val sharedPreferences = context.getSharedPreferences(BuildConfig.TOKEN_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val ivBase64 = sharedPreferences.getString("${tokenType}_iv", null) ?: return null
        val encryptedTokenBase64 = sharedPreferences.getString("${tokenType}_encryptedToken", null) ?: return null

        val iv = Base64.decode(ivBase64, Base64.DEFAULT)
        val encryptedToken = Base64.decode(encryptedTokenBase64, Base64.DEFAULT)

        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val spec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, getKey(tokenType), spec)

        val decryptedData = cipher.doFinal(encryptedToken)
        return String(decryptedData)
    }

    private fun getKey(tokenType: String): SecretKey {
        val alias = aliasPrefix + tokenType
        return keystore.getKey(alias, null) as SecretKey
    }

    override fun clearToken() {
        val sharedPreferences = context.getSharedPreferences(BuildConfig.TOKEN_PREFERENCES_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }
}
