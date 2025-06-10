package com.ssafy.neegongnaegong.domain.model.notification

enum class NotificationType {
    /** 그룹 가입 요청이 이미 수행됨 - 화면 이동 없음 */
    GROUP_JOIN_REQUEST_EXPIRED,

    /** 그룹 가입 요청 도착 - 화면 이동 없음, 수락과 거절 로직 수행 */
    GROUP_JOIN_REQUEST,

    /** 그룹 가입이 승인됨 - 그룹 화면으로 이동 */
    GROUP_JOIN_APPROVE,

    /** 그룹 가입이 거절됨 - 화면 이동 없음 */
    GROUP_JOIN_REJECT,

    /** 공지사항이 등록됨 - NOTICE 화면으로 이동 */
    NOTICE_POSTED,

    /** 투표가 생성됨 - VOTE 화면으로 이동 */
    VOTE_CREATED,

    /** 투표가 종료됨 - VOTE 화면으로 이동 */
    VOTE_ENDED,

    /** 시스템 알림 - 화면 이동 없음 */
    SYSTEM,
}
