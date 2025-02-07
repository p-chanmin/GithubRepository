# GithubRepository


## 스크린샷

| <img src="https://github.com/user-attachments/assets/17b53377-83d2-45d4-89ee-d3d48d8e4568" width="300"> | <img src="https://github.com/user-attachments/assets/56072a06-4d2f-41e1-98cb-d2c30b30e046" width="300"> |
| :----------------------------------------------------------: | :----------------------------------------------------------: |



## 프로젝트 구성

| <img src="https://developer.android.com/static/topic/libraries/architecture/images/mad-arch-overview.png?hl=ko" width="400"> | <img src="https://github.com/user-attachments/assets/5a4bc1a6-11fc-4dd6-a87b-39d84e2be612" width="300"> |
| ------------------------------------------------------------ | ------------------------------------------------------------ |

- UI Layer, Domain Layer, Data Layer로 분리하여 아키텍처를 구성하였습니다.
- MVVM 패턴을 적용하였습니다.



## Github API data logic

`/search/repositories`  API는 검색 키워드와 함께 `perPage`와 `page`를 쿼리 파라미터로 필요합니다.

사용자가 한번에 전체 데이터를 모두 가져오는 것이 아닌, 스크롤 하면서 데이터가 더 필요한 경우에 추가로 API를 호출하여 데이터를 받아오는 방식으로 구현하도록 고민하였습니다.

```kotlin
// :core:data - GithubRepositoryImpl.kt

class GitHubRepositoryImpl @Inject constructor(
    private val gitHubApi: GitHubApi,
) : GitHubRepository {

    private var page = 1
    private var endPage = false
    private var cacheTotalCount = 0
    private var cacheKeyword = ""
    private val cacheList = mutableListOf<RepositoryResponse>()

    override fun searchRepositories(
        keyword: String,
        perPage: Int
    ): Flow<List<RepositoryInfo>> = flow {
        if (cacheKeyword == keyword) {
            page++
        } else {
            cacheClear()
        }
        if (!endPage) {
            val response = gitHubApi.searchRepositories(
                keyword = keyword,
                page = page,
                perPage = perPage,
            )
            emit(response)
        } else {
            emit(SearchRepositoryResponse(totalCount = cacheTotalCount))
        }
    }.onEach { response ->
        cacheKeyword = keyword
        cacheTotalCount = response.totalCount
        cacheList.addAll(response.items)
        endPage = response.totalCount <= cacheList.size
    }.map {
        cacheList.map {
            it.toData()
        }
    }
    
    ...
    
    override fun cacheClear() {
        page = 1
        endPage = false
        cacheTotalCount = 0
        cacheKeyword = ""
        cacheList.clear()
    }  
}
```

-  `keyword`와 `perPage`를 받아 flow를 생성합니다.
- 먼저 cache된 키워드와 새로 들어온 인자를 비교하여 새로운 키워드가 검색 되었는지, 아니면 같은 키워드를 스크롤을 통해서 추가로 데이터를 불러오려고 하는지 판단합니다.
- 새로운 키워드가 검색되었다면, `cacheClear()`를 통해 캐시를 지우고 검색을 시작합니다.
- 기존 키워드와 같다면 추가로 데이터를 요청하기에 page를 증가시킵니다.
- `endPage`값을 통해 페이지가 끝에 도달했는지 판단하여, 남은 페이지가 있다면 Api를 통해 response를 받아 emit 하고, 페이지가 끝에 도달했다면 비어있는 response를 emit합니다.
- response를 통해 cache값을 저장하고 mapper를 통해 데이터를 변환합니다.

```kotlin
// :feature:search - SearchViewModel.kt

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepositoriesUseCase: SearchRepositoriesUseCase,
) : ViewModel() {

	...

    private val loadFlow = MutableStateFlow(false)

    init {
        loadRepositories().launchIn(viewModelScope)
    }

    private fun loadRepositories(): Flow<Unit> = loadFlow
        .filter { it }
        .filter {
            _searchUiState.value.keyword.isNotEmpty().also {
                if (!it) {
                    loadFlow.value = false
                } else {
                    _searchUiState.update { it.copy(showProgress = true) }
                }
            }
        }.flatMapLatest {
            searchRepositoriesUseCase(_searchUiState.value.keyword, 40)
        }.map { data ->
            loadFlow.value = false
            _searchUiState.update {
                it.copy(
                    repositories = data.toSet().toPersistentList(),
                    showProgress = false
                )
            }
        }.retry { e ->
            loadFlow.value = false
            _searchUiState.update {
                it.copy(
                    showProgress = false
                )
            }
            _errorFlow.emit(e)
            true
        }
```

