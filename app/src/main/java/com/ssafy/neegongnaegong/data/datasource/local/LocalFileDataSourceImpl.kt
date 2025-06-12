package com.ssafy.neegongnaegong.data.datasource.local

import android.content.Context
import android.webkit.MimeTypeMap
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class LocalFileDataSourceImpl
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : LocalFileDataSource {
        override fun getFileType(url: String): Flow<String?> =
            flow {
                val uri = url.toUri()
                val mimeType: String? = context.contentResolver.getType(uri)
                val fileType: String? = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
                emit(value = fileType)
            }

        override fun getRequestBody(url: String): Flow<RequestBody?> =
            flow {
                val uri = url.toUri()
                val mimeType: String? = context.contentResolver.getType(uri)
                val body: RequestBody? =
                    context.contentResolver.openInputStream(uri)?.use { stream ->
                        val bytes = stream.readBytes()
                        bytes.toRequestBody(mimeType?.toMediaType())
                    }
                emit(value = body)
            }
    }
