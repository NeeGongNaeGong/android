package com.ssafy.neegongnaegong.presentation.util

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

object FileUtils {
    fun Context.getFileExtension(uri: Uri): String? {
        val mimeType = contentResolver.getType(uri) ?: return null
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
    }

    fun Context.uriToRequestBody(uri: Uri): RequestBody? {
        val mimeType = contentResolver.getType(uri) ?: return null
        return contentResolver.openInputStream(uri)?.use { stream ->
            val bytes = stream.readBytes()
            bytes.toRequestBody(mimeType.toMediaType())
        }
    }
}
