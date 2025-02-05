package com.dev.githubrepository.feature.repository

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.dev.githubrepository.core.navigation.Route
import com.dev.githubrepository.feature.repository.model.RepositoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow get() = _errorFlow.asSharedFlow()

    private val _repositoryUiState = MutableStateFlow(RepositoryUiState())
    val repositoryUiState = _repositoryUiState.asStateFlow()

    init {
        val (owner, repo) = savedStateHandle.toRoute<Route.Repository>()
        _repositoryUiState.update {
            it.copy(owner = owner, repo = repo)
        }
    }
}