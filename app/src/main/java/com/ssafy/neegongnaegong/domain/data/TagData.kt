package com.ssafy.neegongnaegong.domain.data

import com.ssafy.neegongnaegong.domain.model.learning.Tag

object TagData {
    val tags: List<Tag> = listOf(
        // 프로그래밍
        Tag(1L, "CS", "Computer Science"),
        Tag(2L, "프론트엔드", "Frontend"),
        Tag(3L, "백엔드", "Backend"),
        Tag(4L, "모바일", "Mobile"),
        Tag(5L, "데브옵스", "DevOps"),
        Tag(6L, "알고리즘", "Algorithm"),

        // 디자인
        Tag(7L, "UI/UX", "UI/UX"),
        Tag(8L, "3D 모델링", "3D Modeling"),
        Tag(9L, "일러스트레이션", "Illustration"),
        Tag(10L, "모션 그래픽", "Motion Graphic"),
        Tag(11L, "웹 디자인", "Web Design"),
        Tag(12L, "타이포그래피", "Typography"),

        // 언어
        Tag(13L, "영어", "English"),
        Tag(14L, "일본어", "Japanese"),
        Tag(15L, "중국어", "Chinese"),
        Tag(16L, "한국어", "Korean"),
        Tag(17L, "스페인어", "Spanish"),
        Tag(18L, "프랑스어", "French"),
        Tag(19L, "독일어", "German"),

        // 비즈니스
        Tag(20L, "마케팅", "Marketing"),
        Tag(21L, "재테크", "Asset Management"),
        Tag(22L, "금융", "Finance"),
        Tag(23L, "회계", "Accounting"),
        Tag(24L, "인사", "HR"),
        Tag(25L, "영업", "Sales"),

        // 예술
        Tag(26L, "음악", "Music"),
        Tag(27L, "미술", "Art"),
        Tag(28L, "사진", "Photography"),
        Tag(29L, "영상", "Video"),
        Tag(30L, "문학", "Literature"),
        Tag(31L, "공연", "Performance"),
        Tag(32L, "디지털 아트", "Digital Art"),
        Tag(33L, "만화", "Comics"),

        // 학문
        Tag(34L, "수학", "Math"),
        Tag(35L, "과학", "Science"),
        Tag(36L, "인문학", "Humanities"),
        Tag(37L, "사회과학", "Social Science"),
        Tag(38L, "공학", "Engineering"),
        Tag(39L, "의학", "Medicine"),
        Tag(40L, "역사", "History"),
        Tag(41L, "철학", "Philosophy"),

        // 취업
        Tag(42L, "자기소개서", "Resume"),
        Tag(43L, "포트폴리오", "Portfolio"),
        Tag(44L, "자격증", "Certificate"),
        Tag(45L, "어학", "Language Test"),
        Tag(46L, "면접", "Interview"),

        // 취미
        Tag(47L, "요리", "Cooking"),
        Tag(48L, "운동", "Exercise"),
        Tag(49L, "여행", "Travel"),
        Tag(50L, "독서", "Reading"),
        Tag(51L, "게임", "Gaming"),
        Tag(52L, "공예", "Craft"),
        Tag(53L, "식물", "Plant"),
        Tag(54L, "반려동물", "Pet")
    )
}
