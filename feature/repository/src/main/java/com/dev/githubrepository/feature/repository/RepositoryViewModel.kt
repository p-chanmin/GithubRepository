package com.dev.githubrepository.feature.repository

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.dev.githubrepository.core.domain.usecase.GetRepositoryDetailsUseCase
import com.dev.githubrepository.core.domain.usecase.GetUserInfoUseCase
import com.dev.githubrepository.core.domain.usecase.GetUserRepositoriesUseCase
import com.dev.githubrepository.core.navigation.Route
import com.dev.githubrepository.feature.repository.model.RepositoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRepositoryDetailsUseCase: GetRepositoryDetailsUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getUserRepositoriesUseCase: GetUserRepositoriesUseCase,
) : ViewModel() {

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow get() = _errorFlow.asSharedFlow()

    private val _repositoryUiState = MutableStateFlow(RepositoryUiState())
    val repositoryUiState = _repositoryUiState.asStateFlow()

    private val loadFlow = MutableStateFlow(false)

    init {
        val (owner, repo) = savedStateHandle.toRoute<Route.Repository>()
        loadRepositoryDetail(owner, repo).launchIn(viewModelScope)
        loadFlow.value = true
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun loadRepositoryDetail(owner: String, repo: String): Flow<Unit> = loadFlow
        .filter { it }
        .onEach {
            _repositoryUiState.update {
                it.copy(showProgress = true)
            }
        }.flatMapLatest {
            combine(
                getRepositoryDetailsUseCase(owner, repo),
                getUserInfoUseCase(owner),
                getUserRepositoriesUseCase(owner)
            ) { repositoryInfo, userInfo, userRepositories ->
                _repositoryUiState.value.copy(
                    showProgress = false,
                    name = repositoryInfo.name,
                    description = repositoryInfo.description,
                    topics = repositoryInfo.topics.toPersistentList(),
                    stargazersCount = repositoryInfo.stargazersCount,
                    watchersCount = repositoryInfo.watchersCount,
                    forksCount = repositoryInfo.forksCount,
                    owner = userInfo.username,
                    avatarUrl = userInfo.avatarUrl,
                    followers = userInfo.followers,
                    following = userInfo.following,
                    language = userRepositories.mapNotNull { it.language }.toSet()
                        .joinToString(","),
                    publicRepos = userInfo.publicRepos,
                    bio = userInfo.bio
                )
            }
        }.map { state ->
            loadFlow.value = false
            _repositoryUiState.update {
                state.copy(
                    showProgress = false
                )
            }
            println("${_repositoryUiState.value}")
        }.retry { e ->
            loadFlow.value = false
            _repositoryUiState.update {
                it.copy(
                    showProgress = false
                )
            }
            _errorFlow.emit(e)
            true
        }

    fun refreshRepositoryDetail() {
        loadFlow.value = true
    }

    fun updateBottomSheetState(value: Boolean) {
        _repositoryUiState.update {
            it.copy(
                showBottomSheet = value
            )
        }
    }
}