# android

## commit convention
- feat(#이슈) : 기능 (새로운 기능)
- ui(#이슈): 화면 (화면 수정)
- fix(#이슈): 버그 (버그 수정)
- refactor(#이슈): 리팩토링
- comment(#이슈): 필요한 주석 추가 및 변경
- style(#이슈): 스타일 (코드 형식, 세미콜론 추가: 비즈니스 로직에 변경 없음)
- docs(#이슈): 문서 수정 (문서 추가, 수정, 삭제, README)
- test(#이슈): 테스트 (테스트 코드 추가, 수정, 삭제: 비즈니스 로직에 변경 없음)
- chore(#이슈): 기타 변경사항 (빌드 스크립트 수정, assets, 패키지 매니저 등)
- init(#이슈): 초기 생성
- rename(#이슈): 파일 혹은 폴더명을 수정하거나 옮기는 작업만 한 경우
- remove(#이슈): 파일을 삭제하는 작업만 수행한 경우

## Ktlint & Detekt
### Ktlint 검사
```bash
#ktlint는 특정 파일만 검사하는게 안됩니다
./gradlew ktlintFormat
```
### Detekt 검사
```bash
./gradlew detekt --auto-correct --continue

# 특정 파일을 검사하고 싶을 땐 아래와 같은 꼴로
./gradlew detektStaged -PstagedFiles="app/src/main/java/com/ssafy/neegongnaegong/presentation/group/record/RecordScreen.kt"
```

## Metrics & Report
최상위의 build에 생성됨
```
./gradlew assembleRelease -PenableComposeCompilerMetrics=true -PenableComposeCompilerReports=true
```
