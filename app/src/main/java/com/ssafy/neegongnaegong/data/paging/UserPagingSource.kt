package com.ssafy.neegongnaegong.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ssafy.neegongnaegong.data.datasource.network.NetworkUserDataSource
import com.ssafy.neegongnaegong.data.model.user.response.CursorKey
import com.ssafy.neegongnaegong.data.model.user.response.UserResponse
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

class UserPagingSource
    @AssistedInject
    constructor(
        private val userDataSource: NetworkUserDataSource,
        @Assisted private val userName: String,
    ) : PagingSource<CursorKey, UserResponse>() {
        override suspend fun load(params: LoadParams<CursorKey>): LoadResult<CursorKey, UserResponse> {
            val cursorId: Long? = params.key?.id
            val cursorCreateAt: String? = params.key?.createdAt

            return runCatching {
                val remoteResult =
                    userDataSource.searchUsers(
                        time = cursorCreateAt,
                        cursorId = cursorId,
                        size = params.loadSize,
                        userName = userName,
                    ).first()

                val nextKey =
                    remoteResult.run {
                        if (cursorId != null && cursorCreatedAt != null) {
                            CursorKey(id = cursorId, createdAt = cursorCreatedAt)
                        } else {
                            null
                        }
                    }

                LoadResult.Page(
                    data = remoteResult.content,
                    prevKey = null,
                    nextKey = nextKey,
                )
            }.getOrElse { throwable ->
                LoadResult.Error(throwable)
            }
        }

        override fun getRefreshKey(state: PagingState<CursorKey, UserResponse>): CursorKey? {
            val anchor = state.anchorPosition ?: return null
            val item =
                state.closestPageToPosition(anchor)
                    ?.data
                    ?.firstOrNull()
                    ?: return null

            return CursorKey(id = item.cursorId, createdAt = item.cursorCreatedAt)
        }

        @AssistedFactory
        interface Factory {
            fun create(userName: String): UserPagingSource
        }
    }
