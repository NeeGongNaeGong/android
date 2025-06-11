package com.ssafy.neegongnaegong.domain.usecase.user

import androidx.paging.PagingData
import com.ssafy.neegongnaegong.domain.model.User
import com.ssafy.neegongnaegong.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUserUseCase
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) {
        operator fun invoke(userName: String): Flow<PagingData<User>> = userRepository.searchUser(userName = userName)
    }
