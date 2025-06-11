package com.ssafy.neegongnaegong.domain.usecase.studygroup

import com.ssafy.neegongnaegong.domain.exception.InvalidGroupIdException
import com.ssafy.neegongnaegong.domain.repository.NotificationRepository
import com.ssafy.neegongnaegong.domain.repository.StudyGroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class RejectStudyGroupJoinUseCase
    @Inject
    constructor(
        private val studyGroupRepository: StudyGroupRepository,
        private val notificationRepository: NotificationRepository,
    ) {
        operator fun invoke(
            studyGroupId: Long?,
            userId: Long,
            notificationId: Long?,
        ): Flow<Unit> {
            if (studyGroupId == null) throw InvalidGroupIdException()

            return studyGroupRepository.rejectStudyGroupJoin(
                studyGroupId = studyGroupId,
                userId = userId,
                notificationId = notificationId,
            ).onEach {
                notificationId?.let {
                    notificationRepository.getNotification(notificationId = notificationId)
                        .firstOrNull()
                }
            }
        }
    }
