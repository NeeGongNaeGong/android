package com.ssafy.neegongnaegong.domain.usecase.update

import com.ssafy.neegongnaegong.domain.repository.UpdateRepository
import javax.inject.Inject

class SkipUpdateUseCase
    @Inject
    constructor(
        private val updateRepository: UpdateRepository,
    ) {
        suspend operator fun invoke(skipVersionCode: Int) =
            with(updateRepository) {
                saveSkippedVersionCode(versionCode = skipVersionCode)
            }
    }