- ViewModel에서는 `searchRepositoriesUseCase`를 통해서 레포지토리 검색 데이터를 가져올 수 있습니다.
- `loadFlow`를 통해서 `loadFlow`의 State가 변할 때마다 flow를 실행하게 됩니다. `loadFlow`의 값이 true일 때, 입력된 keyword를 확인하고, showProgress state를 갱신하며, 데이터를 받아 옵니다.
- repository에서 최종적으로 cacheList를 건네주기 때문에, ViewModel쪽에서는 uiState 업데이트만 하게 됩니다. 
- error를 catch하면서 flow가 종료되는 것을 막기 위해 retry를 사용했습니다.
  error가 발생하면, loadFlow를 false로 설정하여 filter를 통해 자동으로 다시 flow가 수행하지 않도록 하며, `_errorFlow`에 에러를 emit하여 사용자에게 에러 메세지를 전달할 수 있도록 하였습니다.



## Scroll

```kotlin
// :feature:search - SearchViewModel.kt

fun loadMoreRepositories(currentItemIndex: Int) {
    if (!loadFlow.value && currentItemIndex >= _searchUiState.value.repositories.size - 10) {
        loadFlow.value = true
    }
}
```

- ViewModel에서는 사용자가 스크롤 하면서 추가로 데이터를 받아오는 `loadMoreRepositories`함수가 있습니다.

- `LazyListState`에서 관리되는 `firstVisibleItemIndex`와 `layoutInfo.visibleItemsInfo.size`값을 더해서, 사용자가 현재 보고있는 화면의 마지막 아이템 인덱스인 `currentItemIndex`를 구할 수 있었습니다.
- 이를 통해, 첫 아이템부터 스크롤을 하다가 보여줄 수 있는 데이터가 10개 남았을 때 새롭게 호출하도록 하였습니다.
- 스크롤이 완전히 끝나고 새로 데이터를 호출하는 것보다, 사용자가 스크롤을 하는 중에 데이터를 받아와서 자연스럽게 계속 스크롤 할 수 있도록 하는 것이 더 사용자 친화적이라고 생각했습니다.

```kotlin
// :feature:search - RepositoryList.kt

@Composable
internal fun RepositoryList(
	...
    loadMoreRepositories: (Int) -> Unit,
) {
    LaunchedEffect(Unit) {
        snapshotFlow { listState.firstVisibleItemIndex + listState.layoutInfo.visibleItemsInfo.size }
            .collectLatest { currentItemIndex ->
                loadMoreRepositories(currentItemIndex)
            }
    }
    ...
}
```

- `LaunchedEffect`를 안에서 snapshotFlow를 생성합니다. listState의 `firstVisibleItemIndex`와 `layoutInfo.visibleItemsInfo.size`값을 더해 `currentItemIndex`의 변화를 감지하고, collectLatest를 통해 최신값만 처리하여 ViewModel의 `loadMoreRepositories()`를 실행시킵니다.
- snapshotFlow를 통해 빠르게 스크롤 시, 너무 많은 호출이 일어나지 않도록 방지할 수 있었습니다.



<img src="https://github.com/user-attachments/assets/83d2f22b-b1d5-4736-bad6-57d9baa25093" width="300">

- repository에서의 데이터 로직과 스크롤 시 호출되어 추가로 데이터를 받아오는 flow 통해, 한번에 모든 데이터를 받아오지 않고, 일부를 필요할 때 받아오는 방법으로 불필요한 리소스를 점유하지 않으면서 메모리 사용을 최적화할 수 있었습니다. 또한 로딩 시간을 단축하여 앱의 반응성도 높일 수 있었습니다.



## 사용 라이브러리

| 분류                    |                       |
| ----------------------- | --------------------- |
| UI                      | Jetpack Compose       |
| DI                      | Hilt                  |
| navigation              | jetpack navigation    |
| Network                 | retrofit2, okhttp3    |
| serializer/deserializer | kotlinx Serialization |
| Asynchronous            | Coroutines, Flow      |
| Image                   | Coil                  |
| test                    | kotest, turbine       |



## Dependency Graph

<img src="https://github.com/user-attachments/assets/104d3539-b5a4-4f05-af48-3a7f4b9c04da" width="600">





## 참고자료

> https://docs.github.com/en/rest/repos/repos?apiVersion=2022-11-28
>
> https://github.com/ozh/github-colors

