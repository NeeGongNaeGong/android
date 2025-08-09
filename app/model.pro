# retrofit 통신할 때 쓰는 request 및 response 난독화 방지 위해 domain의 model을 난독화 하지 않도록 변경
# 정확히는 gson의 SerializaeName 어노테이션을 모든 request 및 response에 붙혀서 api 통신할 때 쓰이는 것만 안되도록 막는 게 맞음
# 추후 변경해야 함 - 또한 Gson에서 serialization으로 변경하는 것도 검토 대상
-keep class com.ssafy.neegongnaegong.data.model.** { *; }
# API의 Response의 일부 데이터 타입을 Domain Type을 가져다 쓴 것이 있어서 우선 domain model 또한 난독화에서 제외시킴
-keep class com.ssafy.neegongnaegong.domain.model.** { *; }

#  Ensure that the serialName for this argument is the default fully qualified name 처럼 serialName 관련해서 클래스 이름이 변해서 에러가 나는 것 막아야 함
-keepnames enum * {
    *;
}