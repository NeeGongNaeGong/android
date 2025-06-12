package com.ssafy.neegongnaegong.domain.usecase.user

import com.ssafy.neegongnaegong.domain.exception.DuplicateNicknameException
import com.ssafy.neegongnaegong.domain.exception.InvalidNicknameException
import com.ssafy.neegongnaegong.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateNicknameUseCase
    @Inject
    constructor(private val userRepository: UserRepository) {
        @OptIn(ExperimentalCoroutinesApi::class)
        operator fun invoke(nickname: String): Flow<Unit> =
            flow {
                if (!isValidNickname(nickname = nickname) or nickname.isBlank()) {
                    throw InvalidNicknameException()
                }

                val isUsed: Boolean = userRepository.validateNickname(nickname = nickname).first()
                if (isUsed) throw DuplicateNicknameException()

                userRepository.updateNickname(nickname = nickname).firstOrNull()
            }

        // 한글 / 영어 / 숫자 + 공백 만 가능
        private fun isValidNickname(nickname: String): Boolean {
            val regex = Regex("^[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9 ]+$")
            return regex.matches(nickname)
        }
    }
