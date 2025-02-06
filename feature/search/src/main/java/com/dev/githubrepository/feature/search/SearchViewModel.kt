package com.dev.githubrepository.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.githubrepository.core.domain.usecase.SearchRepositoriesUseCase
import com.dev.githubrepository.feature.search.model.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepositoriesUseCase: SearchRepositoriesUseCase,
) : ViewModel() {

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow get() = _errorFlow.asSharedFlow()

    private val _searchUiState = MutableStateFlow(SearchUiState())
    val searchUiState = _searchUiState.asStateFlow()

    private val loadFlow = MutableStateFlow(false)

    init {
        loadRepositories().launchIn(viewModelScope)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadRepositories(): Flow<Unit> = loadFlow
        .filter {
            it
        }.filter {
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
            println("${_searchUiState.value.repositories}")
        }

    fun loadMoreRepositories(
        firstVisibleItemIndex: Int,
        visibleItemCount: Int
    ) {
        val currentItemIndex = firstVisibleItemIndex + visibleItemCount
        if (!loadFlow.value && currentItemIndex >= _searchUiState.value.repositories.size - 10) {
            loadFlow.value = true
        }
    }

    fun changeKeyword(keyword: String) {
        _searchUiState.value = searchUiState.value.copy(
            keyword = keyword,
        )
    }

    fun searchKeyword() {
        loadFlow.value = true
    }
}