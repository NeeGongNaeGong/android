package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.remote.S3Api
import okhttp3.RequestBody
import javax.inject.Inject

class NetworkS3DataSourceImpl
    @Inject
    constructor(
        private val s3Api: S3Api,
    ) : NetworkS3DataSource {
        override suspend fun uploadImageToS3(
            presignedUrl: String,
            request: RequestBody,
        ) {
            s3Api.uploadImageToS3(presignedUrl, request)
        }
    }
