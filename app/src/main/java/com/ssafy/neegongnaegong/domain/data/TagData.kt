package com.ssafy.neegongnaegong.domain.data

import com.ssafy.neegongnaegong.domain.model.write.Tag

object TagData {
    val tags: List<Tag> = listOf(
        // 프로그래밍
        Tag("CS", "Computer Science, CS"),
        Tag("프론트엔드", "Frontend"),
        Tag("백엔드", "Backend"),
        Tag("모바일", "Mobile"),
        Tag("데브옵스", "DevOps"),
        Tag("알고리즘", "Algorithm"),

        // 디자인
        Tag("UI/UX", "UI/UX"),
        Tag("3D 모델링", "3D Modeling"),
        Tag("일러스트레이션", "Illustration"),
        Tag("모션 그래픽", "Motion Graphic"),
        Tag("웹 디자인", "Web Design"),
        Tag("타이포그래피", "Typography"),

        // 언어
        Tag("영어", "English"),
        Tag("일본어", "Japanese"),
        Tag("중국어", "Chinese"),
        Tag("한국어", "Korean"),
        Tag("스페인어", "Spanish"),
        Tag("프랑스어", "French"),
        Tag("독일어", "German"),

        // 비즈니스
        Tag("마케팅", "Marketing"),
        Tag("재테크", "Asset Management"),
        Tag("금융", "Finance"),
        Tag("회계", "Accounting"),
        Tag("인사", "HR"),
        Tag("영업", "Sales"),

        // 예술
        Tag("음악", "Music"),
        Tag("미술", "Art"),
        Tag("사진", "Photography"),
        Tag("영상", "Video"),
        Tag("문학", "Literature"),
        Tag("공연", "Performance"),
        Tag("디지털 아트", "Digital Art"),
        Tag("만화", "Comics"),

        // 학문
        Tag("수학", "Math"),
        Tag("과학", "Science"),
        Tag("인문학", "Humanities"),
        Tag("사회과학", "Social Science"),
        Tag("공학", "Engineering"),
        Tag("의학", "Medicine"),
        Tag("역사", "History"),
        Tag("철학", "Philosophy"),

        // 취업
        Tag("자기소개서", "Resume"),
        Tag("포트폴리오", "Portfolio"),
        Tag("자격증", "Certificate"),
        Tag("어학", "Language Test"),
        Tag("면접", "Interview"),

        // 취미
        Tag("요리", "Cooking"),
        Tag("운동", "Exercise"),
        Tag("여행", "Travel"),
        Tag("독서", "Reading"),
        Tag("게임", "Gaming"),
        Tag("공예", "Craft"),
        Tag("식물", "Plant"),
        Tag("반려동물", "Pet")
    )
}
