# RickDex

Rick and Morty 캐릭터 도감 앱.
Jetpack Compose와 클린 아키텍처 학습을 목적으로, 실무에서 쓰이는 구조와 라이브러리를 그대로 적용해 만들었습니다.

<!-- 스크린샷 4장 정도. 목록 / 검색 / 상세 / 설정(다크모드) -->
| 목록 | 검색 | 상세 | 설정 |
|---|---|---|---|
| <img src="docs/screenshot_list.png" width="180"/> | <img src="docs/screenshot_search.png" width="180"/> | <img src="docs/screenshot_detail.png" width="180"/> | <img src="docs/screenshot_settings.png" width="180"/> |

---

## 목차

- [기능](#기능)
- [기술 스택](#기술-스택)
- [아키텍처](#아키텍처)
- [모듈 구조](#모듈-구조)
- [주요 구현 내용](#주요-구현-내용)
- [테스트](#테스트)
- [빌드 환경](#빌드-환경)
- [회고](#회고)

---

## 기능

- 캐릭터 목록 조회 (무한 스크롤, 총 826명)
- 이름 검색 (디바운스 적용)
- 캐릭터 상세 정보
- 북마크 (로컬 DB 저장)
- 당겨서 새로고침
- 다크 모드 / Dynamic Color 설정
- 네트워크 오류 구분 처리 및 재시도

---

## 기술 스택

### UI
| 라이브러리 | 용도 |
|---|---|
| Jetpack Compose | 선언형 UI |
| Material 3 | 디자인 시스템 |
| Navigation 3 | 화면 이동 |
| Coil 3 | 이미지 로딩 |
| Paging 3 | 무한 스크롤 |

### 아키텍처 / 비동기
| 라이브러리 | 용도 |
|---|---|
| Hilt | 의존성 주입 |
| Coroutines / Flow | 비동기 처리 |
| ViewModel + StateFlow | 상태 관리 |

### 데이터
| 라이브러리 | 용도 |
|---|---|
| Retrofit + OkHttp | REST 통신 |
| kotlinx.serialization | JSON 직렬화 |
| Room | 로컬 DB (북마크) |
| DataStore | 설정값 저장 |

### 빌드 / 품질
| 도구 | 용도 |
|---|---|
| Version Catalog | 의존성 버전 관리 |
| KSP | 어노테이션 처리 |
| LeakCanary | 메모리 누수 탐지 |
| JUnit / Turbine | 단위 테스트 |
| Compose UI Test | 화면 테스트 |
| R8 | 릴리즈 최적화 |

---

## 아키텍처

클린 아키텍처 3계층 + MVVM

```
┌─────────────────────────────────────────┐
│           presentation                  │
│   Composable · ViewModel · UiState      │
└───────────────┬─────────────────────────┘
                │
                ▼
┌─────────────────────────────────────────┐
│              domain                     │
│   Model · UseCase · Repository(추상)     │  ← 안드로이드 의존성 0
└───────────────▲─────────────────────────┘
                │
┌───────────────┴─────────────────────────┐
│               data                      │
│  Retrofit · Room · Repository(구현)      │
└─────────────────────────────────────────┘
```

**의존성 역전**

Repository 인터페이스는 `domain`에, 구현체는 `data`에 두고 Hilt의 `@Binds`로 연결했습니다.
`domain` 모듈은 순수 Kotlin 모듈로 만들어, 안드로이드 SDK와 Retrofit·Room에 대한
의존이 **컴파일 단계에서 원천 차단**되도록 했습니다.

**모델 분리**

| 모델 | 위치 | 역할 |
|---|---|---|
| `CharacterDto` | data | API 응답 형태 그대로 |
| `BookmarkEntity` | data | DB 스키마 |
| `Character` | domain | 앱이 사용하는 형태 |

`CharacterDto`는 `origin`을 객체로 받지만 화면에는 이름만 필요합니다.
이 변환을 `data` 계층의 Mapper가 담당하므로, **API 응답 구조가 바뀌어도
화면 코드는 수정할 필요가 없습니다.**

**단방향 데이터 흐름**

```
Screen  ──이벤트(콜백)──▶  ViewModel  ──▶  UseCase  ──▶  Repository
   ◀────상태(StateFlow)────
```

---

## 모듈 구조

```
RickDex
├── app                  진입점, 네비게이션 조립, DI 설정
├── domain               모델, UseCase, Repository 인터페이스 (순수 Kotlin)
├── data                 Retrofit, Room, DataStore, Repository 구현
├── core
│   └── ui               테마, 공용 컴포넌트, 화면 키(NavKey)
└── feature
    ├── list             목록 화면
    └── detail           상세 화면
```

의존 방향

```
app ──▶ feature/* ──▶ domain ◀── data
              │                  │
              └──▶ core:ui       └──▶ app (DI 조립 시점)
```

---

## 주요 구현 내용

### 검색 디바운스

입력할 때마다 API를 호출하면 요청이 과다하게 발생하고, 응답 순서가 뒤바뀌어
이전 검색어의 결과가 표시되는 문제가 있었습니다.

```kotlin
val characters: Flow<PagingData<Character>> = _query
    .debounce(400)
    .distinctUntilChanged()
    .flatMapLatest { query -> getCharacters(query.ifBlank { null }, viewModelScope) }
```

- `debounce` — 입력이 멈춘 뒤에만 요청
- `distinctUntilChanged` — 동일 검색어 중복 요청 방지
- `flatMapLatest` — 새 입력이 오면 진행 중인 요청 취소

`flatMapLatest`가 이전 코루틴을 취소하므로, `catch (e: Exception)`이
`CancellationException`까지 잡아 "오류"로 표시하는 문제가 있었습니다.
구체적인 예외(`IOException`, `HttpException`)만 잡도록 수정해 해결했습니다.

### Navigation 3와 ViewModel 인자 주입

Navigation 2에서는 route가 문자열이고 인자가 Bundle에 담기므로
`SavedStateHandle`로 ViewModel에서 인자를 꺼낼 수 있었습니다.

Navigation 3는 백스택 키가 타입이 있는 객체이기 때문에 Bundle 포장을 하지 않고,
따라서 `SavedStateHandle`이 비어 있습니다. 공식 문서가 권장하는
**Assisted Injection**으로 해결했습니다.

```kotlin
@HiltViewModel(assistedFactory = CharacterDetailViewModel.Factory::class)
class CharacterDetailViewModel @AssistedInject constructor(
    @Assisted private val navKey: CharacterDetailNavKey,
    private val getCharacter: GetCharacterUseCase
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(navKey: CharacterDetailNavKey): CharacterDetailViewModel
    }

    init { load() }
}
```

생성자에서 id를 알 수 있게 되어 `init`에서 한 번만 로드하면 됩니다.
`LaunchedEffect`로 호출하던 기존 방식은 화면 회전 시 재호출되는 문제가 있었습니다.

또한 `NavDisplay`에 `rememberViewModelStoreNavEntryDecorator`를 추가해야
ViewModel이 각 `NavEntry`에 스코프됩니다. 누락 시 Activity 스코프가 되어
다른 항목을 선택해도 이전 ViewModel이 재사용됩니다.

### UseCase에서의 데이터 조합

UseCase를 Repository의 단순 위임으로만 두면 의미가 없습니다.
API 목록과 로컬 북마크 상태를 합치는 규칙을 UseCase에 배치했습니다.

```kotlin
operator fun invoke(query: String?, scope: CoroutineScope) =
    combine(
        characterRepository.getCharactersPaged(query).cachedIn(scope),
        bookmarkRepository.observeBookmarkedIds()
    ) { pagingData, bookmarkedIds ->
        pagingData.map { it.copy(isBookmarked = it.id in bookmarkedIds) }
    }
```

Room DAO가 `Flow`를 반환하므로 테이블 변경 시 자동으로 새 값이 방출됩니다.
북마크를 토글하면 목록이 자동 갱신되어, **수동 새로고침 코드가 필요 없습니다.**

북마크 id는 `List`가 아닌 `Set`으로 변환했습니다.
스크롤할 때마다 각 항목의 포함 여부를 확인하므로 `contains`가 O(1)이어야 합니다.

### 에러 처리

Retrofit 예외를 그대로 화면에 노출하면 사용자가 이해할 수 없고,
presentation 계층이 Retrofit에 의존하게 됩니다.

```
HttpException/IOException  →  toDataError()  →  DataError  →  asMessage()  →  화면
        (data)                  (data)         (domain)       (core:ui)
```

`DataError`는 `domain`의 enum이므로 화면은 Retrofit을 알 필요가 없습니다.
사용자 메시지는 `core:ui`에 두어 다국어 대응 시 확장 가능하도록 했습니다.

검색 결과가 없을 때 이 API는 HTTP 404를 반환합니다.
`PagingSource`에서 404를 빈 페이지로 변환해, 오류가 아닌 "결과 없음"으로 처리했습니다.

Cloudflare rate limit(429) 대응으로 OkHttp Interceptor를 추가해
`Retry-After` 헤더를 존중하여 재시도합니다.

### 리컴포지션 최적화

목록 아이템에 캡처하는 람다를 전달하면 리컴포지션마다 새 객체가 생성되어,
파라미터가 변경된 것으로 판단되어 skipping이 무효화됩니다.

```kotlin
// 개선 전 — 매 리컴포지션마다 새 람다 객체
CharacterCard(onClick = { onCharacterClick(character.id) })

// 개선 후 — 함수 참조 전달, id는 카드 내부에서 주입
CharacterCard(onClick = onCharacterClick)
```

Layout Inspector의 Recomposition Counts로 개선 전후를 확인했습니다.

---

## 테스트

| 위치 | 대상 | 도구 |
|---|---|---|
| `src/test` | Mapper, UseCase, ViewModel | JUnit, Turbine, coroutines-test |
| `src/androidTest` | Composable | Compose UI Test |

Repository를 인터페이스로 분리했기 때문에 Fake 구현체로 교체해 테스트합니다.
화면을 `Route`(ViewModel 연결)와 `Screen`(상태 없음)으로 분리해,
UI 테스트에서 원하는 상태를 직접 주입할 수 있습니다.

```kotlin
CharacterListScreen(
    uiState = CharacterListUiState(isLoading = true),  // 로딩 상태를 Preview로도 확인 가능
    ...
)
```

---

## 빌드 환경

| 항목 | 버전 |
|---|---|
| Android Gradle Plugin | 9.4 |
| Kotlin | 2.2.10 |
| compileSdk | 36 |
| minSdk | 26 |
| JDK | 17 |

### 실행 방법

```bash
git clone https://github.com/{사용자명}/RickDex.git
cd RickDex
./gradlew installDebug
```

API 키가 필요 없습니다. [Rick and Morty API](https://rickandmortyapi.com)는 인증 없이 사용 가능합니다.

---

## 회고

### 배운 것

**계층 분리의 실질적 효과**

프로젝트 초기에 사용할 API를 세 번 변경했습니다.
그 경험이 "화면 코드가 데이터 출처에 묶이면 안 된다"는 원칙을 체감하게 했고,
DTO와 도메인 모델을 분리하는 이유를 이해하는 계기가 되었습니다.

**멀티모듈의 강제력**

패키지로만 나누면 계층 규칙은 약속에 불과합니다.
`domain`을 순수 Kotlin 모듈로 분리하니 `import android.*`이 컴파일 에러가 되어,
규칙 위반이 물리적으로 불가능해졌습니다.

**최신 버전의 대가**

AGP 9 환경에서 시작해 Hilt, KSP 등 여러 라이브러리의 호환성 문제를 겪었습니다.
에러 메시지의 `What went wrong` 섹션을 읽고 원인을 좁히는 과정,
그리고 라이브러리 릴리즈 노트를 확인해 대응 버전을 찾는 과정을 반복했습니다.

### 개선하고 싶은 것

- Room + Paging `RemoteMediator`를 이용한 완전한 오프라인 캐싱
- 태블릿 대응 (Navigation 3의 `ListDetailSceneStrategy`)
- CI 구축 (PR 시 테스트 자동 실행)
- Baseline Profile 직접 생성 (현재는 라이브러리 기본 프로파일만 적용)

---

## 라이선스

이 프로젝트는 학습 목적으로 제작되었습니다.
캐릭터 데이터는 [Rick and Morty API](https://rickandmortyapi.com)를 사용합니다.
